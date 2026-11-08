package dev.carv.bank.commons.util;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomEnum {

    private static final Random random = new SecureRandom();

    public static <E extends Enum<?>> E obtain(Class<E> clazz) {
        var values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

}
