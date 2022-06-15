package com.yourcodereview.jegors.task1.benchmark;

import com.yourcodereview.jegors.task1.container.BitSetContainer;
import com.yourcodereview.jegors.task1.container.DualBitSetContainer;
import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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
//@Fork(jvmArgs = {"-Xmx2000m", "-Xms512m"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
public class ContainerBenchmark {
    private final IntContainer dualBitSetContainer = new DualBitSetContainer();
    private final IntContainer level1Container = new BitSetContainer(1);
    private final IntContainer level8Container = new BitSetContainer(Byte.SIZE);
    private final IntContainer arrayContainer = new LongArrayContainer();

    private Map<String, int[]> data;

    private int[] generate(int amount) {
        return current().ints(amount).toArray();
    }

    @Param({"1B", "1M", "1K", "1m"})
    public String amount;

    @Setup
    public void generateTestData() {
        data = Map.of(
                "1B", generate(1_000_000_000),
                "1M", generate(1_000_000),
                "1K", generate(1000),
                "1m", current()
                        .ints(1_000_000, 0, 0x01000000)
                        .toArray()
        );
    }

    @Benchmark
    public long dualBitSetContainer() {
        stream(data.get(amount)).forEach(dualBitSetContainer::add);
        return dualBitSetContainer.countUnique();
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

    @Benchmark
    public long longArrayContainer() {
        stream(data.get(amount)).forEach(arrayContainer::add);
        return arrayContainer.countUnique();
    }
}
