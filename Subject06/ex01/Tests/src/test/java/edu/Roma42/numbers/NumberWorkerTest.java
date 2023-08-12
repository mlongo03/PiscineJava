package edu.Roma42.numbers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import edu.Roma42.numbers.NumberWorker;


import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11})
    void isPrimeForPrimes(int number) {
        assertTrue(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10})
    void isPrimeForNotPrimes(int number) {
        assertFalse(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int expectedSum) {
        assertEquals(expectedSum, new NumberWorker().digitsSum(number));
    }
}

