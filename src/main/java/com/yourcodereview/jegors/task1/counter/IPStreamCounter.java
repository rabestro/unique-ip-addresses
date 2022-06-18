package com.yourcodereview.jegors.task1.counter;

import com.yourcodereview.jegors.task1.container.IntContainer;
import com.yourcodereview.jegors.task1.container.LongArrayContainer;

import java.io.IOException;
import java.nio.file.Path;

public class IPStreamCounter implements IPv4Counter {

    @Override
    public long countUnique(Path path) throws IOException {
        try (var ips = IPStream.from(path)) {
            return ips
                    .collect(LongArrayContainer::new, IntContainer::add, IntContainer::addAll)
                    .countUnique();
        }
    }
}
