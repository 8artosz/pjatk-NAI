import java.util.*;

public class Classifier {
    private List<List<String>> trainData;
    private int k;

    public Classifier(List<List<String>> trainData, int k) {
        this.trainData = trainData;
        this.k = k;
    }

    public String classify(List<String> record){
        List<DistanceWithClassName> allDistances = new ArrayList<>();

        for(List<String> lineInTrainData: trainData){
            DistanceWithClassName calculatedDWCN = new DistanceWithClassName(
                    calculateDistance(record, lineInTrainData),
                    lineInTrainData.get(lineInTrainData.size() - 1)
            );
            allDistances.add(calculatedDWCN);
        }

        allDistances.sort((o1, o2) -> {
            if(o1.distance  == o2.distance )return 0;
            return o1.distance > o2.distance ? 1: -1;
        });

        List<String> mostCommonKOccurances = new LinkedList<>();


        for(int i = 0; i < k; i++){
            mostCommonKOccurances.add(allDistances.get(i).className);
        }
        return getMostCommonElemnt(mostCommonKOccurances);
    }


    private double calculateDistance(List<String> record, List<String> lineInTrainData){
        double sum = 0;
        for(int i = 0; i < record.size(); i++){
            double coordinate1 = Double.parseDouble(record.get(i));
            double coordinate2 = Double.parseDouble(lineInTrainData.get(i));

            sum += (coordinate1 - coordinate2) * (coordinate1 - coordinate2);
        }
        return Math.sqrt(sum);
    }



    public static String getMostCommonElemnt(List<String> list) {
        Map<String, Integer> occurences = new HashMap<>();
        for(String key : list){
            if(!occurences.containsKey(key))
                occurences.put(key, 0);
            occurences.put(key, occurences.get(key) + 1);
        }
        String toReturn = "";
        int numberOfOccurences = -1;
        for(Map.Entry<String, Integer> entry : occurences.entrySet()){
            if(entry.getValue() > numberOfOccurences){
                toReturn = entry.getKey();
                numberOfOccurences = entry.getValue();
            }
        }
        return toReturn;
    }
}
