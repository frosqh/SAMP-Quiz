package deptinfo.ubfc.sampquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBase{
    private DataBase() {}

    public static class Quiz implements BaseColumns {
        public static final String TABLE_NAME = "quiz";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_FIRST_SCORE = "first_score";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_SECOND_SCORE = "second_score";
        public static final String COLUMN_NAME_SECOND_NAME = "second_name";
        public static final String COLUMN_NAME_THIRD_SCORE = "third_score";
        public static final String COLUMN_NAME_THIRD_NAME = "third_name";
    }

    public static class Question implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_QUIZ = "quiz_id";
    }

    public static class Answer implements BaseColumns {
        public static final String TABLE_NAME = "answer";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_QUESTION = "question_id";
    }
}
