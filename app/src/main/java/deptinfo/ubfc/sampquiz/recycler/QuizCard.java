package deptinfo.ubfc.sampquiz.recycler;

public class QuizCard {
    private String text;
    private String imageUrl;
    private String topGuy;
    private String topScore;
    private String id;

    public QuizCard(String text, String imageUrl, String topGuy, String topScore, String id) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.topGuy = topGuy;
        this.topScore = topScore;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTopGuy(){
        return topGuy;
    }

    public String getTopScore(){
        return topScore;
    }

    public String getId(){
        return id;
    }
}
