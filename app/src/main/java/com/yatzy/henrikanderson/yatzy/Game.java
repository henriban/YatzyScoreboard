package com.yatzy.henrikanderson.yatzy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends AppCompatActivity {


    private TableLayout table;
    private int numOfPlayers = 4;

    //private List<Integer> colors = Arrays.asList(Color.argb(1, 200, 0, 0), Color.argb(1, 0, 200, 0));

    private List<String> scoring = Arrays.asList("Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Bonus",
            "One Pair", "Two Pairs", "Three of a Kind", "Four of a Kind", "Small Straight", "Large Straight",
            "Full House", "Chance", "Yatzy", "Total");
    private List<String> maxScore = Arrays.asList("5", "10", "15", "20", "25", "30", "50",
            "12", "22", "18", "24", "15", "20",
            "28", "30", "50", "375");
    //min valid score
    private List<String> minScore = Arrays.asList("1", "2", "3", "4", "5", "6", "0",
            "2", "6", "3", "4", "0", "0",
            "7", "6", "0", "49");

    private List<String> rules = Arrays.asList(
            "The sum of all dice showing the number 1." ,
            "The sum of all dice showing the number 2." ,
            "The sum of all dice showing the number 3." ,
            "The sum of all dice showing the number 4." ,
            "The sum of all dice showing the number 5." ,
            "The sum of all dice showing the number 6." ,
            "If a player manages to score at least 63 points (an average of three of each number) in the upper section, they are awarded a bonus of 50 points." ,

            "Two dice showing the same number. \n\nScore: Sum of those two dice." ,
            "Two different pairs of dice. \n\nScore: Sum of dice in those two pairs." ,
            "Three dice showing the same number. \n\nScore: Sum of those three dice." ,
            "Four dice with the same number. \n\nScore: Sum of those four dice." ,
            "The combination 1-2-3-4-5. \n\nScore: 15 points (sum of all the dice)." ,
            "The combination 2-3-4-5-6. \n\nScore: 20 points (sum of all the dice)." ,
            "Any set of three combined with a different pair. \n\nScore: Sum of all the dice." ,
            "Any combination of dice. \n\nScore: Sum of all the dice." ,
            "All five dice with the same number. \n\nScore: 50 points.",
            "Total score");  //Tester dette

    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupUI(findViewById(R.id.parent));

        //Back button in actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Catch a string gameType from main
        //Tells what game is it
        Bundle bundle = getIntent().getExtras();
        gameType = "null";
        if(bundle != null){
            gameType = bundle.getString("key");
        }
        initGameType(gameType);

        //init elements from activity
        table = (TableLayout) findViewById(R.id.table);
        ruleBox = (LinearLayout) findViewById(R.id.ruleBox);
        row1 = new TableRow(this);
        darkBackground = (LinearLayout) findViewById(R.id.darkBackground);
        alertBox = (LinearLayout) findViewById(R.id.alertBox);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        extraLayout = (LinearLayout) findViewById(R.id.extraLayout);


        // Makes the super cool background :D
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.parent);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFE0D6B4,0xFFD4C490});
        gd.setCornerRadius(0f);

        relativeLayout.setBackgroundDrawable(gd);

        //initialize the sheet
        buildFirstRow();
        init();

        //update the sheet to show 0's in "bonus" and "total"
        setTotalScore();
    }

    /**
     * Init the game type (the rules of the game)
     * @param value is a String with the game type.
     */

    private void initGameType(String value) {
        if(value.equals("Yatzy")){
            requiredPointsForBonus = 63;
        }else if(value.equals("Maxi Yatzy")){
            this.scoring = Arrays.asList("Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Bonus",
                    "One Pair", "Two Pairs", "Three Pairs",
                    "Three of a Kind", "Four of a Kind", "Five of a Kind", "Small Straight", "Large Straight", "Full Straight",
                    "Full House", "Castle" ,"Tower",
                    "Chance", "Maxi Yatzy", "Total");
            this.maxScore = Arrays.asList("6", "12", "18", "24", "30", "36", "100",
                    "12", "22", "30",
                    "18", "24", "30","15", "20", "21",
                    "28","33", "34",
                    "36", "100", "375");
            rules = Arrays.asList(
                    "The sum of all dice showing the number 1." ,
                    "The sum of all dice showing the number 2." ,
                    "The sum of all dice showing the number 3." ,
                    "The sum of all dice showing the number 4." ,
                    "The sum of all dice showing the number 5." ,
                    "The sum of all dice showing the number 6." ,
                    "If a player manages to score at least 84 points (an average of four of each number) in the upper section, they are awarded a bonus of 100 points." ,

                    "Two dice showing the same number. \n\nScore: Sum of those two dice." , // One Pair
                    "Two different pairs of dice. \n\nScore: Sum of dice in those two pairs." , // Two Pair
                    "Three different pairs of dice. \n\nScore: Sum of dice in those three pairs.", // Three Pairs

                    "Three dice showing the same number. \n\nScore: Sum of those three dice." , // Three of a Kind
                    "Four dice showing the same number. \n\nScore: Sum of those four dice.", //Four of a Kind
                    "Five dice with the same number. \n\nScore: Sum of those five dice." , // Five of a Kind
                    "The combination 1-2-3-4-5. \n\nScore: 15 points (sum of all the dice)." , // Small Straight
                    "The combination 2-3-4-5-6. \n\nScore: 20 points (sum of all the dice)." , // Large Straight
                    "The combination 1-2-3-4-5-6. Score: 21 points (sum of all the dice).", // Full Straight

                    "Any set of three combined with a different pair. \n\nScore: Sum of all the dice." , // Full House
                    "Two sets of three dice showing the same number. Score: Sum of all the dice.", // Castle
                    "A set of four combined with a set of two. Score: Sum of all the dice.", // Tower

                    "Any combination of dice. \n\nScore: Sum of all the dice." ,  // Chance
                    "All six dice with the same number. \n\nScore: 100 points." , // Maxi Yatzy
                    "Total score");  // Total
            requiredPointsForBonus = 84;
            bonus = 100;
        }

        //TODO: Valid all maxScore is int
        bonus = Integer.parseInt(maxScore.get(6));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_restart) {
            System.out.println("Restart");
            restart();
            return true;
        }
        if (id == R.id.action_save) {
            save();
            return true;
        }

        if (id == R.id.action_saveAs) {
            saveAs();
            return true;
        }

        if (id == R.id.action_load) {
            load();
            return true;
        }

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

        //return super.onOptionsItemSelected(item);
    }

    private void save() {
        GameModel gameModel = new GameModel("Game1", gameType, numOfPlayers, getPlayersName(), getPlayersScore());
        GameManager.Save(gameModel, this);
    }

    private List<String> getPlayersName(){
        List<String> playersNames = new ArrayList<>();

        for(int i = 2;  i <= numOfPlayers + 1; i ++) {
            EditText editText = (EditText) row1.getChildAt(i);
            String name = editText.getText().toString();
            playersNames.add(name);
        }

        return playersNames;
    }

    private List<List<Integer>> getPlayersScore(){
        List<List<Integer>> playersScore = new ArrayList<>();

        for(int player = 0; player < numOfPlayers; player++) {
            List<Integer> playerScore = new ArrayList<>();
            for (int i = 0; i < playerEditTextList.get(player).size() - 1; i++) {
                String value = playerEditTextList.get(player).get(i).getText().toString();
                if (!value.isEmpty()) {
                    playerScore.add(Integer.parseInt(value));
                }else{
                    playerScore.add(0);
                }
            }
            playersScore.add(playerScore);
        }

        return playersScore;
    }

    private void saveAs() {

    }

    private void load() {
        GameModel gameModel = GameManager.Load(this);

        if(gameModel != null){

            numOfPlayers = gameModel.getNumbersOfPlayers();

            gameType = gameModel.getGameType();
            initGameType(gameType);

            init();


            //Set players names
            for (int i = 0; i < numOfPlayers - 1; i++){
                View view = row1.getChildAt(i + 2);
                if(view instanceof EditText){
                    ((EditText) view).setText(gameModel.getPlayersName().get(i));
                }

                // Set players scores
                for(int j = 0; j < gameModel.getPlayersScore().get(i).size() - 1; j++){
                    playerEditTextList.get(i).get(j).setText(String.valueOf(gameModel.getPlayersScore().get(i).get(j)));
                }
            }
            setTotalScore();

        }else{
            System.out.println("GameModel --> NullPoint ");
        }
    }

    Button yesBtn;
    LinearLayout extraLayout;

    private void restart() {
        showAlert();
        TextView text = (TextView) findViewById(R.id.alertText);
        text.setText("Do you want to restart? \n ");

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Starts a new game with the same game type (gameType)
                Intent intent = new Intent(Game.this, Game.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", gameType);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Making the rows for scoring and max score + columns for two players (or numberOfPlayers)
     */

    private List<List<EditText>> playerEditTextList = new ArrayList<>();
    private List<TableRow> rows = new ArrayList<>();

    private void init(){

        for(int i = 0; i < numOfPlayers; i++){
            List<EditText> editTextList = new ArrayList<>();
            playerEditTextList.add(editTextList);
        }

        for(int i = 0; i < scoring.size(); i++){
            TableRow row = new TableRow(this);

            row.addView(setUpTextView(scoring.get(i)));
            row.addView(setUpTextView(maxScore.get(i)));

            for(int j = 0; j < numOfPlayers; j++){
                EditText editText = setUpEditText();
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                if(i == 6 || i == scoring.size() - 1){
                    editText.setBackground(getResources().getDrawable(R.drawable.text_not_editable));
                    editText.setFocusable(false);
                }

                playerEditTextList.get(j).add(editText);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            checkValidInput();
                            setTotalScore();

                        }
                    }
                });
                row.addView(editText);
            }
            rows.add(row);
            table.addView(row);
        }
    }

    /**
     * Corrects the number
     */

    private void checkValidInput() {
        for(int i = 0; i < numOfPlayers; i++){
            for(int j = 0; j < playerEditTextList.get(i).size(); j++){
                if(!playerEditTextList.get(i).get(j).getText().toString().trim().isEmpty()){
                    if(Integer.parseInt(String.valueOf(playerEditTextList.get(i).get(j).getText())) > Integer.parseInt(maxScore.get(j))){
                        playerEditTextList.get(i).get(j).setText(maxScore.get(j));
                    }
                }
            }
        }
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
            if(!text.matches("^-?\\d+$")){
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRules(textView.getText().toString());
                    }
                });
            }
        }
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    public EditText setUpEditText(){
        EditText editText = new EditText(this);
        editText.setBackground(getResources().getDrawable(R.drawable.text_edit));
        editText.setMinWidth(75);
        editText.setGravity(Gravity.CENTER);
        return editText;
    }


    //////////////// ALERT AND RULE BOX ///////////////

    private void showAlert(){
        hideSoftKeyboard(Game.this);
        alertBox.setVisibility(View.VISIBLE);
        darkBackground.setVisibility(View.VISIBLE);
        extraLayout.removeAllViews();
        yesBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Show a rule box with the rule of the combination
     * @param a string with the text/rule to show
     */

    LinearLayout ruleBox;
    LinearLayout darkBackground;
    LinearLayout alertBox;

    private void showRules(String text) {
        /*
        if(scoring.contains(text) && scoring.indexOf(text) != 16){
            int index = scoring.indexOf(text);
            System.out.println(index);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialog));
            builder.setTitle(text)
                    .setMessage(rules.get(index).toString())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }*/

        ruleBox.setVisibility(View.VISIBLE);
        darkBackground.setVisibility(View.VISIBLE);

        TextView scoringText = (TextView) findViewById(R.id.scoringText);
        TextView ruleText = (TextView) findViewById(R.id.ruletext);
        TextView okTextBtn = (TextView) findViewById(R.id.okTextBtn);

        //TODO: Tester dette
        //if(scoring.contains(text) && scoring.indexOf(text) != 16) {
        if(scoring.contains(text)){
            int index = scoring.indexOf(text);
            ruleBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.rulebox));
            scoringText.setText(text);
            ruleText.setText(rules.get(index));

            okTextBtn.setText(R.string.gotIt);
            okTextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideRules();
                }
            });
        }
    }

    private void hideRules(){
        ruleBox.setVisibility(View.INVISIBLE);
        alertBox.setVisibility(View.GONE);
        darkBackground.setVisibility(View.INVISIBLE);
    }

    //Some times AStudio is stupid and you need to restart it too it to work...
    // Hide the rule box bye pressing the dark background
    public void hideRule(View view) {
        hideRules();
    }

    public void hideAlertBox(View view) {
        alertBox.setVisibility(View.GONE);
        darkBackground.setVisibility(View.INVISIBLE);
    }




    //////////////// THE GAME ///////////////


    /**
     * Making the first row with player names and scoring
     */
    private TableRow row1;

    private void buildFirstRow() {

        row1.addView(setUpTextView("Scoring"));
        row1.addView(setUpTextView("Max"));

        for(int i = 0; i < numOfPlayers; i++){
            EditText text = new EditText(this);
            row1.addView(text);
        }

        // Players name

        final Button addPlayersBtn = new Button(this);
        addPlayersBtn.setText("+");
        addPlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer(addPlayersBtn);
            }
        });
        row1.addView(addPlayersBtn);
        row1.setBackground(getResources().getDrawable(R.drawable.row1));

        table.addView(row1);
    }

    /**
     * Adds a new player (and then also rows) to the game
     * @param btn the button has to be deleted and changed to a new one
     */

    private void addPlayer(Button btn) {
        EditText playerEditText = new EditText(this);
        row1.removeView(btn);
        row1.addView(playerEditText);

        // Just making sure that the button is the last item in the row
        final Button addPlayersBtn = new Button(this);
        addPlayersBtn.setText("+");
        addPlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer(addPlayersBtn);
            }
        });
        row1.addView(addPlayersBtn);

        numOfPlayers++;
        playerEditTextList.add(new ArrayList<EditText>());

        //Makes a new column with num edit texts

        for(TableRow row : rows){
            EditText editText = setUpEditText();
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            playerEditTextList.get(numOfPlayers - 1).add(editText);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        checkValidInput();
                        setTotalScore();

                    }
                }
            });
            row.addView(editText);
        }
    }


    /**
     * Setts the total score for all players
     */

    private int requiredPointsForBonus = 63;
    private int bonus = 50;

    private void setTotalScore(){
        for(int p = 0; p < numOfPlayers; p++) {
            int total = 0;
            for (int i = 0; i < playerEditTextList.get(p).size() - 1; i++) {
                if (!playerEditTextList.get(p).get(i).getText().toString().isEmpty()) {

                    if (i == 6 && Integer.parseInt(playerEditTextList.get(p).get(6).getText().toString()) < requiredPointsForBonus) {
                        playerEditTextList.get(p).get(6).setTextColor(Color.RED);
                    } else {
                        total += Integer.parseInt(playerEditTextList.get(p).get(i).getText().toString());
                        playerEditTextList.get(p).get(6).setTextColor(Color.BLACK);
                    }
                }
            }

            // Check for bonus
            int bonusCheck = 0;

            for (int i = 0; i < 6; i++) {
                if (!playerEditTextList.get(p).get(i).getText().toString().isEmpty()) {
                    bonusCheck += Integer.parseInt(playerEditTextList.get(p).get(i).getText().toString());
                    if (bonusCheck >= requiredPointsForBonus) {
                        playerEditTextList.get(p).get(6).setTextColor(Color.BLACK);
                        bonusCheck = bonus;
                        total += bonus;
                        break;
                    }
                }
            }

            String strBonus = String.valueOf(bonusCheck);
            playerEditTextList.get(p).get(6).setText(strBonus);

            String str = String.valueOf(total);
            playerEditTextList.get(p).get(playerEditTextList.get(p).size() - 1).setText(str);
        }
    }



    //////////////// THE KEYBOARD ///////////////


    // hide the soft keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //Copy paste kode jeg ikke vet helt hvordan funker, men den gjemmer keyboardet om du trykker en plass utenfor det
    public void setupUI(View view) {

        //Setter opp en lytter som sjekker om du trykker utenfor tekstfeltet og keyboardet
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Game.this);
                    setTotalScore();
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion (Copy-paste kommentar)
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

}
