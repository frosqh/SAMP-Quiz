package deptinfo.ubfc.sampquiz;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizPlayHolder extends RecyclerView.ViewHolder{
    private TextView textView;
    private ImageView imageView;

    public QuizPlayHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_recycler_quiz_play);
        imageView = itemView.findViewById(R.id.image_recycler_quiz_play);
    }

    public void bind(QuizCard quizCard){
        textView.setText(quizCard.getText());
        //TODO Foutre l'image dans ImageView (en utilisant la DB par exemple ici)
    }
}
