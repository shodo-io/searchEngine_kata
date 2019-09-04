package com.shodo.io;

import com.shodo.io.entities.Rank;
import com.shodo.io.exceptions.ScoresEngineInputException;
import com.shodo.io.utils.FilesUtils;
import com.shodo.io.utils.PromptUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public class ScoresEngine {

    /**
     * Search Engine main function. It takes the words given on the prompt and return
     * a list of the top 10 matching filenames in rank order.
     *
     * @param args Program arguments containing the directory in which research is done.
     */
    public static void main(String[] args) throws ScoresEngineInputException {
        if (args.length == 0) {
            throw new ScoresEngineInputException("No directory given to index. Please type directory containing files to read.");
        }
        try {
            var directory = args[0];
            FilesUtils.displayFilesCount(directory);
            readFilesAndShowScores(directory);
        } catch (IOException e) {
            System.err.println("The program can't find the specified folder. Please type an existing one.");
        }
    }

    private static void readFilesAndShowScores(String directory) throws IOException {
        try (var filesPaths = Files.list(Paths.get(directory))) {
            var filesContent = FilesUtils.readFilesContent(filesPaths);
            while (true) {
                var searchedWords = PromptUtils.readSearchedWords();
                if (searchedWords.size() == 1 && searchedWords.contains("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                var ranks = Rank.calculateRanks(filesContent, searchedWords);
                ranks.sort(Comparator.comparing(Rank::getFileName));
                ranks.forEach(System.out::println);
            }
        }
    }
}
