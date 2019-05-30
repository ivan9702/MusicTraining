package com.example.musictraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TriviaQuizHelper extends SQLiteOpenHelper {
    String TAG = "TriviaQuizHelper";
    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //wavFile
    private static final String WAVFILE = "WAVFILE";
    //QuestTpye
    private static final String QTYPE = "QTYPE";

    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    //private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255), " + WAVFILE + " VARCHAR(255));";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255), " + WAVFILE + " VARCHAR(255)," + QTYPE + " VARCHAR(255));";


    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();
        // Note Name
        arraylist.add(new TriviaQuestion("What's the Do's Note Name ?", "D", "C", "E", "B", "C", "name1.wav",1));
        arraylist.add(new TriviaQuestion("What's the Re's Note Name ?", "A", "B", "D", "G", "D", "name2.wav",1));
        arraylist.add(new TriviaQuestion("What's the Mi's Note Name ?", "G", "E", "F", "A", "E", "name3.wav",1));
        arraylist.add(new TriviaQuestion("What's the Fa's Note Name ?", "F", "D", "A", "B", "F", "name4.wav",1));
        arraylist.add(new TriviaQuestion("What's the Sol's Note Name ?", "B", "C", "D", "G", "G", "name5.wav",1));
        arraylist.add(new TriviaQuestion("What's the La's Note Name ?", "A", "E", "F", "C", "A", "name6.wav",1));
        arraylist.add(new TriviaQuestion("What's the Si's Note Name ?", "F", "D", "B", "G", "B", "name7.wav",1));

        //Clef
        arraylist.add(new TriviaQuestion("What's the treble clef ?", "B", "C", "D", "T", "T", "clef1.wav",2));
        arraylist.add(new TriviaQuestion("What's the Bass clef ?", "T", "I", "R", "B", "B", "clef2.wav",2));
        arraylist.add(new TriviaQuestion("What's the Sharp signature ?", "S", "T", "I", "F", "S", "clef3.wav",2));
        arraylist.add(new TriviaQuestion("What's the Flat signature ?", "F", "L", "S", "T", "F", "clef4.wav",2));
        arraylist.add(new TriviaQuestion("What's the crescendo signature ?", "C", "T", "L", "F", "C", "clef5.wav",2));
        arraylist.add(new TriviaQuestion("What's the decrescendo signature ?", "F", "B", "S", "D", "D", "clef6.wav",2));
        arraylist.add(new TriviaQuestion("What's the slur signature ?", "C", "T", "L", "F", "L", "clef7.wav",2));
        arraylist.add(new TriviaQuestion("What's the tie signature ?", "L", "B", "S", "I", "I", "clef8.wav",2));
        arraylist.add(new TriviaQuestion("What's the repeat signature ?", "F", "R", "S", "I", "R", "clef8.wav",2));

        //musical Notation
        arraylist.add(new TriviaQuestion("What's the Do's location ?","F", "D","B", "C", "C",  "staff1.wav",3));
        arraylist.add(new TriviaQuestion("What's the Re's location ?","G","E","D", "A", "D", "staff2.wav",3));
        arraylist.add(new TriviaQuestion("What's the Mi's location ?","A","B","D", "E", "E", "staff3.wav",3));
        arraylist.add(new TriviaQuestion("What's the Fa's location ?","B","F","D", "G", "F","staff4.wav",3));
        arraylist.add(new TriviaQuestion("What's the Sol's location ?","F","G","A", "B", "G",  "staff5.wav",3));
        arraylist.add(new TriviaQuestion("What's the La's location ?","D","A","E", "B", "A",   "staff6.wav",3));
        arraylist.add(new TriviaQuestion("What's the Si's location ?","A","E","F", "B", "B","staff7.wav",3));

        //musical Notation
        arraylist.add(new TriviaQuestion("What's the whole note ?", "S", "M", "D", "Q", "S", "note1.wav",4));
        arraylist.add(new TriviaQuestion("What's the half note ?", "D", "M", "Q", "C", "M", "note2.wav",4));
        arraylist.add(new TriviaQuestion("What's the quarter note ?", "M", "S", "C", "D", "C", "note3.wav",4));
        arraylist.add(new TriviaQuestion("What's the eighth note ?", "S", "M", "D", "Q", "Q", "note4.wav",4));
        arraylist.add(new TriviaQuestion("What's the sixteenth note ?", "C", "Q", "D", "S", "D", "note5.wav",4));
        arraylist.add(new TriviaQuestion("What's the whole REST note ?", "H", "W", "I", "E", "W", "note6.wav",4));
        arraylist.add(new TriviaQuestion("What's the half REST note ?", "H", "E", "U", "W", "H", "note7.wav",4));
        arraylist.add(new TriviaQuestion("What's the quarter REST note ?", "I", "U", "E", "H", "U", "note8.wav",4));
        arraylist.add(new TriviaQuestion("What's the eighth REST note ?", "U", "W", "E", "H", "E", "note9.wav",4));
        arraylist.add(new TriviaQuestion("What's the sixteenth REST note ?", "W", "H", "U", "I", "I", "note10.wav",4));


        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                values.put(WAVFILE,question.getWavPath());
                values.put(QTYPE, question.getQuestType());

                db.insert(TABLE_NAME, null, values);

                Log.d(TAG, "Qtype: "+ values.get(QTYPE));
                Log.d(TAG, "wav: "+ values.get(WAVFILE));

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER,WAVFILE,QTYPE};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);

        int i=0;
        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            question.setWavPath(cursor.getString(7));
            question.setQuestType(cursor.getInt(8));

            questionsList.add(question);

            i++;
            Log.d(TAG, "Question Number is:"+ i+ "  id: "+cursor.getInt(0));
            Log.d(TAG, "Question Number is:"+ i+ "  id: "+cursor.getInt(8));
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}
