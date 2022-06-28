import java.io.*;
import java.util.*;

public class Main {


    public static Data prepareDataFromOneFile(List<List<String>> recordsFromFile){
        Map<String, List<List<String>>> trainingData = new HashMap<>();
        List<List<String>> testData = new LinkedList<>();
        for(List<String> recordFromFile : recordsFromFile){
            String uniqueClass = recordFromFile.get(recordFromFile.size() - 1);

            if(!trainingData.containsKey(uniqueClass)){
                List<List<String>> listToAdd = new LinkedList<>();
                listToAdd.add(recordFromFile);
                trainingData.put(uniqueClass, listToAdd);
            }else{
                List<List<String>> list = trainingData.get(uniqueClass);
                if(list.size() < 35){
                    list.add(recordFromFile);
                }else{
                    testData.add(recordFromFile);
                }
            }
        }
        List<List<String>> traningDataAsList = new LinkedList<>();

        for(Map.Entry<String, List<List<String>>> mapEntry : trainingData.entrySet()){
            traningDataAsList.addAll(mapEntry.getValue());
        }

        return new Data(traningDataAsList, testData);
    }


    public static List<List<String>> readFile(String path){
        List<List<String>> records = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static Data readDataFromPaths(String pathToData, String pathToTestData){
        return new Data(readFile(pathToData), readFile(pathToTestData));
    }


    public static void main(String[] args) {

        InputResult inputResult = InputGetter.getInput();

        List<List<String>> records;
        Data dataFromFile;


        if(inputResult.isInOneFile){
            records = readFile(inputResult.filePath1);

            records.remove(0);

            dataFromFile = prepareDataFromOneFile(records);

        }else{
            dataFromFile = readDataFromPaths(inputResult.filePath1, inputResult.filePath2);
        }


        Classifier classifier = new Classifier(dataFromFile.trainingData, inputResult.k);

        ModelResults modelResults = new ModelResults();

        for(List<String> lineOfData : dataFromFile.testData){
            List<String> dataToClassify = new LinkedList<>();
            for(int i = 0; i < lineOfData.size()-1; i++) {
                dataToClassify.add(lineOfData.get(i));
            }
            String correctAnswer = lineOfData.get(lineOfData.size() - 1);
            String givenAnswer = classifier.classify(dataToClassify);
            if(correctAnswer.equals(givenAnswer))
                modelResults.modelAnsweredCorrectly();
            else
            {
                modelResults.modelAnsweredWrongly();
            }
        }
        System.out.println("Model accuracy with k="+ inputResult.k + " is " + modelResults.getAccuracy());

        while (true){
            System.out.println("Give input to classify");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            String dataFromConsole = "";

            try {
                dataFromConsole = reader.readLine();
                String[] values = dataFromConsole.split(",");
                List<String> listOfValues = new LinkedList<>();
                for(String v : values){
                    listOfValues.add(v);
                }
                System.out.println("This object belongs to class " + classifier.classify(listOfValues));

            } catch (IOException e) {
                System.out.println("Bad input");
            }
        }

    }
}
