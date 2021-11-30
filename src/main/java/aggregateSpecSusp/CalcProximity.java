package aggregateSpecSusp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class CalcProximity {
    private ExtractLineData extractLineData;
    private List<ExecutionRoute> executionRoutes;
    private List<BlockedExecutionRoute> blockedExecutionRoutes;
    // failed test case passed route 'x'
    private List<Integer> numberOfCorrespondingBlock = new ArrayList<>();
    private List<Integer> numberOfNotCorrespondingBlock = new ArrayList<>();

    private Set<Integer> failedTestList;

    // regist weight of test case
    private List<Double> weightTestCase = new ArrayList<Double>();

    private int numberOfTest;

    private File file = new File("Weight.txt");
    private PrintWriter pw;

    CalcProximity(ExtractLineData extractLineData) {
        this.extractLineData = extractLineData;
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.blockedExecutionRoutes = extractLineData.getBlockedExecutionRoutes();
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
        numberOfTest = blockedExecutionRoutes.get(0).getBlockedExecutionRoutes().size();

        // init list that regist correspondingBlock / notcorrespoindingBlock
        for (int i = 0; i < numberOfTest; i++) {
            numberOfCorrespondingBlock.add(0);
            numberOfNotCorrespondingBlock.add(0);
        }

    }

    public void calcurateProximity(int failedTestNumber) {
        int numberOfTest = blockedExecutionRoutes.get(0).getBlockedExecutionRoutes().size();
        for (BlockedExecutionRoute blockedExecutionRoute : blockedExecutionRoutes) {
            List<Integer> failedBlockedExecutionRoute = blockedExecutionRoute.getBlockedExecutionRoutes()
                    .get(failedTestNumber);
            for (int i = 0; i < numberOfTest; i++) {
                int corresponding = 0;
                int notcorresponding = 0;
                if (failedTestList.contains(i)) {
                    continue;
                }
                List<Integer> blocked = blockedExecutionRoute.getBlockedExecutionRoutes().get(i);
                for (int line = 0; line < failedBlockedExecutionRoute.size(); line++) {
                    if (failedBlockedExecutionRoute.get(line) == 0) {
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
                numberOfCorrespondingBlock.set(i, numberOfCorrespondingBlock.get(i) + corresponding);
                numberOfNotCorrespondingBlock.set(i, numberOfNotCorrespondingBlock.get(i) + notcorresponding);
            }
        }
        for (int i = 0; i < numberOfTest; i++) {
            weightTestCase.add(formura(numberOfCorrespondingBlock.get(i), numberOfNotCorrespondingBlock.get(i)));
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
            ret = yoshiruka(corresponding, notcorresponding);
            break;
        case 2:
            ret = ruka(corresponding,notcorresponding);
            break;
        case 3:
            ret = haru(corresponding,notcorresponding);
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
            case 8:
            ret = functionC(corresponding,notcorresponding);
            break;
        }

        return ret;
    }

private double functionC(int corresponding,int notcorresponding){
    double x;
    if (corresponding + notcorresponding == 0) {
        x = 1.0;
    } else {
        x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
    }
    double threshold = 0.75;
    if (x < threshold) {
        return 1.0;
    } else {
        return (double) corresponding / Math.sqrt((double)corresponding + (double)notcorresponding);
    }
}

    private double yoshiruka(int corresponding,int notcorresponding){
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.75;
        if (x < threshold) {
            return 1.0;
        } else {
            return 1.0 + 10.0*(x - 0.75);
        }
    }

    private double haru(int corresponding,int notcorresponding){
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.25;
        if(x < threshold){
            return x + 0.75;
        }else if (threshold <= x && x <= (1-threshold)) {
            return 1.0;
        } else {
            return 1.0+x-0.75;
        }
    }

    private double haruka(int corresponding, int notcorresponding) {
        double x;
        if (corresponding + notcorresponding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorresponding));
        }
        double threshold = 0.8;
        if (x < threshold) {
            return 1.0;
        } else {
            return (double) corresponding / x;
        }
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
            return (double) corresponding / Math.sqrt((x));
        }
    }

    private double haka(int corresponding, int notcorresponding) {
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
            return (double) (corresponding * corresponding * corresponding) / Math.sqrt(Math.sqrt((x)));
        }
    }

    private double yoshiokaharuka(int corresponding, int notcorrespoinding) {
        double x;
        if (corresponding + notcorrespoinding == 0) {
            x = 1.0;
        } else {
            x = (double) ((double) corresponding / (double) (corresponding + notcorrespoinding));
        }
        double threshold = 0.75;
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
