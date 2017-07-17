package com.yatzy.henrikanderson.yatzy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveGameModel implements Serializable{

    private List<GameModel> savedGames;

    public SaveGameModel() {
        savedGames = new ArrayList<>();
    }

    public List<GameModel> getSavedGames() {
        return savedGames;
    }

    public void setSavedGames(List<GameModel> savedGames) {
        this.savedGames = savedGames;
    }

    public GameModel getGame(String gameName){
        for(GameModel gameModel : savedGames){
            if(gameModel.getGameName().equals(gameName)){
                return gameModel;
            }
        }
        return null;
    }

    public void addGame(GameModel game){
        savedGames.add(game);
    }
}
