package deptinfo.ubfc.sampquiz;

class QuizCard {
    private String text;
    private String imageUrl;

    public QuizCard(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
