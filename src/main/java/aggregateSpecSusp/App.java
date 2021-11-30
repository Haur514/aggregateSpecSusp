/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aggregateSpecSusp;

public class App {
    // using in weighting test cases;
    public static int weightType;
    public static double threshold;

    public static void main(String[] args) {
        setArgs(args);

        ExtractLineData extractLineData = new ExtractLineData();
        CalcProximity calcProximity = new CalcProximity(extractLineData);
        NonBlockedCalcProximity nonBlockedCalcProximity = new NonBlockedCalcProximity(extractLineData);
        SpectrumBasedFaultLocalization spectrumBasedFaultLocalization = new SpectrumBasedFaultLocalization(
                extractLineData);
        BlockedSpectrumBasedFaultLocalization blockedSpectrumBasedFaultLocalization = new BlockedSpectrumBasedFaultLocalization(
                extractLineData, calcProximity.getWeightTestCase());
        NonBlockedSpectrumBasedFaultLocalization nonBlockedSpectrumBasedFaultLocalization = new NonBlockedSpectrumBasedFaultLocalization(
                extractLineData, nonBlockedCalcProximity.getWeightTestCase());
    }

    public static void setArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-weightType")) {
                if (i + 1 >= args.length) {
                    System.err.println("invarid command line");
                }
                weightType = Integer.parseInt(args[i + 1]);
            }

            if (args[i].equals("-threshold")) {
                if (i + 1 >= args.length) {
                    System.err.println("invarid command line");
                }
                
                threshold = Double.parseDouble(args[i + 1]);
                System.out.println(threshold);
            }
        }
        System.out.println(Double.toString(threshold)+" "+weightType);
    }
}
