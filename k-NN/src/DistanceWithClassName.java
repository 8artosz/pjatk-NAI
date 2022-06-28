public class DistanceWithClassName {
    public double distance;
    public String className;

    public DistanceWithClassName(double distance, String className) {
        this.distance = distance;
        this.className = className;
    }

    @Override
    public String toString() {
        return          "distance=" + distance +
                ", className='" + className;
    }
}
