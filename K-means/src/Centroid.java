import java.util.List;

public class Centroid {

    double[] dimensions;
    double distanceToAllPointsOfThisGroup;
    static int centroidsCreated;
    int centroidNumber;

    public Centroid(double[] dimensions) {
        this.dimensions = dimensions;
        this.centroidNumber = centroidsCreated;
        ++centroidsCreated;
    }

    public void calculateNewCentroid(List<BankLoan> bankLoans) {

        int[] liczniki = new int[bankLoans.get(0).values.length];
        double mianownik = 0;

        for (BankLoan bankLoan : bankLoans) {
            if (bankLoan.centroid.equals(this)) {
                for (int i = 0; i < bankLoan.values.length; i++) {
                    liczniki[i] += bankLoan.values[i];
                }
                mianownik++;
            }
        }
        for (int i = 0; i < this.dimensions.length; i++) {
            this.dimensions[i] = (liczniki[i] / mianownik);
        }
    }
}