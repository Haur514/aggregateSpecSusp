/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aggregateSpecSusp;

public class App {
    // using in weighting test cases;
    public static int weightType;

    public static void main(String[] args) {
        setArgs(args);

        ExtractLineData extractLineData = new ExtractLineData();

        CalcProximity calcProximity = new CalcProximity(extractLineData);
        SpectrumBasedFaultLocalization spectrumBasedFaultLocalization = new SpectrumBasedFaultLocalization(
                extractLineData);
        BlockedSpectrumBasedFaultLocalization blockedSpectrumBasedFaultLocalization = new BlockedSpectrumBasedFaultLocalization(
                extractLineData, calcProximity);
    }

    public static void setArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-weightType")) {
                if (i + 1 >= args.length) {
                    System.err.println("invaridate command line");
                }
                weightType = Integer.parseInt(args[i + 1]);
                break;
            }
        }
    }
}
