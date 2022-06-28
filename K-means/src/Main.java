import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj k");
        int k = Integer.parseInt(scan.nextLine());
        System.out.println("Wybrales podzial na: " + k + " grupy.\n");
        String fileToReadFrom = "bankloans.csv";
        List<BankLoan> bankLoans = new ArrayList<>();
        List<Centroid> centroids = new ArrayList<>();
        String[] columnNames;
        try {
            columnNames = readColumnNames(fileToReadFrom);
            readfromCSV(fileToReadFrom, bankLoans,columnNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createCentroids(k, bankLoans, centroids);
        assignCentroidsToBankLoans(bankLoans, centroids);
        int iterations = kMeans(bankLoans, centroids);
        displayResult(bankLoans, iterations);
    }

    public static void assignCentroidsToBankLoans(List<BankLoan> bankLoans, List<Centroid> centroids) {

        for (BankLoan bankLoan : bankLoans) {
            bankLoan.centroid = centroids.get(getRandomNumberInRange(0, centroids.size() - 1));
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void createCentroids(int k, List<BankLoan> bankLoans, List<Centroid> centroids) {
        HashSet <Integer> randoms = new HashSet<>();
        while(randoms.size() < k){
            int random = getRandomNumberInRange(0, bankLoans.size() - 1);
            randoms.add(random);
        }
        Integer[] randomNumbers = randoms.toArray(new Integer[randoms.size()]);
        for (int i = 0; i < randomNumbers.length; i++) {
            Centroid centroid = new Centroid(bankLoans.get(randomNumbers[i]).values);
            centroids.add(centroid);
        }
    }

    public static void readfromCSV(String fileName, List<BankLoan> destinationList,String[] columnNames) throws IOException {

        BufferedReader reader;
        reader = new BufferedReader(new FileReader(fileName));
        String line;
        int columns = columnNames.length;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            destinationList.add(splitter(line,columns));
        }

        reader.close();
    }

    public static String[] readColumnNames(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine();
        String[] splitLine =  line.split(",");
        return Arrays.copyOf(splitLine,splitLine.length-1);
    }

    public static BankLoan splitter(String line, int lineCounter) {

        String[] parts = line.split(",");
        double[] values = new double[lineCounter];

        for (int i = 0; i < lineCounter; i++) {
            double measurement = Double.parseDouble(parts[i]);
            values[i] = measurement;
        }
        return (new BankLoan(values));
    }

    public static void distanceToAllPointsOfCentroidGroup(List<Centroid> centroids, List<BankLoan> bankLoans, int iterations) {
        for (Centroid centroid : centroids) {
            centroid.distanceToAllPointsOfThisGroup = 0;
            for (BankLoan bankLoan : bankLoans) {
                if (bankLoan.centroid.equals(centroid)) {
                    centroid.distanceToAllPointsOfThisGroup += bankLoan.calculateDistanceToOwnCentroid();
                }
            }
        }

        double distance = 0;
        for (Centroid centroid : centroids) {
            distance += centroid.distanceToAllPointsOfThisGroup;
        }
        System.out.println("Po " + iterations + " iteracji dystans rekordow do centroidow wynosi: " + distance);
    }

    public static int kMeans(List<BankLoan> bankLoans, List<Centroid> centroids) {

        int changedGroup;
        int iterations = 0;
        do {
            changedGroup = 0;
            for (BankLoan bankLoan : bankLoans) {
                for (Centroid centroid : centroids) {
                    if (bankLoan.calculateDistanceToOwnCentroid() > bankLoan.calculateDistanceToOtherCentroid(centroid)) {
                        bankLoan.centroid = centroid;
                        changedGroup++;
                    }
                }
            }
            if (changedGroup > 0) {
                for (Centroid centroid : centroids) {
                    centroid.calculateNewCentroid(bankLoans);
                }
            }
            ++iterations;
            distanceToAllPointsOfCentroidGroup(centroids, bankLoans, iterations);

        } while (changedGroup > 0);
        System.out.println();
        return iterations;
    }

    public static String printBankLoanData(BankLoan bankLoan){
        StringBuilder bankLoanData = new StringBuilder();
        for (int i = 0; i < bankLoan.values.length; i++){
            bankLoanData.append(bankLoan.values[i]).append(", ");
        }
        return bankLoanData.toString();
    }

    public static void displayResult(List<BankLoan> bankLoans, int iterations) {
        for (BankLoan bankLoan : bankLoans) {
            System.out.println("BankLoan " + printBankLoanData(bankLoan) + "nalezy do centroidu " + bankLoan.centroid.centroidNumber);
        }
        System.out.println();
        System.out.println("Potrzeba: " + iterations + " iteracji.");
    }
}