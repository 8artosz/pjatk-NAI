import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputGetter {

    public static InputResult getInput(){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String isDataInOneFileS = "";
        boolean isDataInOneFile = true;

        System.out.println("Is data in one file (Y/N) Yes/No");
        try {
            isDataInOneFileS = reader.readLine();

            isDataInOneFileS = isDataInOneFileS.toLowerCase();
            if(isDataInOneFileS.equals("y") || isDataInOneFileS.equals("yes")){
                isDataInOneFile = true;
            }else if(isDataInOneFileS.equals("n") || isDataInOneFileS.equals("no")){
                isDataInOneFile = false;
            }
        } catch (IOException e) {
        }

        System.out.println("What K to use");
        int k = 3;

        try {
            k = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
        }

        System.out.println();

        String file1 = "iris.csv";
        String file2 = "";
        if(isDataInOneFile){
            System.out.println("Type file path");
            try {
                file1 = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Type file path with training data");
            try {
                file1 = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("Type file path with test data");
            try {
                file2 = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new InputResult(k, isDataInOneFile, file1, file2);
    }
}
