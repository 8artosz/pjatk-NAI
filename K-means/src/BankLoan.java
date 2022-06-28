public class BankLoan {
    double values[];
    Centroid centroid;

    BankLoan(double values[]) {
        this.values = values;
    }

    public double calculateDistanceToOtherCentroid(Centroid otherCentroid) {

        double distance = 0;
        for (int i = 0; i < otherCentroid.dimensions.length; i++) {
            distance += Math.pow(this.values[i] - otherCentroid.dimensions[i], 2);
        }

        return distance;
    }

    public double calculateDistanceToOwnCentroid() {

        double distance = 0;
        for (int i = 0; i < this.values.length; i++) {
            distance += Math.pow(this.values[i] - this.centroid.dimensions[i], 2);
        }
        return distance;
    }
}
