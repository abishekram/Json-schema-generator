package org.ran.jsonschema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class RandomUtils {
    public static String getRandomString(int maxDerived) {
        Stream<String> randomStrings = Stream.generate(() -> random
                        .ints('a', 'z')
                        .limit(maxDerived)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString());
        return randomStrings.findFirst().get();
    }

    public static int getRandomInteger(int max, int min, int multipleOf) {
        int dividedMin = (min % multipleOf) == 0 ? min / multipleOf : min / multipleOf + 1;
        int dividedMax = max / multipleOf;

        if (dividedMin > dividedMax) {
            throw new JsonGenerationException("Integer: can't generate number that satisfy max, min and multipleOf");
        }

        return (random.nextInt(dividedMax + 1 - dividedMin) + dividedMin) * multipleOf;
    }

    public static Double getRandomNumber(double rangeMax, double rangeMin, double multipleOf) {
        long dividedMin = 0;
        if (Double.compare(Math.round(rangeMin / multipleOf), rangeMin / multipleOf) != 0) {
            dividedMin = Math.round(rangeMin / multipleOf) + 1;
        } else {
            dividedMin = Math.round(rangeMin / multipleOf);
        }

        long dividedMax = Math.round(rangeMax / multipleOf);

        if (dividedMin > dividedMax) {
            throw new JsonGenerationException("Number: can't generate number that satisfy max, min and multipleOf");
        }
        return (ThreadLocalRandom.current().nextLong(dividedMax + 1 - dividedMin) + dividedMin) * multipleOf;
    }

    public static Double getRandomNumber(double rangeMax, double rangeMin) {
        if (rangeMin > rangeMax) {
            throw new JsonGenerationException("Number: can't generate number that satisfy max and min");
        }
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    public static Map<String, Object> getRandomObject(int fields) {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < fields; ++i) {
            map.put(getRandomString(10),
                    random.nextInt(1) == 0 ?
                            getRandomString(10) :
                            getRandomInteger(100, 0, 1));
        }

        return map;
    }

    public static List<Object> getRandomArray(int size) {
        List<Object> array = new ArrayList<>();
        boolean stringElements = random.nextInt(1) == 0;

        for (int i = 0; i < size; ++i) {
            array.add(stringElements ? getRandomString(10) : getRandomInteger(100, 0, 1));
        }

        return array;
    }

    private static Random random = new Random();
}
