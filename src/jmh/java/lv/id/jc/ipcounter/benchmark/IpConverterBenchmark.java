package lv.id.jc.ipcounter.benchmark;

import lv.id.jc.ipcounter.collector.IPv4Converter;
import lv.id.jc.ipcounter.collector.IPv4SimpleConverter;
import org.openjdk.jmh.annotations.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
public class IpConverterBenchmark {
    private final IPv4Converter converter = new IPv4SimpleConverter();

    @Param({"1.2.3.4", "120.1.34.78", "129.205.201.114"})
    public String ipAddress;

    @Benchmark
    public int simpleConverter() {
        return converter.applyAsInt(ipAddress);
    }

    @Benchmark
    public int inetAddressConverter() throws UnknownHostException {
        return ByteBuffer
                .allocate(Integer.BYTES)
                .put(InetAddress.getByName(ipAddress).getAddress())
                .getInt(0);
    }
}
