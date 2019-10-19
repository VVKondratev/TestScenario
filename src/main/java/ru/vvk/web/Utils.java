package ru.vvk.web;

public class Utils {
    /** Поле, сохраняющее предидущее полученное значение */
    static int prev = -1;

    /**
     * Пункт 6. Генерируется случайное число в диапазоне от 1 до num.
     * @param num Количество товаров, полученное в пункте 5.
     * @return Возвращает случайное число.
     */
    public static Long randomize(Long num) {
        long leftLimit = 1L;
        long rightLimit = num;
        long generatedLong = 0L;
        do {
            generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        } while (generatedLong == prev);

        return generatedLong;
    }

    /**
     * Вспомогательный метод, приостанавливающий работу программы.
     * @param timeOut Время в миллисекундах.
     */
    public static void wait(int timeOut) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
