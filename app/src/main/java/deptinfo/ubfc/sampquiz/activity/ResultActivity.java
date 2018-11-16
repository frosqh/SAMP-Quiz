package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import deptinfo.ubfc.sampquiz.R;
import deptinfo.ubfc.sampquiz.database.DataBase;
import deptinfo.ubfc.sampquiz.database.DataBaseHelper;

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
        final TextView letter1;
        final TextView letter2;
        final TextView letter3;


        if (score <= third) {
            ((TextView) findViewById(R.id.rank)).setText("Unfortunately, you weren't able to make it to the top 3. For you information, third best's score is " + third);
            findViewById(R.id.choose_score).setVisibility(View.INVISIBLE);
        }
        else{
            findViewById(R.id.choose_score).setVisibility(View.VISIBLE);
            letter1 = findViewById(R.id.letter1);
            letter2 = findViewById(R.id.letter2);
            letter3 = findViewById(R.id.letter3);


            findViewById(R.id.up1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter1,false);
                }
            });
            findViewById(R.id.down1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter1,true);
                }
            });
            findViewById(R.id.up2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter2,false);
                }
            });
            findViewById(R.id.down2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter2,true);
                }
            });
            findViewById(R.id.up3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter3,false);
                }
            });
            findViewById(R.id.down3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewText(letter3,true);
                }
            });
            if (score <= second)
                ((TextView) findViewById(R.id.rank)).setText("Congratulations, you're now in the top 3. Please enter your name using the up/down arrows");
            else
                if (score <= first)
                    ((TextView) findViewById(R.id.rank)).setText("Amazing, you're now in the top 2. Please enter your name using the up/down arrows");
                else
                    ((TextView) findViewById(R.id.rank)).setText("Astonishing, you're the best player for this quiz ! Please enter your name using up/down arrows");
        }

        findViewById(R.id.confirm_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score > third){
                    TextView l1 = findViewById(R.id.letter1);
                    TextView l2 = findViewById(R.id.letter2);
                    TextView l3 = findViewById(R.id.letter3);
                    String name = l1.getText().toString()+l2.getText().toString()+l3.getText().toString();
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(l1.getContext());
                    SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                    if (score > second){
                        
                    }

                }
            }
        });


    }

    private void setNewText(TextView letter, boolean add){
        Log.e("PLOP",letter.getText().toString());
        Log.e("PLOP",String.valueOf(alph.indexOf(letter.getText().charAt(0))));
        int val = alph.indexOf(letter.getText().charAt(0));
        if (add)
            val++;
        else
            val--;
        val=(val+26)%26;
        Log.e("PLOP",String.valueOf(val));
        letter.setText(String.valueOf(alph.charAt(val)));
        }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
