package com.shodo.io.scoring;

import com.shodo.io.entities.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ScoresService {


    private UserPromptService userPromptService;
    private RankService rankService;

    public ScoresService(UserPromptService userPromptService, RankService rankService) {
        this.userPromptService = userPromptService;
        this.rankService = rankService;
    }

    public boolean calculateScores(Map<String, List<String>> filesContent) {
        var searchedWords = userPromptService.readSearchedWords();
        if (searchedWords.size() == 1 && searchedWords.contains("exit")) {
            System.out.println("Exiting...");
            return true;
        }
        var ranks = rankService.calculateRanks(filesContent, searchedWords);
        ranks.sort(Comparator.comparing(Rank::getScore).reversed());
        ranks.stream().limit(10).forEach(System.out::println);
        return false;
    }

}
