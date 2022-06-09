package lv.id.jc.ipcounter.collector;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A collector for IPv4 addresses that counts unique ones.
 */
public class IPv4Collector implements Collector<CharSequence, IntContainer, Long> {
    private final Supplier<IntContainer> containerSupplier;
    private final IPv4Converter converter;

    public IPv4Collector(Supplier<IntContainer> supplier, IPv4Converter converter) {
        this.containerSupplier = supplier;
        this.converter = converter;
    }

    public static IPv4Collector countingUnique() {
        var converter = new IPv4SimpleConverter();
        return new IPv4Collector(DoubleBitSetContainer::new, converter);
    }

    @Override
    public Supplier<IntContainer> supplier() {
        return containerSupplier;
    }

    @Override
    public BiConsumer<IntContainer, CharSequence> accumulator() {
        return (container, ipAddress) -> container.add(converter.applyAsInt(ipAddress));
    }

    @Override
    public BinaryOperator<IntContainer> combiner() {
        return (a, b) -> {
            throw new UnsupportedOperationException();
        };
    }

    @Override
    public Function<IntContainer, Long> finisher() {
        return IntContainer::countUnique;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
