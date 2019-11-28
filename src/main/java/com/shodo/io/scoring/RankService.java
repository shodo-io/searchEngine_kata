package com.shodo.io.scoring;

import com.shodo.io.entities.Rank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RankService {

    /**
     * Calculate the file rank.
     * @param fileName The file name.
     * @param fileWords the words contained in the file.
     * @param searchedWords The searched words.
     * @return The rank of the file.
     */
    public Rank calculateRank(String fileName, List<String> fileWords, List<String> searchedWords) {
        var existingWordsInFile = searchedWords.stream().filter(fileWords::contains).collect(Collectors.toList());
        return new Rank(fileName,
                BigDecimal.valueOf(existingWordsInFile.size())
                        .divide(BigDecimal.valueOf(searchedWords.size()), 4, RoundingMode.HALF_UP));
    }

    /**
     * Calculate multiple files ranks. For each file, a rank is calculated, but the list is not sorted.
     * <p>If you want to sort the returned list, you must do that after calling this method.</p>
     * @param filesContent Map of files (files name) and their content (list of words).
     * @param searchedWords The searched words.
     * @return A rank for each file. This list is not sorted.
     */
    public List<Rank> calculateRanks(Map<String, List<String>> filesContent, List<String> searchedWords) {
        List<Rank> ranks = new ArrayList<>();
        filesContent.forEach((inputFileName, fileWords) ->
                ranks.add(calculateRank(inputFileName, fileWords, searchedWords))
        );
        return ranks;
    }

}
