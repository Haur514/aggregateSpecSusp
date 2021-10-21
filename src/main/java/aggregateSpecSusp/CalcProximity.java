package aggregateSpecSusp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    CalcProximity(ExtractLineData extractLineData) {
        this.extractLineData = extractLineData;
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.blockedExecutionRoutes = extractLineData.getBlockedExecutionRoutes();
        this.failedTestList = extractLineData.getFailedTestList();
        init();
        for (Integer failedTestNumber : failedTestList) {
            calcurateProximity(failedTestNumber);
        }
    }

    public List<Double> getWeightTestCase() {
        return this.weightTestCase;
    }

    private void init() {
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
            System.out.println(formura(numberOfCorrespondingBlock.get(i), numberOfNotCorrespondingBlock.get(i)));
        }
        for (int i = 0; i < weightTestCase.size(); i++) {
            System.out.println(weightTestCase.get(i));
        }
    }

    private double formura(int corresponding, int notcorresponding) {

        double ret = 0;
        int type = 0;
        // Haruka 0
        switch (type) {
        case 0:
            ret = (double) corresponding / (0.1 + notcorresponding);
            break;
        }

        return ret;
    }

}
