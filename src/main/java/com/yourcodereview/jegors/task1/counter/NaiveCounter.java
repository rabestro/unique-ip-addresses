package com.yourcodereview.jegors.task1.counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NaiveCounter implements IPv4Counter {

    @Override
    public long countUnique(Path path) throws IOException {
        try (var ips = Files.lines(path)) {
            return ips.distinct().count();
        }
    }
}
