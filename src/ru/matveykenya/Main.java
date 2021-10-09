package ru.matveykenya;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 *  Отчет для налоговой
 */

public class Main {

    static final Random random = new Random();
    static final LongAdder totalSum = new LongAdder();
    static final int MAX_COUNT = 100;
    static final int MAX_SUM = 1000;


    public static void main(String[] args) {

        Thread shop1 = new Thread(Main::getTax, "Магазин_1");
        Thread shop2 = new Thread(Main::getTax, "Магазин_2");
        Thread shop3 = new Thread(Main::getTax, "Магазин_3");

        shop1.start();
        shop2.start();
        shop3.start();

        try {
            shop1.join();
            shop2.join();
            shop3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long sum = totalSum.sum();
        System.out.println("Сумма всех платежей " + sum);

        int[] array = {3, 5, 7, 12};
        int sum1 = Arrays.stream(array).sum();
    }

    private static void getTax(){
        List<Integer> list = getProceeds();
        System.out.println(Thread.currentThread().getName() + " -всего платежей- " + list.size() + " -на сумму- " + sum(list));
        for (int item : list) {
            totalSum.add(item);
        }
    }

    private static List<Integer> getProceeds() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < MAX_COUNT; i++) {
            list.add(random.nextInt(MAX_SUM));
        }
        return list;
    }

    private static long sum(List<Integer> list){
        long sum = 0;
        for (int item : list) {
            sum += item;
        }
        return sum;
    }
}
