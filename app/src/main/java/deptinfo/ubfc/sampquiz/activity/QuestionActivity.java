package deptinfo.ubfc.sampquiz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deptinfo.ubfc.sampquiz.Question;
import deptinfo.ubfc.sampquiz.R;

import static java.lang.Math.max;

public class QuestionActivity extends AppCompatActivity{
    int index;
    int size;
    double score;
    String name;
    List<Question> questionsList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_question);
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("index");
        size = extras.getInt("size");
        score = extras.getDouble("score");
        name = extras.getString("name");
        if (index == size){ //Go to results
            Intent intent = new Intent(this,ResultActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("name",name);
            intent.putExtra("size",size);
            startActivity(intent);
            System.exit(5);
        }
        questionsList = new ArrayList<>();
        for (int i = 0; i<size;i++)
            questionsList.add((Question) extras.getSerializable("question"+i));
        Log.e("INDEX", String.valueOf(index));
        Log.e("INDEX","list length : "+questionsList.size());
        Question currentQuestion = questionsList.get(index);

        ((TextView) findViewById(R.id.question_text)).setText(currentQuestion.getQuestion());
        ((TextView) findViewById(R.id.question_score_text)).setText("Question : "+(index+1)+"/"+size+" - Score : "+score);

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
                        nextQuestion(index+1,score+1);
                    }
                });
            } else {
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextQuestion(index+1, max(0,(score-0.5)));
                    }
                });
            }
        }
    }

    public void goToNext(View v){
        nextQuestion(index+1,score);
    }

    private void nextQuestion(int ind, double score){
        Intent intent = new Intent(this,QuestionActivity.class);
        intent.putExtra("index",ind);
        intent.putExtra("size",size);
        intent.putExtra("score",score);
        intent.putExtra("name",name);
        for (int i = 0;i<questionsList.size();i++){
            intent.putExtra("question"+i,questionsList.get(i));
        }
        startActivity(intent);
    }

    public void showResponse(View v){
        new AlertDialog.Builder(this)
                .setTitle("Show Response")
                .setMessage("Do you really want to cheat ? Your score will suffer a -0.25.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        score = max(0, score-0.25);
                        ((TextView) findViewById(R.id.question_score_text)).setText("Question : "+(index+1)+"/"+size+" - Score : "+score);
                        Toast.makeText(QuestionActivity.this, questionsList.get(index).getAnswers().get(questionsList.get(index).getAnswer()), Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }


}
