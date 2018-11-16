package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import deptinfo.ubfc.sampquiz.R;

public class ResultActivity extends AppCompatActivity {
    private String name;
    private double score;
    private int size;
    private double first, second, third;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        score = extras.getDouble("score");
        size = extras.getInt("size");
        first = extras.getDouble("first");
        second = extras.getDouble("second");
        third = extras.getDouble("third");
        ((TextView) findViewById(R.id.quizz_name)).setText(name);
        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(((String) scoreView.getText()).replace("%s",String.valueOf(score)).replace("%t",String.valueOf(Double.valueOf(size))));
        if (score < third)
            ((TextView) findViewById(R.id.rank)).setText("Unfortunately, you weren't able to make it to the top 3. For you information, third best's score is "+third);
        else{

        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
