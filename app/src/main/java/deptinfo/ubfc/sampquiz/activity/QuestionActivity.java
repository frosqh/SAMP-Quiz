package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import deptinfo.ubfc.sampquiz.Question;
import deptinfo.ubfc.sampquiz.R;

import static java.lang.Math.max;

public class QuestionActivity extends AppCompatActivity{
    int index;
    int size;
    float score;
    List<Question> questionsList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_question);
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("index");
        size = extras.getInt("size");
        score = extras.getFloat("score");
        questionsList = new ArrayList<>();
        for (int i = index+1; i<size;i++){
            questionsList.add((Question) extras.getSerializable("question"+i));
        }
        Log.e("INDEX", String.valueOf(index));
        Question currentQuestion = (Question) extras.getSerializable("question"+index);

        ((TextView) findViewById(R.id.question_text)).setText(currentQuestion.getQuestion());
        ((TextView) findViewById(R.id.question_score_text)).setText("Question : "+index+"/"+size+" - Score : "+score);

        final List<Button> buttons = new ArrayList<>();

        buttons.add((Button) findViewById(R.id.answer_1));
        buttons.add((Button) findViewById(R.id.answer_2));
        buttons.add((Button) findViewById(R.id.answer_3));
        buttons.add((Button) findViewById(R.id.answer_4));
        buttons.add((Button) findViewById(R.id.answer_5));
        buttons.add((Button) findViewById(R.id.answer_6));
        List<String> answers = currentQuestion.getAnswers();
        for (int i = 0;i<answers.size();i++){
            buttons.get(i).setText(answers.get(i));
            buttons.get(i).setVisibility(View.VISIBLE);
            if (i == currentQuestion.getAnswer()){
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),QuestionActivity.class);
                        intent.putExtra("index",index+1);
                        intent.putExtra("size",size);
                        intent.putExtra("score",score+1);
                        for (int i = 0;i<questionsList.size();i++){
                            intent.putExtra("question"+i,questionsList.get(i));
                        }
                        v.getContext().startActivity(intent);
                    }
                });
            } else {
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),QuestionActivity.class);
                        intent.putExtra("index",index+1);
                        intent.putExtra("size",size);
                        intent.putExtra("score",max(0,(score-0.5)));
                        for (int i = 0;i<questionsList.size();i++){
                            intent.putExtra("question"+index+i,questionsList.get(i));
                        }
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }
}
