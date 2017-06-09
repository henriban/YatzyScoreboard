package com.yatzy.henrikanderson.yatzy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadGameActivity extends AppCompatActivity {

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

        //Back button in actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        GameManager.Load(this);

        Intent intent = new Intent(LoadGameActivity.this, Game.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", "Yatzy");
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
