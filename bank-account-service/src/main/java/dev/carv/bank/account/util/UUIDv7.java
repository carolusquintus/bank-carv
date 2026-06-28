package dev.carv.bank.account.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

public final class UUIDv7 {

    // Cryptographically strong pseudo-random number generator
    private static final SecureRandom RANDOM = new SecureRandom();

    // Prevent instantiation of this utility class
    private UUIDv7() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }

    /**
     * Generates a new time-ordered UUIDv7 based on the current system time.
     *
     * @return a randomly generated, time-ordered java.util.UUID
     */
    public static UUID generate() {
        // Allocate space for a 128-bit UUID (16 bytes)
        byte[] value = new byte[16];

        // 1. Fill the array with cryptographically secure random bytes
        RANDOM.nextBytes(value);

        // 2. Extract current timestamp in milliseconds (48-bit constraint)
        long timestamp = System.currentTimeMillis();

        // 3. Inject the 48-bit timestamp into the first 6 bytes of the array
        value[0] = (byte) (timestamp >> 40);
        value[1] = (byte) (timestamp >> 32);
        value[2] = (byte) (timestamp >> 24);
        value[3] = (byte) (timestamp >> 16);
        value[4] = (byte) (timestamp >> 8);
        value[5] = (byte) (timestamp);

        // 4. Set the 4-bit Version to 7 (0111) in the most significant bits of byte 6
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);

        // 5. Set the 2-bit Variant to IETF RFC 9562 (10xx) in the most significant bits of byte 8
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        // 6. Convert the byte array into standard java.util.UUID bits
        ByteBuffer buffer = ByteBuffer.wrap(value);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();

        return new UUID(mostSigBits, leastSigBits);
    }

}
