package deptinfo.ubfc.sampquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "DataBaseSAMP.db";

    private static final String SQL_CREATE_ENTRY_QUIZ =
            "CREATE TABLE " + DataBase.Quiz.TABLE_NAME + " ( " +
                    DataBase.Quiz._ID + " INTEGER PRIMARY KEY, " +
                    DataBase.Quiz.COLUMN_NAME_TITLE + " TEXT , " +
                    DataBase.Quiz.COLUMN_NAME_IMAGE + " TEXT, "+
                    DataBase.Quiz.COLUMN_NAME_FIRST_SCORE + " REAL , " +
                    DataBase.Quiz.COLUMN_NAME_FIRST_NAME + " TEXT , " +
                    DataBase.Quiz.COLUMN_NAME_SECOND_SCORE + " REAL , " +
                    DataBase.Quiz.COLUMN_NAME_SECOND_NAME + " TEXT , " +
                    DataBase.Quiz.COLUMN_NAME_THIRD_SCORE + " REAL , " +
                    DataBase.Quiz.COLUMN_NAME_THIRD_NAME + " TEXT);";

    private static final String SQL_CREATE_ENTRY_QUESTION =
            "CREATE TABLE " + DataBase.Question.TABLE_NAME + " ( " +
                    DataBase.Question._ID + " INTEGER PRIMARY KEY, " +
                    DataBase.Question.COLUMN_NAME_TEXT + " TEXT , " +
                    DataBase.Question.COLUMN_NAME_ANSWER + " INTEGER, " +
                    DataBase.Question.COLUMN_NAME_QUIZ + " INTEGER , " +
                    "FOREIGN KEY (" + DataBase.Question.COLUMN_NAME_QUIZ + ") REFERENCES "+ DataBase.Quiz.TABLE_NAME+" ( "+ DataBase.Quiz._ID+" ));";

    private static final String SQL_CREATE_ENTRY_ANSWER =
            "CREATE TABLE " + DataBase.Answer.TABLE_NAME + " ( " +
                    DataBase.Answer._ID + " INTEGER PRIMARY KEY, " +
                    DataBase.Answer.COLUMN_NAME_TEXT + " TEXT , " +
                    DataBase.Answer.COLUMN_NAME_QUESTION + " INTEGER, " +
                    "FOREIGN KEY (" + DataBase.Answer.COLUMN_NAME_QUESTION + ") REFERENCES "+ DataBase.Question.TABLE_NAME+" ( "+ DataBase.Question._ID+" ));";

    public static final String SQL_DELETE_ENTRY_ANSWER =
            "DROP TABLE IF EXISTS "+ DataBase.Answer.TABLE_NAME;

    public static final String SQL_DELETE_ENTRY_QUESTION =
            "DROP TABLE IF EXISTS "+ DataBase.Question.TABLE_NAME;

    public static final String SQL_DELETE_ENTRY_QUIZ =
            "DROP TABLE IF EXISTS "+ DataBase.Quiz.TABLE_NAME;


    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRY_QUIZ);
        db.execSQL(SQL_CREATE_ENTRY_QUESTION);
        db.execSQL(SQL_CREATE_ENTRY_ANSWER);

        ContentValues values = new ContentValues();
        values.put(DataBase.Quiz.COLUMN_NAME_TITLE,"Vie Animale");
        values.put(DataBase.Quiz.COLUMN_NAME_FIRST_NAME,"WIN");
        values.put(DataBase.Quiz.COLUMN_NAME_FIRST_SCORE,5);
        values.put(DataBase.Quiz.COLUMN_NAME_SECOND_NAME,"MED");
        values.put(DataBase.Quiz.COLUMN_NAME_SECOND_SCORE,4);
        values.put(DataBase.Quiz.COLUMN_NAME_THIRD_NAME,"LOS");
        values.put(DataBase.Quiz.COLUMN_NAME_THIRD_SCORE,3);
        values.put(DataBase.Quiz.COLUMN_NAME_IMAGE,"res::panda");
        long quizId = db.insert(DataBase.Quiz.TABLE_NAME, null, values);

        addTrueFalseAnswers(db, addQuestion(db, "Le diable de Tasmanie vit dans la jungle du Brésil",1,quizId));
        addTrueFalseAnswers(db, addQuestion(db, "La sauterelle saute l'équivalent de 200 fois sa taille",0, quizId));
        addTrueFalseAnswers(db, addQuestion(db, "Les pandas hibernent",0,quizId));
        addTrueFalseAnswers(db, addQuestion(db, "On trouve des dromadaires en liberté en Australie",1,quizId));
        addTrueFalseAnswers(db, addQuestion(db, "Le papillon monarque vole plus de 4000km",1,quizId));
        addTrueFalseAnswers(db, addQuestion(db, "Les gorilles mâles dorment dans les arbres",0,quizId));

    }

    public long addQuestion(SQLiteDatabase db, String text, int answer, long quizId){
        ContentValues values = new ContentValues();
        values.put(DataBase.Question.COLUMN_NAME_TEXT,text);
        values.put(DataBase.Question.COLUMN_NAME_ANSWER,answer);
        values.put(DataBase.Question.COLUMN_NAME_QUIZ,(int) quizId);
        return db.insert(DataBase.Question.TABLE_NAME, null, values);
    }

    private void addTrueFalseAnswers(SQLiteDatabase db, long questionId){
        ContentValues values = new ContentValues();
        values.put(DataBase.Answer.COLUMN_NAME_TEXT,"Vrai");
        values.put(DataBase.Answer.COLUMN_NAME_QUESTION,(int) questionId);
        db.insert(DataBase.Answer.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(DataBase.Answer.COLUMN_NAME_TEXT,"Faux");
        values.put(DataBase.Answer.COLUMN_NAME_QUESTION, (int) questionId);
        db.insert(DataBase.Answer.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Save done edits ...
        db.execSQL(SQL_DELETE_ENTRY_ANSWER);
        db.execSQL(SQL_DELETE_ENTRY_QUESTION);
        db.execSQL(SQL_DELETE_ENTRY_QUIZ);
        onCreate(db);

    }
}
