import java.text.DecimalFormat;

public class ModelResults {
    private int allAnswers = 0;
    private int correctAnswers = 0;

    private static DecimalFormat twoDifitsAfterComa = new DecimalFormat("#.##");

    public void reset(){
        allAnswers = 0;
        correctAnswers = 0;
    }

    public void modelAnsweredWrongly(){
        allAnswers++;
    }
    public void modelAnsweredCorrectly(){
        allAnswers++;
        correctAnswers++;
    }

    public String getAccuracy(){
        double accuracy = (double) correctAnswers/ (double)allAnswers;
        return twoDifitsAfterComa.format(accuracy * 100) + "%";
    }
}
