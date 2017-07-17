package com.yatzy.henrikanderson.yatzy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadGameActivity extends Activity {

    private LinearLayout loadLayout;

    private RelativeLayout darkBackground;
    private LinearLayout alertBox;
    private TextView alertText;

    private Button yesBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Makes the super cool background :D
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFE0D6B4,0xFFD4C490});
        gd.setCornerRadius(0f);

        relativeLayout.setBackgroundDrawable(gd);


        loadLayout = (LinearLayout) findViewById(R.id.loadLayout);

        darkBackground = (RelativeLayout) findViewById(R.id.darkBackground);
        alertBox = (LinearLayout) findViewById(R.id.alertBox);
        alertText = (TextView) findViewById(R.id.alertText);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        darkBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAlertBox();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAlertBox();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initLoadGameLayout();
    }

    private void initLoadGameLayout() {
        SaveGameModel saveGameModel = GameManager.Load(this);

        if(saveGameModel != null) {

            for (final GameModel gameModel : saveGameModel.getSavedGames()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button gameButton = new Button(this);
                gameButton.setText(gameModel.getGameName());
                gameButton.setTextSize(28);
                params.setMargins(0,7,0,7);

                gameButton.setLayoutParams(params);


                gameButton.setBackgroundResource(R.drawable.main_button);

                gameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGame(gameModel);
                    }
                });

                loadLayout.addView(gameButton);
            }
        }else{
            TextView textView = new TextView(this);
            textView.setText("Can't find any saved games");
            loadLayout.addView(textView);
        }
    }

    private void openGame(GameModel gameModel) {
        Intent intent = new Intent(LoadGameActivity.this, Game.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("gameModel", gameModel);

//        Bundle bundle = new Bundle();
//        bundle.putString("key", "Yatzy");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    private void showAlertBox(String gameName) {
        darkBackground.setVisibility(View.VISIBLE);
        alertBox.setVisibility(View.VISIBLE);

        alertText.setText("Load " + gameName);
    }

    private void hideAlertBox() {
        darkBackground.setVisibility(View.GONE);
        alertBox.setVisibility(View.GONE);
    }



}
