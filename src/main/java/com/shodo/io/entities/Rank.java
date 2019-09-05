package com.shodo.io.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Rank {

    private final String fileName;
    private final BigDecimal score;

    Rank(String fileName, BigDecimal score) {
        this.fileName = fileName;
        this.score = score;
    }

    public String getFileName() {
        return fileName;
    }

    public BigDecimal getScore() {
        return score;
    }

    @Override
    public String toString() {
        return fileName + " : " +
                score
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP)
                        .toString() + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rank rank = (Rank) o;
        return Objects.equals(fileName, rank.fileName) &&
                Objects.equals(score.setScale(2, RoundingMode.HALF_UP), rank.score.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, score);
    }

    static Rank calculateRank(String fileName, List<String> fileWords, List<String> searchedWords) {
        var existingWordsInFile = searchedWords.stream().filter(fileWords::contains).collect(Collectors.toList());
        return new Rank(fileName,
                BigDecimal.valueOf(existingWordsInFile.size())
                        .divide(BigDecimal.valueOf(searchedWords.size()), 4, RoundingMode.HALF_UP));
    }

    public static List<Rank> calculateRanks(Map<String, List<String>> filesContent, List<String> searchedWords) {
        List<Rank> ranks = new ArrayList<>();
        filesContent.forEach((fileName, fileWords) ->
                ranks.add(Rank.calculateRank(fileName, fileWords, searchedWords))
        );
        return ranks;
    }
}
