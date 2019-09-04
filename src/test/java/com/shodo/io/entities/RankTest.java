package com.shodo.io.entities;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RankTest {

    private List<String> fileWords;
    private String fileName;

    @Before
    public void setUp() {
        fileWords = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
        fileName = "file.txt";
    }

    @Test
    public void should_return_0_percent_score() {
        var rank = Rank.calculateRank(fileName, fileWords, Arrays.asList("fifteen", "eleven", "twelve", "twenty"));
        assertEquals(new Rank(fileName, new BigDecimal("0.00")), rank);
    }

    @Test
    public void should_return_25_percent_score() {
        var rank = Rank.calculateRank(fileName, fileWords, Arrays.asList("one", "eleven", "twelve", "twenty"));
        assertEquals(new Rank(fileName, new BigDecimal("0.25")), rank);
    }

    @Test
    public void should_return_50_percent_score() {
        var rank = Rank.calculateRank(fileName, fileWords, Arrays.asList("one", "eleven"));
        assertEquals(new Rank(fileName, new BigDecimal("0.50")), rank);
    }

    @Test
    public void should_return_75_percent_score() {
        var rank = Rank.calculateRank(fileName, fileWords, Arrays.asList("one", "two", "three", "twenty"));
        assertEquals(new Rank(fileName, new BigDecimal("0.75")), rank);
    }

    @Test
    public void should_return_100_percent_score() {
        var rank = Rank.calculateRank(fileName, fileWords, Arrays.asList("one", "two", "three", "four"));
        assertEquals(new Rank(fileName, new BigDecimal("1.00")), rank);
    }

    @Test
    public void should_return_100_percent_for_two_files() {
        var ranks = Rank.calculateRanks(Map.of(fileName, fileWords, "file2.txt", Arrays.asList("one", "two", "three", "four")),
                Arrays.asList("one", "two", "three", "four"));
        assertEquals(2, ranks.size());
        ranks.forEach(rank ->
                assertTrue("file.txt : 100.00%".equals(rank.toString())
                        || "file2.txt : 100.00%".equals(rank.toString())));

    }
}
