package com.onoprienko.movieland.service.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomGenerationUtilsTest {

    @Test
    void getRandomIndexesReturnIndexes() {
        List<Integer> randomIndexes = RandomGenerationUtils.getRandomIndexes(10, 2);

        assertNotNull(randomIndexes);

        assertEquals(randomIndexes.size(), 2);

        assertNotEquals(randomIndexes.get(0), randomIndexes.get(1));
    }

    @Test
    void getRandomIndexesReturnExceptionIfSizeNull() {
        assertThrows(NullPointerException.class, () -> RandomGenerationUtils.getRandomIndexes(10, null));
    }

    @Test
    void getRandomIndexesReturnExceptionIfBoundNull() {
        assertThrows(NullPointerException.class, () -> RandomGenerationUtils.getRandomIndexes(null, 1));
    }

    @Test
    void getRandomIndexesReturnExceptionIfBoundLessThanTwo() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerationUtils.getRandomIndexes(1, 4));
    }


    @Test
    void getRandomIndexesReturnExceptionIfBoundLessThanSize() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerationUtils.getRandomIndexes(5, 6));
    }


    @Test
    void getRandomPageReturnPage() {
        Integer randomPage = RandomGenerationUtils.getRandomPage(30, 10);

        assertNotNull(randomPage);

    }


    @Test
    void getRandomPageReturnPageZeroIfCapacityBiggerThanCount() {
        Integer randomPage = RandomGenerationUtils.getRandomPage(10, 11);

        assertNotNull(randomPage);

        assertEquals(randomPage, 0);

    }
}