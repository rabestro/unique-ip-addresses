package com.yourcodereview.jegors.task1.counter;

import java.io.IOException;
import java.nio.file.Path;

public interface IPv4Counter {

    long countUnique(Path path) throws IOException;
}
