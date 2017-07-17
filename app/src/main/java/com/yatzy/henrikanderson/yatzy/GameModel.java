package com.yatzy.henrikanderson.yatzy;

import java.io.Serializable;
import java.util.List;

public class GameModel implements Serializable {

    private String gameName;
    private String gameType;

    private Rules rules;

    private int numbersOfPlayers;

    private List<String> playersName;
    private List<List<Integer>> playersScore;

    public GameModel(String gameName, Rules rules, int numbersOfPlayers, List<String> playersName, List<List<Integer>> playersScore) {
        this.gameName = gameName;
        this.gameType = rules.getGameType();
        this.rules = rules;
        this.numbersOfPlayers = numbersOfPlayers;
        this.playersName = playersName;
        this.playersScore = playersScore;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getNumbersOfPlayers() {
        return numbersOfPlayers;
    }

    public void setNumbersOfPlayers(int numbersOfPlayers) {
        this.numbersOfPlayers = numbersOfPlayers;
    }

    public List<String> getPlayersName() {
        return playersName;
    }

    public void setPlayersName(List<String> playersName) {
        this.playersName = playersName;
    }

    public List<List<Integer>> getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(List<List<Integer>> playersScore) {
        this.playersScore = playersScore;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }
}
