package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deptinfo.ubfc.sampquiz.Question;
import deptinfo.ubfc.sampquiz.R;
import deptinfo.ubfc.sampquiz.database.DataBase;
import deptinfo.ubfc.sampquiz.database.DataBaseHelper;

public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Bundle bundle = getIntent().getExtras();
        String quizId = bundle.getString("quizId");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(QuizActivity.this);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        List<Question> questionsList = new ArrayList<>();
        String query = "SELECT * FROM "+ DataBase.Question.TABLE_NAME+" WHERE "+DataBase.Question.COLUMN_NAME_QUIZ+" = "+quizId+";";
        Cursor questions = db.rawQuery(query,null);

        questions.moveToFirst();
        while (!questions.isAfterLast()){
            Question tempQ = new Question(questions.getString(1));
            tempQ.setAnswer(questions.getInt(2));
            String queryA = "SELECT * FROM "+DataBase.Answer.TABLE_NAME+" WHERE "+DataBase.Answer.COLUMN_NAME_QUESTION+" = "+questions.getString(0)+";";
            Cursor answers = db.rawQuery(queryA ,null);
            answers.moveToFirst();
            while (!answers.isAfterLast()){
                tempQ.add(answers.getString(1));
                answers.moveToNext();
            }
            questionsList.add(tempQ);
            answers.close();
            questions.moveToNext();
        }
        questions.close();

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("index",0);
        intent.putExtra("size",questionsList.size());
        intent.putExtra("score",0);
        for (int i = 0;i<questionsList.size();i++){
            Log.e("QUE",questionsList.get(i).toString());
            intent.putExtra("question"+i,questionsList.get(i));
        }
        startActivity(intent);
    }
}
