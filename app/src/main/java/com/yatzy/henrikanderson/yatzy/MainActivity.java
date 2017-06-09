package com.yatzy.henrikanderson.yatzy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Makes the super cool background :D
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFE0D6B4,0xFFD4C490});
        gd.setCornerRadius(0f);

        relativeLayout.setBackgroundDrawable(gd);
    }

    public void yatzy(View view){
        Intent intent = new Intent(MainActivity.this, Game.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", "Yatzy");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }

    public void maxiYatzy(View view){
        Intent intent = new Intent(MainActivity.this, Game.class);
        Bundle bundle = new Bundle();
        bundle.putString("key", "Maxi Yatzy");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void editor(View view){
        startActivity(new Intent(this, Editor.class));
        finish();
    }

    public void loadGame(View view){

    }

    public void options(View view){

    }
}
