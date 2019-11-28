package com.shodo.io.scoring;

import com.shodo.io.entities.Rank;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoresServiceTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    private ScoresService scoresServiceSUT;
    private UserPromptService userPromptService;
    private RankService rankService;

    @Before
    public void setUp() {
        userPromptService = mock(UserPromptService.class);
        rankService = mock(RankService.class);
        scoresServiceSUT = new ScoresService(userPromptService, rankService);
    }

    @Test
    public void calculateScores() {
        String searchedWord = "second";
        List<String> searchedWords = Collections.singletonList(searchedWord);
        Map<String, List<String>> filesContent = Map.of(
                "file1.txt", List.of("the", "word"),
                "file2.txt", List.of("the", searchedWord, "word")
        );
        when(userPromptService.readSearchedWords()).thenReturn(searchedWords);
        when(rankService.calculateRanks(filesContent, searchedWords))
                .thenReturn(Arrays.asList(
                        new Rank("file2.txt", BigDecimal.valueOf(1)),
                        new Rank("file1.txt", BigDecimal.ZERO)
        ));
        systemInMock.provideLines(searchedWord);
        boolean result = scoresServiceSUT.calculateScores(filesContent);
        assertFalse(result);
        assertEquals("file2.txt : 100.00%\nfile1.txt : 0.00%\n", systemOutRule.getLogWithNormalizedLineSeparator());

    }

    @Test
    public void should_return_true_exit() {
        when(userPromptService.readSearchedWords()).thenReturn(Collections.singletonList("exit"));
        systemInMock.provideLines("exit");
        boolean result = scoresServiceSUT.calculateScores(Map.of(
                "file1.txt", List.of("exit")
        ));
        assertTrue(result);
    }
}
