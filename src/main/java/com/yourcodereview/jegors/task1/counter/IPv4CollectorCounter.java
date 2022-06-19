package com.yourcodereview.jegors.task1.counter;

import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.converter.IPv4Converter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class IPv4CollectorCounter implements IPv4Counter {
    private final IPv4Collector collector;

    public IPv4CollectorCounter(Supplier<IntContainer> intContainerSupplier) {
        collector = new IPv4Collector(intContainerSupplier, new IPv4Converter());
    }

    @Override
    public long countUnique(Path path) throws IOException {
        try (var ips = Files.lines(path, StandardCharsets.US_ASCII)) {
            return ips.collect(collector);
        }
    }
}
