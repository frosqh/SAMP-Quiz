package deptinfo.ubfc.sampquiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

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
            quizList.add(new QuizCard(quiz.getString(1),null));
            quiz.moveToNext();
        }

        for (QuizCard q: quizList){
            Log.e("QUIZ",q.getText());
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_quiz_play);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Log.e("SIIIIIIZE", String.valueOf(quizList.size()));
        recyclerView.setAdapter(new QuizPlayAdapter(quizList));

    }
}
