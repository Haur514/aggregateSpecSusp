package aggregateSpecSusp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * calc proximity with non-blocked spectrum for control experiment
 */
public class NonBlockedCalcProximity {
    private List<ExecutionRoute> executionRoutes;
    // failed test case passed route 'x'
    private List<Integer> numberOfCorrespoindingLine = new ArrayList<>();
    private List<Integer> numberOfNotCorrespoindingLine = new ArrayList<>();

    private Set<Integer> failedTestList;

    // regist weight of test case
    private List<Double> weightTestCase = new ArrayList<Double>();

    private int numberOfTest;

    private File file = new File("Weight.txt");
    private PrintWriter pw;

    NonBlockedCalcProximity(ExtractLineData extractLineData) {
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.failedTestList = extractLineData.getFailedTestList();
        init();
        for (Integer failedTestNumber : failedTestList) {
            calcurateProximity(failedTestNumber);
        }

        for (int i = 0; i < numberOfTest; i++) {
            double tmp = 0;
            for (int j = 0; j < failedTestList.size(); j++) {
                tmp += weightTestCase.get(i + j * numberOfTest);
            }
            weightTestCase.set(i, tmp / failedTestList.size());
        }
        for (int i = 0; i < numberOfTest; i++) {
            pw.println(i + "," + weightTestCase.get(i));
        }
        pw.close();
    }

    public List<Double> getWeightTestCase() {
        return this.weightTestCase;
    }

    private void init() {
        // init PointWriter
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            throw new RuntimeException("ERROR : FAILED CalcProximity INIT");
        }

        // regist numberOfTest
        numberOfTest = executionRoutes.get(0).getNumberOfTest();

        // init list that regist correspondingBlock / notcorrespoindingBlock
        for (int i = 0; i < numberOfTest; i++) {
            numberOfCorrespoindingLine.add(0);
            numberOfNotCorrespoindingLine.add(0);
        }

    }

    public void calcurateProximity(int failedTestNumber) {
        int numberOfTest = executionRoutes.get(0).getNumberOfTest();
        for (ExecutionRoute executionRoute : executionRoutes) {
            List<Integer> failedExecutionRoute = executionRoute.getExecutionRoutes().get(failedTestNumber);
            for (int i = 0; i < numberOfTest; i++) {
                int corresponding = 0;
                int notcorresponding = 0;
                if (failedTestList.contains(i)) {
                    continue;
                }
                List<Integer> blocked = executionRoute.getExecutionRoutes().get(i);
                for (int line = 0; line < failedExecutionRoute.size(); line++) {
                    if (failedExecutionRoute.get(line) == 0) {
                        if (blocked.get(line) == 1) {
                            notcorresponding++;
                        }
                    } else {
                        if (blocked.get(line) == 1) {
                            corresponding++;
                        } else {
                            notcorresponding++;
                        }
                    }
                }
                numberOfCorrespoindingLine.set(i, numberOfCorrespoindingLine.get(i) + corresponding);
                numberOfNotCorrespoindingLine.set(i, numberOfNotCorrespoindingLine.get(i) + notcorresponding);
            }
        }
        for (int i = 0; i < numberOfTest; i++) {
            weightTestCase.add(formura(numberOfCorrespoindingLine.get(i), numberOfNotCorrespoindingLine.get(i)));
        }
    }

    private double formura(int corresponding, int notcorresponding) {

        double ret = 0;
        int type = App.weightType;
        // Haruka 0
        // Yoshiruka 1
        // Ruka 2
        // Haru 3
        // Haka 4
        // Yoruka 5
        // Senko 6
        // YoshiokaHaruka 7
        switch (type) {
        case 0:
            ret = haruka(corresponding, notcorresponding);
            break;
        case 1:
            ret = (double) (corresponding * corresponding) / Math.sqrt((1 + notcorresponding));
            break;
        case 2:
            ret = ruka(corresponding,notcorresponding);
            break;
        case 3:
            ret = (double) (corresponding * corresponding * corresponding * corresponding)
                    / Math.sqrt((1 + notcorresponding));
            break;
        case 4:
            ret = haka(corresponding, notcorresponding);
            break;
        case 5:
            ret = yoruka((double) (corresponding / (corresponding + notcorresponding + 1.0)));
            break;
        case 6:
            ret = senko((double) (corresponding / (corresponding + notcorresponding + 1.0)));
            break;
        case 7:
            ret = yoshiokaharuka(corresponding, notcorresponding);
            break;
        }

        return ret;
    }

    private double ruka(int corresponding,int notcorresponding){
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = App.threshold;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt(x);
        }
    }

    private double haruka(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.7;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt((x));
        }
    }

    private double haka(int corresponding,int notcorresponding){
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = App.threshold;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / Math.sqrt(corresponding+notcorresponding);
        }
    }
    private double yoshiokaharuka(int corresponding, int notcorrespoinding) {
        double x;
        if (corresponding + notcorrespoinding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorrespoinding));
        }
        double threshold = 0.7;
        if (x < threshold) {
            return 1.0;
        } else {
            return yoruka(x);
        }
    }

    private double senko(double x) {
        double thresholdL = 0.25;
        double thresholdR = 0.75;
        if (x < thresholdL) {
            return 2.0 * thresholdL - x;
        } else if (thresholdR < x) {
            return 2.0 * thresholdR - x;
        } else {
            return x;
        }
    }

    private double yoruka(double x) {
        double A = 10.0;
        double Min = 1.0;
        double Pi = Math.PI;
        double ret = A * (-Math.sin((double) ((5.0 / 6.0) * Pi * x + (1.0 / 6.0) * Pi)) + (1.0 + Min));
        return ret;
    }
}
