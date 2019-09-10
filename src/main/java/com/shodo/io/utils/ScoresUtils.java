package com.shodo.io.utils;

import com.shodo.io.entities.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class ScoresUtils {

    private ScoresUtils() {
        throw new AssertionError(ScoresUtils.class.getName() + " can not be instantiated.");
    }

    public static boolean calculateScores(Map<String, List<String>> filesContent) {
        var searchedWords = PromptUtils.readSearchedWords();
        if (searchedWords.size() == 1 && searchedWords.contains("exit")) {
            System.out.println("Exiting...");
            return true;
        }
        var ranks = Rank.calculateRanks(filesContent, searchedWords);
        ranks.sort(Comparator.comparing(Rank::getScore).reversed());
        ranks.stream().limit(10).forEach(System.out::println);
        return false;
    }

}
