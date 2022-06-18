package com.yourcodereview.jegors.task1.counter;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class IPStream implements Spliterator.OfInt, Closeable {
    private final BufferedReader bufferedReader;

    private IPStream(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public static IntStream from(Path path) throws IOException {
        var bufferedReader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
        return StreamSupport.intStream(new IPStream(bufferedReader), false);
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public OfInt trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        try {
            long base = 0;
            long part = 0;
            int dots = 0;
            for (; ; ) {
                int symbol = bufferedReader.read();
                if (symbol >= '0') {
                    part = part * 10 + symbol - '0';
                    continue;
                }
                base = (base << Byte.SIZE) | part;
                part = 0;

                if (symbol == '.') {
                    ++dots;
                    continue;
                }
                if (symbol < ' ' && dots == 3) {
                    action.accept((int) base);
                    return true;
                }
                if (dots != 0) {
                    throw new IllegalStateException();
                }
                if (symbol == -1) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
