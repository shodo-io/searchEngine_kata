package com.shodo.io.scoring;

import com.shodo.io.scoring.InputFilesService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputFilesServiceTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    private InputFilesService inputFilesServiceSUT;

    @Before
    public void setUp() {
        inputFilesServiceSUT = new InputFilesService();
    }

    @Test
    public void should_return_each_word_in_list_by_file() throws IOException {
        try (var filesPaths = Files.list(Paths.get("src/test/resources"))) {
            Map<String, List<String>> result = inputFilesServiceSUT.readFilesContent(filesPaths);
            String file1 = "file1.txt";
            String file2 = "file2.txt";
            String file3 = "file3.txt";
            var expectedFile1Content = Arrays.asList("the", "other", "word");
            var expectedFile2Content = Arrays.asList("the", "amazing", "word");
            var expectedFile3Content = Arrays.asList("The", "search", "engine", "is",  "very", "efficient");
            assertEquals(Set.of(file1, file2, file3), result.keySet());
            assertEquals(expectedFile1Content, result.get(file1));
            assertEquals(expectedFile2Content, result.get(file2));
            assertEquals(expectedFile3Content, result.get(file3));
        }
    }

    @Test
    public void should_return_empty_map() {
        var result = inputFilesServiceSUT.readFilesContent(Stream.of(Paths.get("src/test/resources/non-existent-folder")));
        assertEquals("Error while loading lines from file : src/test/resources/non-existent-folder",
                systemErrRule.getLogWithNormalizedLineSeparator().trim());
        assertTrue(result.isEmpty());
    }

    @Test
    public void should_display_number_of_files_in_direcory() throws IOException {
        inputFilesServiceSUT.displayFilesCount("src/test/resources", "txt");
        assertEquals("3 files read in directory src/test/resources", systemOutRule.getLogWithNormalizedLineSeparator().trim());
    }
}
