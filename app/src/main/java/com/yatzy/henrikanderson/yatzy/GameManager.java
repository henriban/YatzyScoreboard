package com.yatzy.henrikanderson.yatzy;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class GameManager {

    private static GameModel game;

    /**
     * GameManager is Singleton
     * @param context Context of the application
     * @return The player instance
     */

    public static GameModel getGameInstance(Context context) {
        if (game != null) {
            return game;
        }
        return Load(context);
    }

    /**
     * Load game from disk
     * @param activity Activity in which context we are
     * @return GameModel object
     */

    public static GameModel Load(Context activity){
        try {

            GameModel game;

            File mFolder = new File(activity.getFilesDir()+"/Game");
            File itemFile = new File(mFolder.getAbsolutePath()+"/gameFile");
            if (!mFolder.exists()) {
                return null;
            }
            if (!itemFile.exists()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(itemFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();

            if(object == null){
                objectInputStream.close();
                return null;
            }
            game = (GameModel) object;
            objectInputStream.close();

            return game;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Save game state to disk
     * @param game GameModel object to save
     * @param activity Activity in which you access files
     */

    public static void Save(GameModel game, Activity activity){
        try {

            File mFolder = new File(activity.getFilesDir()+"/Game");
            File itemFile = new File(mFolder.getAbsolutePath()+"/gameFile");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            if (!itemFile.exists()) {
                itemFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(itemFile);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fos);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}








