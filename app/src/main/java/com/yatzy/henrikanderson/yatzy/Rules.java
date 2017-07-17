package com.yatzy.henrikanderson.yatzy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

class Rules implements Serializable {

    private List<String> scoring;
    private List<String> maxScore;
    private List<String> minScore;
    private List<String> rules;

    private String gameType;

    private int requiredPointsForBonus;
    private int bonus;

    public Rules(String gameType) {
        this.gameType = gameType;
        if (gameType.equals("Yatzy")) {
            scoring = Arrays.asList("Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Bonus",
                    "One Pair", "Two Pairs", "Three of a Kind", "Four of a Kind", "Small Straight", "Large Straight",
                    "Full House", "Chance", "Yatzy", "Total");
            maxScore = Arrays.asList("5", "10", "15", "20", "25", "30", "50",
                    "12", "22", "18", "24", "15", "20",
                    "28", "30", "50", "375");
            //min valid score
            minScore = Arrays.asList("1", "2", "3", "4", "5", "6", "0",
                    "2", "6", "3", "4", "0", "0",
                    "7", "6", "0", "49");

            rules = Arrays.asList(
                    "The sum of all dice showing the number 1.",
                    "The sum of all dice showing the number 2.",
                    "The sum of all dice showing the number 3.",
                    "The sum of all dice showing the number 4.",
                    "The sum of all dice showing the number 5.",
                    "The sum of all dice showing the number 6.",
                    "If a player manages to score at least 63 points (an average of three of each number) in the upper section, they are awarded a bonus of 50 points.",

                    "Two dice showing the same number. \n\nScore: Sum of those two dice.",
                    "Two different pairs of dice. \n\nScore: Sum of dice in those two pairs.",
                    "Three dice showing the same number. \n\nScore: Sum of those three dice.",
                    "Four dice with the same number. \n\nScore: Sum of those four dice.",
                    "The combination 1-2-3-4-5. \n\nScore: 15 points (sum of all the dice).",
                    "The combination 2-3-4-5-6. \n\nScore: 20 points (sum of all the dice).",
                    "Any set of three combined with a different pair. \n\nScore: Sum of all the dice.",
                    "Any combination of dice. \n\nScore: Sum of all the dice.",
                    "All five dice with the same number. \n\nScore: 50 points.",
                    "Total score");
            bonus = 50;
            requiredPointsForBonus = 63;

        } else if (gameType.equals("Maxi Yatzy")) {
            scoring = Arrays.asList("Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Bonus",
                    "One Pair", "Two Pairs", "Three Pairs",
                    "Three of a Kind", "Four of a Kind", "Five of a Kind", "Small Straight", "Large Straight", "Full Straight",
                    "Full House", "Castle", "Tower",
                    "Chance", "Maxi Yatzy", "Total");
            maxScore = Arrays.asList("6", "12", "18", "24", "30", "36", "100",
                    "12", "22", "30",
                    "18", "24", "30", "15", "20", "21",
                    "28", "33", "34",
                    "36", "100", "375");
            rules = Arrays.asList(
                    "The sum of all dice showing the number 1.",
                    "The sum of all dice showing the number 2.",
                    "The sum of all dice showing the number 3.",
                    "The sum of all dice showing the number 4.",
                    "The sum of all dice showing the number 5.",
                    "The sum of all dice showing the number 6.",
                    "If a player manages to score at least 84 points (an average of four of each number) in the upper section, they are awarded a bonus of 100 points.",

                    "Two dice showing the same number. \n\nScore: Sum of those two dice.", // One Pair
                    "Two different pairs of dice. \n\nScore: Sum of dice in those two pairs.", // Two Pair
                    "Three different pairs of dice. \n\nScore: Sum of dice in those three pairs.", // Three Pairs

                    "Three dice showing the same number. \n\nScore: Sum of those three dice.", // Three of a Kind
                    "Four dice showing the same number. \n\nScore: Sum of those four dice.", //Four of a Kind
                    "Five dice with the same number. \n\nScore: Sum of those five dice.", // Five of a Kind
                    "The combination 1-2-3-4-5. \n\nScore: 15 points (sum of all the dice).", // Small Straight
                    "The combination 2-3-4-5-6. \n\nScore: 20 points (sum of all the dice).", // Large Straight
                    "The combination 1-2-3-4-5-6. Score: 21 points (sum of all the dice).", // Full Straight

                    "Any set of three combined with a different pair. \n\nScore: Sum of all the dice.", // Full House
                    "Two sets of three dice showing the same number. Score: Sum of all the dice.", // Castle
                    "A set of four combined with a set of two. Score: Sum of all the dice.", // Tower

                    "Any combination of dice. \n\nScore: Sum of all the dice.",  // Chance
                    "All six dice with the same number. \n\nScore: 100 points.", // Maxi Yatzy
                    "Total score");  // Total
            bonus = 100;
            requiredPointsForBonus = 84;
        }
    }

    public List<String> getScoring() {
        return scoring;
    }

    public List<String> getMaxScore() {
        return maxScore;
    }

    public List<String> getRules() {
        return rules;
    }

    public String getGameType() {
        return gameType;
    }

    public int getRequiredPointsForBonus() {
        return requiredPointsForBonus;
    }

    public List<String> getMinScore() {
        return minScore;
    }

    public int getBonus() {
        return bonus;
    }


}
