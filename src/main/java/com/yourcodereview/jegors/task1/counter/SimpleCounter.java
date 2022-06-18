package com.yourcodereview.jegors.task1.counter;

import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;
import com.yourcodereview.jegors.task1.converter.IPv4Converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleCounter implements IPv4Counter {

    @Override
    public long countUnique(Path path) throws IOException {
        try (var ips = Files.lines(path)) {
            return ips.mapToInt(new IPv4Converter())
                    .collect(LongArrayContainer::new, IntContainer::add, IntContainer::addAll)
                    .countUnique();
        }
    }
}
