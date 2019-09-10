package com.shodo.io.utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ScoresUtilsTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void calculateScores() {
        systemInMock.provideLines("second");
        boolean result = ScoresUtils.calculateScores(Map.of(
                "file1.txt", List.of("the", "word"),
                "file2.txt", List.of("the", "second", "word")
        ));
        assertFalse(result);
        assertEquals("search>file2.txt : 100.00%\nfile1.txt : 0.00%\n", systemOutRule.getLogWithNormalizedLineSeparator());

    }

    @Test
    public void should_return_true_exit() {
        systemInMock.provideLines("exit");
        boolean result = ScoresUtils.calculateScores(Map.of(
                "file1.txt", List.of("exit")
        ));
        assertTrue(result);
    }
}
