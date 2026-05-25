package dev.carv.bank.account.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

public final class UUIDv7 {

    private static final SecureRandom random = new SecureRandom();

    private UUIDv7() {
    }

    private static byte[] randomBytes() {
        var value = new byte[16];
        random.nextBytes(value);

        var timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(System.currentTimeMillis());

        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        value[6] = (byte) ((value[6] & 0x0F) | 0x70);
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return value;
    }

    public static UUID generate() {
        var value = randomBytes();
        var buf = ByteBuffer.wrap(value);
        long high = buf.getLong();
        long low = buf.getLong();

        return new UUID(high, low);
    }

}
