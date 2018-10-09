package deptinfo.ubfc.sampquiz.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import deptinfo.ubfc.sampquiz.recycler.QuizCard;
import deptinfo.ubfc.sampquiz.recycler.QuizPlayAdapter;
import deptinfo.ubfc.sampquiz.R;
import deptinfo.ubfc.sampquiz.database.DataBase;
import deptinfo.ubfc.sampquiz.database.DataBaseHelper;

public class ChooseQuizPlayActivity extends AppCompatActivity {

    ArrayList<QuizCard> quizList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz_play);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(ChooseQuizPlayActivity.this);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        Cursor quiz = db.rawQuery("SELECT * FROM "+ DataBase.Quiz.TABLE_NAME+";",null); //Init quizzs
        quiz.moveToFirst();
        while (!quiz.isAfterLast()){
            quizList.add(new QuizCard(quiz.getString(1),quiz.getString(2),quiz.getString(4),quiz.getString(3)+"%",quiz.getString(0)));
            quiz.moveToNext();
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_quiz_play);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new QuizPlayAdapter(quizList));

    }
}
