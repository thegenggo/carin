package com.kwp.util;

import java.util.Random;

public class CarinRandom {
    private static final Random random = new Random();

    private CarinRandom() {

    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static int nextInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static double nextFloat() {
        return random.nextFloat();
    }

    public static double nextDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
