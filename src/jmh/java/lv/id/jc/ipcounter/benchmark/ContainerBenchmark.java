package lv.id.jc.ipcounter.benchmark;

import lv.id.jc.ipcounter.collector.DualBitSetContainer;
import lv.id.jc.ipcounter.collector.IntContainer;
import lv.id.jc.ipcounter.collector.LongArrayContainer;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
public class ContainerBenchmark {
    private final IntContainer bitSetContainer = new DualBitSetContainer();
    private final IntContainer arrayContainer = new LongArrayContainer();

    @Param({"1B", "1M", "1K"})
    public String amount;

    private Map<String, int[]> data;

    private int[] generate(int amount) {
        return ThreadLocalRandom.current().ints().limit(amount).toArray();
    }


    @Setup
    public void generateTestData() {
        data = Map.of(
                "1B", generate(1_000_000_000),
                "1M", generate(1_000_000),
                "1K", generate(1000)
        );
    }

    @Benchmark
    public long dualBitSetContainer() {
        for (int number : data.get(amount)) {
            bitSetContainer.add(number);
        }
        return bitSetContainer.countUnique();
    }

    @Benchmark
    public long longArrayContainer() {
        for (int number : data.get(amount)) {
            arrayContainer.add(number);
        }
        return arrayContainer.countUnique();
    }
}
