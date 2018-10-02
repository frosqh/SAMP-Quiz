package deptinfo.ubfc.sampquiz;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class QuizPlayAdapter extends RecyclerView.Adapter<QuizPlayHolder>{
    List<QuizCard> list;

    public QuizPlayAdapter(List<QuizCard> liste){
        list = liste;
    }

    @NonNull
    @Override
    public QuizPlayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz,viewGroup    ,false);
            return new QuizPlayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizPlayHolder quizPlayHolder, int i) {
        Log.e("POSITION",String.valueOf(i));
        QuizCard quizCard = list.get(i);
        Log.e("QUIIIIIIZ",quizCard.getText());
        quizPlayHolder.bind(quizCard);

    }

    @Override
    public int getItemCount() {
        Log.e("SIIIIIIIIIIIIIIZe", String.valueOf(list.size()));
        return list.size();
    }
}
