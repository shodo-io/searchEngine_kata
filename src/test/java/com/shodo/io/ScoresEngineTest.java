package com.shodo.io;


import com.shodo.io.exceptions.ScoresEngineInputException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertEquals;

public class ScoresEngineTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    @Test(expected = ScoresEngineInputException.class)
    public void main_empty_args() throws ScoresEngineInputException {
        ScoresEngine.main(new String[]{});
    }

    @Test
    public void main_non_existant_directory() throws ScoresEngineInputException {
        ScoresEngine.main(new String[]{"src/test/resosssurces"});
        assertEquals("The program can't find the specified folder. Please type an existing one.",
                systemErrRule.getLogWithNormalizedLineSeparator().trim());
    }
}
