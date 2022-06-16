package com.yourcodereview.jegors.task1.benchmark;

import com.yourcodereview.jegors.task1.converter.IPv4Converter;
import com.yourcodereview.jegors.task1.converter.IPv4SimpleConverter;
import org.openjdk.jmh.annotations.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
public class ConverterBenchmark {
    private static final Pattern DOT = Pattern.compile(".", Pattern.LITERAL);
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

    @Benchmark
    public long parseIpShift() {
        long result = 0L;
        // iterate over each octet
        for (String part : ipAddress.split(Pattern.quote("."))) {
            // shift the previously parsed bits over by 1 byte
            result = result << 8;
            // set the low order bits to the current octet
            result |= Integer.parseInt(part);
        }
        return result;
    }

    @Benchmark
    public long parseIpShiftImproved() {
        long result = 0L;
        // iterate over each octet
        for (String part : DOT.split(ipAddress)) {
            // shift the previously parsed bits over by 1 byte
            result = result << 8;
            // set the low order bits to the current octet
            result |= Integer.parseInt(part);
        }
        return result;
    }
}
