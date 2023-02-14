package com.onoprienko.movieland.service.utils;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerationUtils {
    @SneakyThrows
    public static List<Integer> getRandomIndexes(Integer bound, Integer size) {
        if (bound < 2 || bound < size) {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        while (i < size) {
            int randomIndex = random.nextInt(bound);
            if (!indexes.contains(randomIndex)) {
                indexes.add(randomIndex);
                i++;
            }
        }
        return indexes;
    }

    public static Integer getRandomPage(Integer count, Integer capacity) {
        if (capacity >= count) {
            return 0;
        }
        Random random = new Random();
        int amountOfPages = (count / capacity);
        return random.nextInt(amountOfPages);
    }

}
