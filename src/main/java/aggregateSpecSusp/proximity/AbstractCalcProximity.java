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
            System.out.println("checkNaN");
            System.exit(1);
        }
    }

    private void checkSize(int size){
        if(size == 0){
            System.out.println("Error: AbstractCalcProximity checkSize()");
            System.exit(1);
        }
    }

    protected void printWeight(){
        for (int i = 0; i < numberOfTest; i++) {
            if(failedTestList.contains(i)){
                continue;
            }
            if(!(weightTestCase.get(i) > 0.0)){
                System.out.println("ERROR in printWeight()");
                System.out.println(weightTestCase.get(i));
                System.exit(1);
            }
            pw.println(i + "," + Double.toString(weightTestCase.get(i)));
        }
    }

    final public void setWeight(int fail) {
        int numberOfTest = spectrum.get(0).size();
        for (int pass = 0; pass < numberOfTest; pass++) {
            if(failedTestList.contains(pass)){
                continue;
            }
            weightTestCase.set(pass,weightTestCase.get(pass)+formula.haka(getSimpsonNumerator(fail,pass),getNumberOfSetElements(fail)-getNumberOfIntersection(fail, pass)));
        }
    }

    final public List<Double> setProximityWith(int fail){
        List<Double> ret = new ArrayList<>();
        for (int pass = 0; pass < numberOfTest; pass++) {
            if(failedTestList.contains(pass)){
                continue;
            }
            ret.set(pass,ret.get(pass)+formula.senko(getJaccardCoefficient(fail, pass)));
        }
        return ret;
    }

    private boolean isNotZero(int num){
        if(num == 0){
            return false;
        }
        return true;
    }


    // ジャカード係数
    final public double getJaccardCoefficient(int fail,int pass){
        if(!isNotZero(getNumberOfSetElements(fail)+getNumberOfSetElements(pass)-getNumberOfIntersection(fail,pass))){
            System.err.println("Error : getJaccardCoefficient");
            System.exit(1);
        }
        return (double)getNumberOfIntersection(fail,pass)/(double)(getNumberOfSetElements(fail)+getNumberOfSetElements(pass)-getNumberOfIntersection(fail,pass));
    }
    final public int getJaccardDenominator(int fail,int pass){
        return getNumberOfSetElements(fail)+getNumberOfSetElements(pass)-getNumberOfIntersection(fail,pass);
    }

    final public int getJaccardNumerator(int fail,int pass){
        return getNumberOfIntersection(fail,pass);
    }


    // ダイス係数
    final public double getDiceCoefficient(int fail,int pass){
        if(!isNotZero(getDiceDenominator(fail,pass))){
            System.err.println("Error : getDiceCoefficient");
            System.exit(1);
        }
        return (double)getDiceNumerator(fail,pass)/(double)(getDiceDenominator(fail,pass));
    }

    final public int getDiceDenominator(int fail,int pass){
        return getNumberOfSetElements(fail)+getNumberOfSetElements(pass);
    }

    final public int getDiceNumerator(int fail,int pass){
        return 2*getNumberOfIntersection(fail,pass);
    }


    // シンプソン係数
    final public double getSimpsonCoefficient(int fail,int pass){
        if(getSimpsonDenominator(fail,pass) == 0){
            return 0;
        }
        return (double)getSimpsonNumerator(fail,pass)/(double)getSimpsonDenominator(fail,pass);
    }

    private int getSimpsonNumerator(int fail,int pass){
        return getNumberOfIntersection(fail,pass);
    }

    private int getSimpsonDenominator(int fail,int pass){
        return Math.min(getNumberOfSetElements(fail),getNumberOfSetElements(pass));
    }

    // fail,passの共通部分の要素数を取得
    private int getNumberOfIntersection(int fail,int pass){
        int ret = 0;
        for(int i = 0; i < spectrum.size();i++){
            List<Integer> failedExecutionRoute = spectrum.get(i).get(fail);
            List<Integer> passedExecutionRoute = spectrum.get(i).get(pass);
            for(int j = 0; j < failedExecutionRoute.size();j++){
                if(failedExecutionRoute.get(j) ==-1){
                    if(passedExecutionRoute.get(j) == 1){
                        ret++;
                    }
                }
            }
        }
        return ret;
    }

    // 引数に指定したテストケースの通過した行数を取得
    private int getNumberOfSetElements(int tc){
        int ret = 0;
        for(int i = 0; i < spectrum.size();i++){
            List<Integer> executionRoute = spectrum.get(i).get(tc);
            for(int j = 0; j < executionRoute.size();j++){
                if(executionRoute.get(j) != 0){
                    ret++;
                }
            }
        }
        return ret;
    }
}
