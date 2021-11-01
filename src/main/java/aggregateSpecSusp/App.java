/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aggregateSpecSusp;

public class App {
    public static void main(String[] args) {
        ExtractLineData extractLineData = new ExtractLineData();

        CalcProximity calcProximity = new CalcProximity(extractLineData);
        SpectrumBasedFaultLocalization spectrumBasedFaultLocalization = new SpectrumBasedFaultLocalization(
                extractLineData);
        BlockedSpectrumBasedFaultLocalization blockedSpectrumBasedFaultLocalization = new BlockedSpectrumBasedFaultLocalization(
                extractLineData, calcProximity);
    }
}
