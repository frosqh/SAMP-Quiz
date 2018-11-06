package deptinfo.ubfc.sampquiz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import deptinfo.ubfc.sampquiz.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.quitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        findViewById(R.id.dlButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Download from specified server
            }
        });

        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Edit quiz from database
            }
        });

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ChooseQuizPlayActivity.class);
                startActivity(intent);
                //TODO Play choosen quiz form database
            }
        });
    }
}
