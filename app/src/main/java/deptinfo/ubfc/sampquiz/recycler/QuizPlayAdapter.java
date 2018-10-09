package deptinfo.ubfc.sampquiz.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import deptinfo.ubfc.sampquiz.R;

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
        QuizCard quizCard = list.get(i);
        quizPlayHolder.bind(quizCard);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
