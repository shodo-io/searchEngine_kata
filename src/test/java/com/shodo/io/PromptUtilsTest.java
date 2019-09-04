package com.shodo.io;

import com.shodo.io.utils.PromptUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PromptUtilsTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Before
    public void setUp() {
    }

    @Test
    public void should_display_search_and_return_list_of_searched_word() {
        systemInMock.provideLines("word1");
        List<String> searchedWords = PromptUtils.readSearchedWords();
        assertEquals("search>", systemOutRule.getLog());
        assertEquals(Collections.singletonList("word1"), searchedWords);
    }

    @Test
    public void should_display_search_and_return_list_of_searched_words() {
        systemInMock.provideLines("word1 word2 word3");
        List<String> searchedWords = PromptUtils.readSearchedWords();
        assertEquals("search>", systemOutRule.getLog());
        assertEquals(Arrays.asList("word1", "word2", "word3"), searchedWords);
    }

    @Test
    public void should_display_error() {
        systemInMock.provideLines(" ");
        List<String> searchedWords = PromptUtils.readSearchedWords();
        assertEquals("search>", systemOutRule.getLog());
        assertEquals("No words in the input. Please type something.", systemErrRule.getLogWithNormalizedLineSeparator().trim());
        assertTrue(searchedWords.isEmpty());
    }
}
