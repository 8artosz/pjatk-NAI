import java.util.List;

public class Data{
    List<List<String>> trainingData;
    List<List<String>> testData;

    public Data(List<List<String>> trainingData, List<List<String>> testData) {
        this.trainingData = trainingData;
        this.testData = testData;
    }
}