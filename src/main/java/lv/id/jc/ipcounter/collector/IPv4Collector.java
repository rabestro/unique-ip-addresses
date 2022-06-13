package lv.id.jc.ipcounter.collector;

import lv.id.jc.ipcounter.container.IntContainer;
import lv.id.jc.ipcounter.container.LongArrayContainer;
import lv.id.jc.ipcounter.converter.IPv4Converter;
import lv.id.jc.ipcounter.converter.IPv4SimpleConverter;

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

    /**
     * Collector constructor with the ability to set a custom container and converter.
     *
     * @param supplier  for int numbers container
     * @param converter to transform an IPv4 address from string representation to int
     */
    public IPv4Collector(Supplier<IntContainer> supplier, IPv4Converter converter) {
        this.containerSupplier = supplier;
        this.converter = converter;
    }

    /**
     * Creates a collector instance.
     *
     * @return collector with default container and converter.
     */
    public static IPv4Collector countingUnique() {
        return new IPv4Collector(
                LongArrayContainer::new,
                new IPv4SimpleConverter()
        );
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
            throw new UnsupportedOperationException("Parallel processing is not implemented.");
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
