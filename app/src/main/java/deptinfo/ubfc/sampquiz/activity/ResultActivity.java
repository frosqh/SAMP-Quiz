package deptinfo.ubfc.sampquiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import deptinfo.ubfc.sampquiz.R;

public class ResultActivity extends AppCompatActivity {
    String name;
    double score;
    int size;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        score = extras.getDouble("score");
        size = extras.getInt("size");
        ((TextView) findViewById(R.id.quizz_name)).setText(name);
        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(((String) scoreView.getText()).replace("%s",String.valueOf(score)).replace("%t",String.valueOf(Double.valueOf(size))));
        
    }
}
