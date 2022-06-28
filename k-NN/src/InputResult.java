public class InputResult {
    public int k;
    boolean isInOneFile;
    String filePath1;
    String filePath2;

    public InputResult(int k, boolean isInOneFile, String filePath1, String filePath2) {
        this.k = k;
        this.isInOneFile = isInOneFile;
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
    }
}
