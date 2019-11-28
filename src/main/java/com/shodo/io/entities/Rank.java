package com.shodo.io.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rank of a file. This class contains the file name and its score.
 * <p>This class is immutable</p>
 */
public class Rank {

    private final String fileName;
    private final BigDecimal score;

    /**
     * Parameterized constructor.
     * @param fileName the file name
     * @param score The file Score
     */
    public Rank(String fileName, BigDecimal score) {
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

}
