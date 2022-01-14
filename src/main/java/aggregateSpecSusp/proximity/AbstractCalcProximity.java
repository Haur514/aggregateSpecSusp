package aggregateSpecSusp.proximity;

import aggregateSpecSusp.route.*;
import aggregateSpecSusp.formula.Formula;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public abstract class AbstractCalcProximity {

    protected Set<Integer> failedTestList;
    protected List<Double> weightTestCase = new ArrayList<Double>();
    protected int numberOfTest;

    protected ExtractLineData extractLineData;
    protected List<ExecutionRoute> executionRoutes;
    protected List<BlockedExecutionRoute> blockedExecutionRoutes;

    protected PrintWriter pw;
    protected File file;

    protected List<List<List<Integer>>> spectrum;

    protected List<Integer> numberOfCorrespondingBlock = new ArrayList<>();
    protected List<Integer> numberOfNotCorrespondingBlock = new ArrayList<>();

    private Formula formula;

    AbstractCalcProximity(ExtractLineData extractLineData){
        formula = new Formula();
        this.extractLineData = extractLineData;
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.blockedExecutionRoutes = extractLineData.getBlockedExecutionRoutes();
        setFailedTestList(extractLineData.getFailedTestList());
        setNumberOfTest(blockedExecutionRoutes.get(0).getBlockedExecutionRoutes().size());
    }

    public List<Double> getWeightTestCase() {
        return this.weightTestCase;
    }

    protected void setFailedTestList(Set<Integer> list){
        this.failedTestList = list;
    }

    protected void setNumberOfTest(int num){
        this.numberOfTest = num;
    }

    protected void initSpectrumListAndWeight(){
        for (int i = 0; i < numberOfTest; i++) {
            numberOfCorrespondingBlock.add(0);
            numberOfNotCorrespondingBlock.add(0);
            weightTestCase.add(0.0);
        }
    }

    protected void setExecutionRoutes(List<List<List<Integer>>> exRoutes){
        this.spectrum = exRoutes;
    }

    protected void takeWeightAverage(){
        for (int i = 0; i < numberOfTest; i++) {
            checkNaN(weightTestCase.get(i));
            checkSize(failedTestList.size());
            weightTestCase.set(i, weightTestCase.get(i) / failedTestList.size());
        }
    }

    private void checkNaN(Double weight){
        if(Double.toString(weight).equals("NaN")){
            System.out.println("NaN");
            System.exit(1);
        }
    }

    private void checkSize(int size){
        if(size == 0){
            System.out.println("error");
            System.exit(1);
        }
    }

    protected void printWeight(){
        for (int i = 0; i < numberOfTest; i++) {
            if(failedTestList.contains(i)){
                continue;
            }
            if(!(weightTestCase.get(i) > 0.0)){
                System.out.println("ERROR NaN");
                System.exit(1);
            }
            pw.println(i + "," + Double.toString(weightTestCase.get(i)));
        }
    }

    final public void calcurateProximity(int failedTestNumber) {
        int numberOfTest = spectrum.get(0).size();
        countUpExeRoutes(failedTestNumber);
        for (int i = 0; i < numberOfTest; i++) {
            weightTestCase.set(i,weightTestCase.get(i)+formula.formura(numberOfCorrespondingBlock.get(i), numberOfNotCorrespondingBlock.get(i)));
        }
    }
    private void countUpExeRoutes(int failedTestNumber){
        for (List<List<Integer>> executionRoute : spectrum) {
            List<Integer> failedExecutionRoute = executionRoute.get(failedTestNumber);
            for (int i = 0; i < numberOfTest; i++) {
                if (failedTestList.contains(i)) {
                    continue;
                }
                int corresponding = 0;
                int notcorresponding = 0;
                List<Integer> route = executionRoute.get(i);
                for(int line = 0; line < failedExecutionRoute.size(); line++) {
                    if (failedExecutionRoute.get(line) == 0) {
                        if (route.get(line) == 1) {
                            notcorresponding++;
                        }
                    } else {
                        if (route.get(line) == 1) {
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
    }
}
