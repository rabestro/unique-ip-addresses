package com.yourcodereview.jegors.task1.benchmark;

import com.yourcodereview.jegors.task1.container.BitSetContainer;
import com.yourcodereview.jegors.task1.container.DualBitSetContainer;
import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;
import com.yourcodereview.jegors.task1.container.MaxBitSetContainer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.stream;
import static java.util.concurrent.ThreadLocalRandom.current;

//  "-XX:+PrintGCDetails"
@Fork(jvmArgs = {"-Xmx16g", "-Xms1g"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 50_000, timeUnit = TimeUnit.MILLISECONDS)
public class ContainerBenchmark {
    private static final int BILLION = 1_000_000_000;
    private final IntContainer dualBitSetContainer = new DualBitSetContainer();
    private final IntContainer maxBitSetContainer = new MaxBitSetContainer();
    private final IntContainer arrayContainer = new LongArrayContainer();
    private final IntContainer level1Container = new BitSetContainer(1);
    private final IntContainer level8Container = new BitSetContainer(Byte.SIZE);
    @Param({
            "1B"
            , "1M"
            , "1K"
            , "+1B"
            , "#1B"
    })
    public String amount;
    private Map<String, int[]> data;

    private int[] generate(int amount) {
        return current().ints(amount).toArray();
    }

    private int addRandomSegment(int i) {
        return i | (current().nextInt(10) << 24);
    }

    @Setup
    public void generateTestData() {
        data = Map.of(
                "1B", generate(BILLION),
                "1M", generate(1_000_000),
                "1K", generate(1000)
                , "+1B", current()
                        .ints(BILLION, 0, Integer.MAX_VALUE)
                        .toArray()
                , "#1B", current()
                        .ints(BILLION, 0, 1 << 25)
                        .map(this::addRandomSegment)
                        .toArray()
        );
    }

    //    @Benchmark
    public long dualBitSetContainer() {
        stream(data.get(amount)).forEach(dualBitSetContainer::add);
        return dualBitSetContainer.countUnique();
    }

    @Benchmark
    public long maxBitSetContainer() {
        stream(data.get(amount)).forEach(dualBitSetContainer::add);
        return maxBitSetContainer.countUnique();
    }

    @Benchmark
    public long longArrayContainer() {
        stream(data.get(amount)).forEach(arrayContainer::add);
        return arrayContainer.countUnique();
    }

    @Benchmark
    public long level1Container() {
        stream(data.get(amount)).forEach(level1Container::add);
        return level1Container.countUnique();
    }

    @Benchmark
    public long level8Container() {
        stream(data.get(amount)).forEach(level8Container::add);
        return level8Container.countUnique();
    }

}
