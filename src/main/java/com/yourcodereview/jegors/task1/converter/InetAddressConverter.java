package com.yourcodereview.jegors.task1.converter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.function.ToIntFunction;

public class InetAddressConverter implements ToIntFunction<String> {

    @Override
    public int applyAsInt(final String ipAddress) {
        try {
            return ByteBuffer
                    .allocate(Integer.BYTES)
                    .put(InetAddress.getByName(ipAddress).getAddress())
                    .getInt(0);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
