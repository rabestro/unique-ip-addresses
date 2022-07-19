package com.yourcodereview.jegors.task1.benchmark;

import com.yourcodereview.jegors.task1.converter.IPv4Converter;
import com.yourcodereview.jegors.task1.converter.OptimizedConverter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5000, timeUnit = TimeUnit.MILLISECONDS)
public class ConverterBenchmark {
    private static final Pattern DOT = Pattern.compile(".", Pattern.LITERAL);
    private final ToIntFunction<CharSequence> converter = new IPv4Converter();
    private final ToIntFunction<CharSequence> optimized = new OptimizedConverter();

    @Param({"1.2.3.4", "120.1.34.78", "129.205.201.114"})
    public String ipAddress;

    @Benchmark
    public int simpleConverter() {
        return converter.applyAsInt(ipAddress);
    }

    @Benchmark
    public int optimizedConverter() {
        return optimized.applyAsInt(ipAddress);
    }

    @Benchmark
    public int applyAsInt() {
        long base = 0;
        long part = 0;

        for (int i = 0, n = ipAddress.length(); i < n; ++i) {
            char symbol = ipAddress.charAt(i);
            if (symbol == '.') {
                base = (base << Byte.SIZE) | part;
                part = 0;
            } else {
                part = part * 10 + symbol - '0';
            }
        }
        return (int) ((base << Byte.SIZE) | part);
    }

//    @Benchmark
    public int inetAddressConverter() throws UnknownHostException {
        return ByteBuffer
                .allocate(Integer.BYTES)
                .put(InetAddress.getByName(ipAddress).getAddress())
                .getInt(0);
    }

//    @Benchmark
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

//    @Benchmark
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
