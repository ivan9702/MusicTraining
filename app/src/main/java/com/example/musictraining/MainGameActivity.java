package com.example.musictraining;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import info.hoang8f.widget.FButton;

import static android.os.SystemClock.sleep;

public class MainGameActivity extends Activity {
    int QuestType =0;
    int showAnswerSec=1000;
    static int qIndex=0;
    String TAG = "Main";
    FButton buttonA, buttonB, buttonC, buttonD;
    TextView questionText, triviaQuizText, timeText, resultText,tvStars,tvErrStars;
    ImageView ivcorrect;
    TriviaQuizHelper triviaQuizHelper;
    TriviaQuestion currentQuestion;
    List<TriviaQuestion> list;
    int qid = 0;
    int timeValue =0;
    int coinValue = 0;
    int errCount=0;
    int timer=0;
    CountDownTimer countDownTimer;
    Typeface tb, sb;
    SharedPreferences sharedata;
    SharedPreferences.Editor editor;
    Calendar mCal;
    CharSequence date;
//    Dialog dialoginCorrect;
//    Dialog dialogCorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_game);
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        //Initializing variables
        questionText = (TextView) findViewById(R.id.triviaQuestion);
        buttonA = (FButton) findViewById(R.id.buttonA);
        buttonB = (FButton) findViewById(R.id.buttonB);
        buttonC = (FButton) findViewById(R.id.buttonC);
        buttonD = (FButton) findViewById(R.id.buttonD);
        triviaQuizText = (TextView) findViewById(R.id.triviaQuizText);
        timeText = (TextView) findViewById(R.id.timeText);
        resultText = (TextView) findViewById(R.id.resultText);

        tvStars = (TextView)findViewById(R.id.tvStars);
        tvErrStars = (TextView)findViewById(R.id.tvErrStars);

        ivcorrect = (ImageView)findViewById(R.id.ivcorrect);
        //Setting typefaces for textview and buttons - this will give stylish fonts on textview and button etc
        tb = Typeface.createFromAsset(getAssets(), "fonts/TitilliumWeb-Bold.ttf");
        sb = Typeface.createFromAsset(getAssets(), "fonts/shablagooital.ttf");
        triviaQuizText.setTypeface(sb);
        questionText.setTypeface(tb);
        buttonA.setTypeface(tb);
        buttonB.setTypeface(tb);
        buttonC.setTypeface(tb);
        buttonD.setTypeface(tb);
        timeText.setTypeface(tb);
        resultText.setTypeface(sb);
      //  coinText.setTypeface(tb);

        mCal = Calendar.getInstance();
        date =  DateFormat.format("yyyyMMdd ", mCal.getTime());

        //Our database helper class
        triviaQuizHelper = new TriviaQuizHelper(this);
        //Make db writable
        triviaQuizHelper.getWritableDatabase();


        Log.d(TAG, "brfore trivia Question..");

        //It will check if the ques,options are already added in table or not
        //If they are not added then the getAllOfTheQuestions() will return a list of size zero
        if (triviaQuizHelper.getAllOfTheQuestions().size() == 0) {
            //If not added then add the ques,options in table
            Log.d(TAG, "in all trivia Question..");
            triviaQuizHelper.allQuestion();
        }

        Log.d(TAG, "get all trivia Question..");
        //This will return us a list of data type TriviaQuestion
        list = triviaQuizHelper.getAllOfTheQuestions();
        Log.d(TAG, "shuffle list..");
        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        Collections.shuffle(list);

        //currentQuestion will hold the que, 4 option and ans for particular id
        currentQuestion = list.get(qid);

        timeValue = sharedata.getInt("ClockTimer",20);
        timer = timeValue*1000+2000;
        Log.d(TAG, "timer ...."+timer);
        Log.d(TAG, "timeValue ...."+timeValue);
        //countDownTimer
        countDownTimer = new CountDownTimer(timer, 1000) {
            public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to timeText
                if(timeValue>=10)
                {
                    timeText.setText(String.valueOf(timeValue) + "\"");
                }else
                {
                    timeText.setTextColor(getResources().getColor(R.color.colorAccent));
                    timeText.setText("0"+String.valueOf(timeValue) + "\"");
                }

                //With each iteration decrement the time by 1 sec
                timeValue -= 1;
                Log.d(TAG, "timeValue"+timeValue);
                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
                    resultText.setText(getString(R.string.timeup));

                    //Since user is out of time he won't be able to click any buttons
                    //therefore we will disable all four options buttons using this method

                    disableButton();
                    showTheAnswer();
                }
            }

            //Now user is out of time
            public void onFinish() {
                //We will navigate him to the time up activity using below method
                timeUp();
            }
        }.start();

        if(!sharedata.getString("date", "0").equals(date.toString()))
        {
            Log.d(TAG, "Reset Question Indwx" );
            qIndex=0;
        }


        qIndex++;
        //This method will set the que and four options
        updateQueAndOptions();


    }


    public void updateQueAndOptions() {

        QuestType = currentQuestion.getQuestType();
        Log.d(TAG, "QuestType=" + QuestType);

        new Thread(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                // 您要在執行緒作的事
                sleep(1000);
                MainActivity.playSoundFile(currentQuestion.getWavPath());
            }
        }).start();

        //sharedata.getInt("maxquest", 30)
        //int id=0;
        //This method will setText for que and options
        //questionText.setText(currentQuestion.getQuestion());
        questionText.setText(String.valueOf(qIndex)+ ".  " +currentQuestion.getQuestion());
        qIndex++;
//********** NOTE NAME QUESTION******************//
    if(QuestType == 1) {
        String letter = "";
        FButton btn = null;
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {letter = currentQuestion.getOptA(); btn = buttonA;}
            if (i == 2) {letter = currentQuestion.getOptB(); btn = buttonB;}
            if (i == 3) {letter = currentQuestion.getOptC(); btn = buttonC;}
            if (i == 4) {letter = currentQuestion.getOptD(); btn = buttonD;}

            Log.d(TAG, "Letter=" + letter);
            switch (letter) {

                case "A":
                    btn.setBackgroundResource(R.drawable.la);
                    break;
                case "B":
                    btn.setBackgroundResource(R.drawable.si);
                    break;
                case "C":
                    btn.setBackgroundResource(R.drawable.doo);
                    break;
                case "D":
                    btn.setBackgroundResource(R.drawable.re);
                    break;
                case "E":
                    btn.setBackgroundResource(R.drawable.mi);
                    break;
                case "F":
                    btn.setBackgroundResource(R.drawable.fa);
                    break;
                case "G":
                    btn.setBackgroundResource(R.drawable.sol);
                    break;
            }
        }

    }//********** CLEFS QUESTION******************//
       else if(QuestType == 2) {
            String letter = "";
            FButton btn = null;
            for (int i = 1; i <= 4; i++) {
                if (i == 1) {letter = currentQuestion.getOptA(); btn = buttonA;}
                if (i == 2) {letter = currentQuestion.getOptB(); btn = buttonB;}
                if (i == 3) {letter = currentQuestion.getOptC(); btn = buttonC;}
                if (i == 4) {letter = currentQuestion.getOptD(); btn = buttonD;}

                Log.d(TAG, "Letter=" + letter);
                switch (letter) {

                    case "B":
                        btn.setBackgroundResource(R.drawable.bass);
                        break;
                    case "T":
                        btn.setBackgroundResource(R.drawable.treble);
                        break;
                    case "S":
                        btn.setBackgroundResource(R.drawable.sharp);
                        break;
                    case "F":
                        btn.setBackgroundResource(R.drawable.flat);
                        break;
                    case "C":
                        btn.setBackgroundResource(R.drawable.crescendo);
                        break;
                    case "D":
                        btn.setBackgroundResource(R.drawable.decrescendo);
                        break;
                    case "R":
                        btn.setBackgroundResource(R.drawable.repeat);
                        break;
                    case "L":
                        btn.setBackgroundResource(R.drawable.slur);
                        break;
                    case "I":
                        btn.setBackgroundResource(R.drawable.tie);
                        break;

                }
            }

        }//********** Musical Notation QUESTION (Location)******************//
    else if(QuestType == 3) {
        String letter = "";
        FButton btn = null;
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {letter = currentQuestion.getOptA(); btn = buttonA;}
            if (i == 2) {letter = currentQuestion.getOptB(); btn = buttonB;}
            if (i == 3) {letter = currentQuestion.getOptC(); btn = buttonC;}
            if (i == 4) {letter = currentQuestion.getOptD(); btn = buttonD;}

            Log.d(TAG, "Letter=" + letter);
            switch (letter) {

                case "A":
                    btn.setBackgroundResource(R.drawable.la_locat);
                    break;
                case "B":
                    btn.setBackgroundResource(R.drawable.si_locat);
                    break;
                case "C":
                    btn.setBackgroundResource(R.drawable.do_locat);
                    break;
                case "D":
                    btn.setBackgroundResource(R.drawable.re_locat);
                    break;
                case "E":
                    btn.setBackgroundResource(R.drawable.mi_locat);
                    break;
                case "F":
                    btn.setBackgroundResource(R.drawable.fa_locat);
                    break;
                case "G":
                    btn.setBackgroundResource(R.drawable.sol_locat);
                    break;
            }
        }

    }
    //********** //musical Notation******************//
    else if(QuestType == 4) {
        String letter = "";
        FButton btn = null;
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {letter = currentQuestion.getOptA(); btn = buttonA;}
            if (i == 2) {letter = currentQuestion.getOptB(); btn = buttonB;}
            if (i == 3) {letter = currentQuestion.getOptC(); btn = buttonC;}
            if (i == 4) {letter = currentQuestion.getOptD(); btn = buttonD;}

            Log.d(TAG, "Letter=" + letter);
            switch (letter) {

                case "S":
                    btn.setBackgroundResource(R.drawable.semibreve);
                    break;
                case "M":
                    btn.setBackgroundResource(R.drawable.minim);
                    break;
                case "C":
                    btn.setBackgroundResource(R.drawable.crotchet);
                    break;
                case "Q":
                    btn.setBackgroundResource(R.drawable.quaver);
                    break;
                case "D":
                    btn.setBackgroundResource(R.drawable.demiquaver);
                    break;
                case "W":
                    btn.setBackgroundResource(R.drawable.whole_r);
                    break;
                case "H":
                    btn.setBackgroundResource(R.drawable.half_r);
                    break;
                case "U":
                    btn.setBackgroundResource(R.drawable.quarter_r);
                    break;
                case "E":
                    btn.setBackgroundResource(R.drawable.eighth_r);
                    break;
                case "I":
                    btn.setBackgroundResource(R.drawable.sixtheenth_r);
                    break;
            }
        }

    }
    else {



        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());

    }
        timeValue = sharedata.getInt("ClockTimer",20);

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countDownTimer.cancel();
        countDownTimer.start();

        //set the value of coin text
       // coinText.setText(String.valueOf(coinValue));
        //Now since user has ans correct increment the coinvalue
        //coinValue++;

    }
////  get   >>  image.getDrawable()
    //Onclick listener for first button
    public void buttonA(View view) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
            //buttonA.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));


            Log.d(TAG, "qid: "+ qid +"   list.size:"+list.size());
            //Check if user has not exceeds the que limit
            if (qid < list.size() - 1) {

                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            if (qid < list.size() - 1) {
                showTheAnswer();
//                          sleep(showAnswerSec);
//                          gameLostPlayAgain();
                incorrectDialog();
            }else
            {
                gameLost();
            }
        }
    }

    //Onclick listener for sec button
    public void buttonB(View view) {
        if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {
           // buttonB.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            Log.d(TAG, "qid: "+ qid +"   list.size:"+list.size());
            if (qid < list.size() - 1) {

                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            if (qid < list.size() - 1) {
                showTheAnswer();
//                          sleep(showAnswerSec);
//                          gameLostPlayAgain();
                incorrectDialog();
            }else
            {
                gameLost();
            }
        }
    }

    //Onclick listener for third button
    public void buttonC(View view) {
        if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
           // buttonC.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            Log.d(TAG, "qid: "+ qid +"   list.size:"+list.size());
            if (qid < list.size() - 1) {

                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            if (qid < list.size() - 1) {
                showTheAnswer();
//                          sleep(showAnswerSec);
//                          gameLostPlayAgain();
                incorrectDialog();
            }else
            {
                gameLost();
            }
        }
    }

    //Onclick listener for fourth button
    public void buttonD(View view) {
        if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {
            //buttonD.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            Log.d(TAG, "qid: "+ qid +"   list.size:"+list.size());
           if (qid < list.size() - 1) {

                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            if (qid < list.size() - 1) {
                showTheAnswer();
//                          sleep(showAnswerSec);
//                          gameLostPlayAgain();
                incorrectDialog();
            }else
            {
                gameLost();
            }

        }
    }


    //This method will navigate from current activity to GameWon
    public void gameWon() {
//        coinValue++;
//        editor.putInt("stars",coinValue);
//        tvStars.setText(String.valueOf(coinValue));
//        editor.putString("date",date.toString());
//
//        editor.commit();

        disableButton();

        //Show the dialog that ans is correct
        //correctDialog();

        coinValue++;
        editor.putInt("stars",coinValue);
        tvStars.setText(String.valueOf(coinValue));
        editor.putString("date",date.toString());

        editor.commit();

        final Dialog dialogCorrect = new Dialog(MainGameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        //ivcorrect.setImageDrawable(getResources().getDrawable(R.drawable.correct));
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);

        //Setting type faces
        correctText.setTypeface(sb);
        buttonNext.setTypeface(sb);

        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will dismiss the dialog
                dialogCorrect.dismiss();
//                //it will increment the question number
//                qid++;
//                //get the que and 4 option and store in the currentQuestion
//                currentQuestion = list.get(qid);
//
//                //reset the color of buttons back to white
//                resetColor();
//
//                //Now this method will set the new que and 4 options
//                updateQueAndOptions();
//
//                //Enable button - remember we had disable them when user ans was correct in there particular button methods
//                enableButton();

                Intent intent = new Intent(MainGameActivity.this, GameWon.class);
                startActivity(intent);
                finish();
            }
        });

//        Intent intent = new Intent(this, GameWon.class);
//        startActivity(intent);
//        finish();
    }

    //This method will navigate from current activity to GameWon
    public void gameLost() {
//        coinValue++;
//        editor.putInt("stars",coinValue);
//        tvStars.setText(String.valueOf(coinValue));
//        editor.putString("date",date.toString());
//
//        editor.commit();

        disableButton();

        //Show the dialog that ans is correct
        //incorrectDialog();
        errCount = sharedata.getInt("errstars",0);
        coinValue = sharedata.getInt("stars",0);
        errCount++;
        if(errCount>=3) {
            coinValue= coinValue-(errCount/3);
            errCount %=3;
            editor.putInt("errstars", errCount);
        }
        tvStars.setText(String.valueOf(coinValue));
        tvErrStars.setTextColor(getResources().getColor(R.color.colorAccent));
        tvErrStars.setText(String.valueOf(errCount)+"/3");
        editor.putString("date",date.toString());
        editor.putInt("errstars",errCount);
        editor.putInt("stars", coinValue);

        editor.commit();
        MainActivity.stopSoundFile();
        Intent intent = new Intent(this, GameWon.class);
        startActivity(intent);
        finish();

       final Dialog dialoginCorrect = new Dialog(MainGameActivity.this);
        dialoginCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialoginCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialoginCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialoginCorrect.setContentView(R.layout.dialog_incorrect);
        // ivcorrect.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
        dialoginCorrect.setCancelable(false);
        dialoginCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialoginCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialoginCorrect.findViewById(R.id.dialogNext);

        //Setting type faces
        correctText.setTypeface(sb);
        buttonNext.setTypeface(sb);

        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will dismiss the dialog
                dialoginCorrect.dismiss();
//                //it will increment the question number
//                qid++;
//                //get the que and 4 option and store in the currentQuestion
//                currentQuestion = list.get(qid);
//
//                //reset the color of buttons back to white
//                resetColor();
//
//                //Now this method will set the new que and 4 options
//                updateQueAndOptions();
//
//                //Enable button - remember we had disable them when user ans was correct in there particular button methods
//                enableButton();

                Intent intent = new Intent(MainGameActivity.this, GameWon.class);
                startActivity(intent);
                finish();
            }
        });

//        Intent intent = new Intent(this, GameWon.class);
//        startActivity(intent);
//        finish();
    }


    //This method is called when user ans is wrong
    //this method will navigate user to the activity PlayAgain
    public void gameLostPlayAgain() {
        errCount = sharedata.getInt("errstars",0);
        coinValue = sharedata.getInt("stars",0);
        errCount++;
        if(errCount>=3) {
            coinValue= coinValue-(errCount/3);
            errCount %=3;
            editor.putInt("errstars", errCount);
        }
        tvStars.setText(String.valueOf(coinValue));
        tvErrStars.setTextColor(getResources().getColor(R.color.colorAccent));
        tvErrStars.setText(String.valueOf(errCount)+"/3");
        editor.putString("date",date.toString());
        editor.putInt("errstars",errCount);
        editor.putInt("stars", coinValue);

        editor.commit();
        MainActivity.stopSoundFile();
        Intent intent = new Intent(this, PlayAgain.class);
        startActivity(intent);
        finish();
    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    public void timeUp() {
        sleep(1200);
        errCount = sharedata.getInt("errstars",0);
        coinValue = sharedata.getInt("stars",0);
        errCount++;
        if(errCount>=3) {
            coinValue= coinValue-(errCount/3);
            errCount %=3;
            editor.putInt("errstars", errCount);
        }
        tvStars.setText(String.valueOf(coinValue));
        tvErrStars.setTextColor(getResources().getColor(R.color.colorAccent));
        tvErrStars.setText(String.valueOf(errCount)+"/3");
        editor.putString("date",date.toString());
        editor.putInt("errstars",errCount);
        editor.putInt("stars", coinValue);

        editor.commit();

      Intent intent = new Intent(this, Time_Up.class);
        startActivity(intent);
        finish();
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
    }

    //When activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        MainActivity.stopSoundFile();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        MainActivity.pauseSoundFile();
    }

    //On BackPressed
    @Override
    public void onBackPressed() {
        MainActivity.stopSoundFile();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check date whether  changed
        mCal = Calendar.getInstance();
        date =  DateFormat.format("yyyyMMdd ", mCal.getTime());
        if(sharedata.getString("date", "0") != date.toString())
        {
            Log.d("Main onResume", "NEW Date !!!  Reset Question List..");
            //Now we gonna shuffle the elements of the list so that we will get questions randomly
            Collections.shuffle(list);
            qid=0;

            //currentQuestion will hold the que, 4 option and ans for particular id
            currentQuestion = list.get(qid);
        }
        sharedata = getSharedPreferences("award", MODE_PRIVATE);
        editor = sharedata.edit();//获取Editor

        Log.d("Main onResume", "stars:"+sharedata.getInt("stars",0));
        Log.d("Main onResume", "date:"+sharedata.getString("date", "0"));
        timeValue = sharedata.getInt("ClockTimer",20);
        timer = timeValue*1000+2000;
        coinValue = sharedata.getInt("stars",0);
        errCount = sharedata.getInt("errstars",0);
        tvStars.setText(String.valueOf(sharedata.getInt("stars",0)));
        tvErrStars.setTextColor(getResources().getColor(R.color.colorAccent));
        tvErrStars.setText(String.valueOf(sharedata.getInt("errstars",0))+"/3" );
    }

    //This dialog is show to the user after he ans correct
    public void correctDialog() {
        coinValue++;
        editor.putInt("stars",coinValue);
        tvStars.setText(String.valueOf(coinValue));
        editor.putString("date",date.toString());

        editor.commit();

        final Dialog dialogCorrect = new Dialog(MainGameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        //ivcorrect.setImageDrawable(getResources().getDrawable(R.drawable.correct));
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);

        //Setting type faces
        correctText.setTypeface(sb);
        buttonNext.setTypeface(sb);
        MainActivity.stopSoundFile();
        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will dismiss the dialog
                dialogCorrect.dismiss();

                //it will increment the question number
                qid++;
                //get the que and 4 option and store in the currentQuestion
                currentQuestion = list.get(qid);

                //reset the color of buttons back to white
                resetColor();

                //Now this method will set the new que and 4 options
                updateQueAndOptions();

                //Enable button - remember we had disable them when user ans was correct in there particular button methods
                enableButton();
            }
        });
    }

    //This dialog is show to the user after he ans correct
    public void incorrectDialog() {
        errCount = sharedata.getInt("errstars",0);
        coinValue = sharedata.getInt("stars",0);
        errCount++;
        if(errCount>=3) {
            coinValue= coinValue-(errCount/3);
            errCount %=3;
            editor.putInt("errstars", errCount);
        }
        tvStars.setText(String.valueOf(coinValue));
        tvErrStars.setTextColor(getResources().getColor(R.color.colorAccent));
        tvErrStars.setText(String.valueOf(errCount)+"/3");
        editor.putString("date",date.toString());
        editor.putInt("errstars",errCount);
        editor.putInt("stars", coinValue);

        editor.commit();

        final Dialog dialogCorrect = new Dialog(MainGameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_incorrect);
       // ivcorrect.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);

        //Setting type faces
        correctText.setTypeface(sb);
        buttonNext.setTypeface(sb);
        MainActivity.stopSoundFile();

        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will dismiss the dialog
                dialogCorrect.dismiss();

                //it will increment the question number
                qid++;
                //get the que and 4 option and store in the currentQuestion
                currentQuestion = list.get(qid);

                //reset the color of buttons back to white
                resetColor();

                //Now this method will set the new que and 4 options
                updateQueAndOptions();

                //Enable button - remember we had disable them when user ans was correct in there particular button methods
                enableButton();
            }
        });
    }


    //This method will make button color white again since our one button color was turned green
    public void resetColor() {
//        buttonA.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
//        buttonB.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
//        buttonC.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
//        buttonD.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        buttonA.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        buttonB.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        buttonC.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        buttonD.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
    }

    //This method will disable all the option button
    public void disableButton() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
    }

    //This method will all enable the option buttons
    public void enableButton() {
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }

    public void showTheAnswer()
    {
        Log.d(TAG, "the answer is " + currentQuestion.getAnswer());

//        if(!currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {buttonA.setBackgroundResource(0); buttonA.refresh();}
//        if(!currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {buttonB.setBackgroundResource(0); buttonB.refresh();}
//        if(!currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {buttonC.setBackgroundResource(0); buttonC.refresh();}
//        if(!currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {buttonD.setBackgroundResource(0); buttonD.refresh();}
        if(!currentQuestion.getOptA().equals(currentQuestion.getAnswer()))
            buttonA.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        if(!currentQuestion.getOptB().equals(currentQuestion.getAnswer()))
            buttonB.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        if(!currentQuestion.getOptC().equals(currentQuestion.getAnswer()))
            buttonC.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));
        if(!currentQuestion.getOptD().equals(currentQuestion.getAnswer()))
            buttonD.setButtonColor(ContextCompat.getColor(getApplicationContext(),R.color.fbutton_default_color));

    }
    public void gotoStore(View view) {
        Intent it = new Intent(this,AwardStoreActivity.class);
        startActivity(it);
    }

    public void clockStop(View view) {
        onPause();

    }

    public void playSound(View view) {
        MainActivity.playSoundFile(currentQuestion.getWavPath());
    }
}
