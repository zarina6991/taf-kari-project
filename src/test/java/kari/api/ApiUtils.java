package kari.api;

import java.util.Random;

public final class ApiUtils {

    private static final Random RANDOM = new Random();

    private ApiUtils() {
        throw new UnsupportedOperationException("Это утилитный класс для API-тестов!");
    }
    /**
     * Генерирует строку из случайных цифр заданной длины.
     */
    public static String generateRandomNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
