package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import deptinfo.ubfc.sampquiz.R;

public class ResultActivity extends AppCompatActivity {
    private String name;
    private double score;
    private int size;
    private double first, second, third;

    private static final String alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


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
        if (score <= third) {
            ((TextView) findViewById(R.id.rank)).setText("Unfortunately, you weren't able to make it to the top 3. For you information, third best's score is " + third);
            findViewById(R.id.choose_score).setVisibility(View.INVISIBLE);
        }
        else{
            findViewById(R.id.choose_score).setVisibility(View.VISIBLE);
            if (score <= second)
                ((TextView) findViewById(R.id.rank)).setText("Congratulations, you're now in the top 3. Please enter your name using the up/down arrows");
            else
                if (score <= first)
                    ((TextView) findViewById(R.id.rank)).setText("Amazing, you're now in the top 2. Please enter your name using the up/down arrows");
                else
                    ((TextView) findViewById(R.id.rank)).setText("Astonishing, you're the best player for this quiz ! Please enter your name using up/down arrows");
        }
        final TextView letter1 = findViewById(R.id.letter1);
        final TextView letter2 = findViewById(R.id.letter2);
        final TextView letter3 = findViewById(R.id.letter3);


        findViewById(R.id.up1).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          setNewText(letter1);
                                                      }
                                                  });
        findViewById(R.id.down1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewText(letter1);
            }
        });
        findViewById(R.id.up2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewText(letter2);
            }
        });
        findViewById(R.id.down2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewText(letter2);
            }
        });
        findViewById(R.id.up3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewText(letter3);
            }
        });
        findViewById(R.id.down3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewText(letter3);
            }
        });

    }

    private void setNewText(TextView letter){
        letter.setText(alph.charAt((alph.indexOf(letter.getText().charAt(0))+1)%26));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
