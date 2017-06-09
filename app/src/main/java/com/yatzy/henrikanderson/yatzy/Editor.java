package com.yatzy.henrikanderson.yatzy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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

        table = (TableLayout) findViewById(R.id.table);
        newRuleLayout = (RelativeLayout) findViewById(R.id.newRuleParentLayout);
        save = (Button) findViewById(R.id.saveBtn);
        cancelEditor = (Button) findViewById(R.id.cancelBtn);

        name = (TextView) findViewById(R.id.name);
        maxPoint = (TextView) findViewById(R.id.maxPoint);
        ruleDescription = (TextView) findViewById(R.id.ruleDescription);

        initTable();
    }

    /**
     * Setup for text views, sets the drawable and text
     * @param text the string you want to display in the textView
     * @return a new textView with a background and text
     */
    private TextView setUpTextView(String text){
        final TextView textView = new TextView(this);
        textView.setText(text);
        if(!(text.equals("Scoring") || text.equals("Max"))){
            textView.setBackground(getResources().getDrawable(R.drawable.text_view));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRules(textView.getText().toString());

                }
            });
        }
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    private void showRules(String s) {

    }

    private void initTable() {
/*
        //First row
        TableRow row1 = new TableRow(this);
        TextView textView = new TextView(this);
        textView.setText("Scoring");
        textView.setMinWidth(250);
        textView.setGravity(Gravity.CENTER);

        row1.addView(textView);
        row1.addView(setUpTextView("Max"));
        table.addView(row1);*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            System.out.println("Save");
            return true;
        }

        if (id == R.id.action_saveAs) {
            System.out.println("Save As");
            return true;
        }

        if (id == R.id.action_load) {
            System.out.println("Load");
            return true;
        }

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    private TableLayout table;
    private RelativeLayout newRuleLayout;
    private Button save;
    private Button cancelEditor;

    private TextView name;
    private TextView maxPoint;
    private TextView ruleDescription;

    public void cancelNewRule(View view) {
        newRuleLayout.setVisibility(View.GONE);
    }

    public void addNewRule(View view) {
        String nameToSave = name.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("yatzySaves", MODE_PRIVATE);
            fileOutputStream.write(nameToSave.getBytes());
            fileOutputStream.close();
            name.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //newRuleLayout.setVisibility(View.GONE);

        try {
            String input;
            FileInputStream fileInputStream = openFileInput("yatzySaves");
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            while((input=bufferedReader.readLine()) != null){
                stringBuffer.append(input + "\n");
            }
            ruleDescription.setText(stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewRuleEditor(View view){
        newRuleLayout.setVisibility(View.VISIBLE);

    }

    public void saveNewRule(View view) {
    }

    public void cancelEditor(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
