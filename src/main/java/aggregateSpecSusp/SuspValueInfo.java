package aggregateSpecSusp;

public class SuspValueInfo {
    private String fileName;
    private int lineNum;
    private double suspValue;

    public SuspValueInfo(String fileName, int lineNum, double suspValue) {
        this.setFileName(fileName);
        this.setLineNum(lineNum);
        this.setSuspValue(suspValue);
    }

    public double getSuspValue() {
        return suspValue;
    }

    private void setSuspValue(double suspValue) {
        this.suspValue = suspValue;
    }

    public int getLineNum() {
        return lineNum;
    }

    private void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getFileName() {
        return fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
