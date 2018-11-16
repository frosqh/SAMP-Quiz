package deptinfo.ubfc.sampquiz.recycler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import deptinfo.ubfc.sampquiz.R;
import deptinfo.ubfc.sampquiz.activity.QuizActivity;
import deptinfo.ubfc.sampquiz.database.*;

public class QuizPlayHolder extends RecyclerView.ViewHolder{
    private TextView textView;
    private ImageView imageView;
    private TextView subTextView;
    private TextView idTextView;

    public QuizPlayHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_recycler_quiz_play);
        subTextView = itemView.findViewById(R.id.text_recycler_quiz_play_sub);
        idTextView = itemView.findViewById(R.id.text_recycler_quiz_play_id);
        imageView = itemView.findViewById(R.id.image_recycler_quiz_play);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                intent.putExtra("quizId",idTextView.getText());
                v.getContext().startActivity(intent);
            }

        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int quizId = Integer.parseInt(idTextView.getText().toString());
                DataBaseHelper dataBaseHelper = new DataBaseHelper(idTextView.getContext());
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                String query = "SELECT "
                        + DataBase.Quiz.COLUMN_NAME_FIRST_NAME+", "
                        + DataBase.Quiz.COLUMN_NAME_FIRST_SCORE+", "
                        + DataBase.Quiz.COLUMN_NAME_SECOND_NAME+", "
                        + DataBase.Quiz.COLUMN_NAME_SECOND_SCORE+", "
                        + DataBase.Quiz.COLUMN_NAME_THIRD_NAME+", "
                        + DataBase.Quiz.COLUMN_NAME_THIRD_SCORE
                        + " FROM "+DataBase.Quiz.TABLE_NAME
                        + " WHERE "+ DataBase.Quiz._ID+" = "+quizId;
                Cursor quiz = db.rawQuery(query,null,null);
                quiz.moveToFirst();
                String firstName = quiz.getString(0);
                Double firstScore = quiz.getDouble(1);
                String secondName = quiz.getString(2);
                Double secondScore = quiz.getDouble(3);
                String thirdName = quiz.getString(4);
                Double thirdScore = quiz.getDouble(5);

                Toast.makeText(idTextView.getContext(), "1st : "+firstName+" - "+firstScore+"\n"+
                                                                "2nd : "+secondName+" - "+secondScore+"\n"+
                                                                "3rd : "+thirdName+" - "+thirdScore,Toast.LENGTH_LONG).show();


                return true;
            }
        });
    }

    public void bind(QuizCard quizCard){
        textView.setText(quizCard.getText());
        subTextView.setText("Best Score : "+quizCard.getTopScore()+" by "+quizCard.getTopGuy());
        idTextView.setText(quizCard.getId());
        if (quizCard.getImageUrl().startsWith("url::")){
            String image_url = quizCard.getImageUrl().substring("url::".length());

        } else if (quizCard.getImageUrl().startsWith("file::")){
            String image_url = quizCard.getImageUrl().substring("file::".length());
            File imgFile = new  File("image_url");
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        } else if (quizCard.getImageUrl().startsWith("res::")){
            String image_url = quizCard.getImageUrl().substring("res::".length());
            Context ctx = imageView.getContext();
            imageView.setBackgroundResource(getResourceID(image_url,"drawable",ctx));
        } else imageView.setBackgroundResource(R.color.colorPrimary);
    }

    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }
}
