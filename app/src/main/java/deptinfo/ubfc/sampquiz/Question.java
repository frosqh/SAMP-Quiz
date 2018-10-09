package deptinfo.ubfc.sampquiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable{
    private String question;
    private List<String> answers;
    private int answer;

    public Question(String question){
        this.question = question;
        answers = new ArrayList<>();
    }

    public void add(String ans){
        answers.add(ans);
    }

    public void setAnswer(int id){
        answer = id;
    }

    public String getQuestion(){
        return question;
    }

    public List<String> getAnswers(){
        return answers;
    }

    public int getAnswer(){
        return answer;
    }

    @Override
    public String toString(){
        return question;
    }
}
