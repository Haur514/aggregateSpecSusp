package aggregateSpecSusp.proximity.jaccard;

import java.util.*;

import aggregateSpecSusp.App;
import aggregateSpecSusp.route.ExecutionRoute;
import aggregateSpecSusp.route.ExtractLineData;

public class CalcInJaccard {
    private List<List<List<Integer>>> routes = new ArrayList<>();
    private Set<Integer> failedTestList = new HashSet<>();
    private List<Double> weight = new ArrayList<>();
    private int numberOfTest;

    // 0 : passOnly
    // 1 : failOnly
    // 2 : both
    // 3 : neither
    private int[] countup = new int[4];

    public CalcInJaccard(ExtractLineData extractLineData){
        setRoutes(extractLineData);
        setFailedTestList(extractLineData);
        this.numberOfTest = routes.get(0).size();
        for(int i = 0; i < numberOfTest;i++){
            weight.add(0.0);
        }
        calcCoincidence();
    }

    private void setRoutes(ExtractLineData extractLineData){
        for(ExecutionRoute executionRoute : extractLineData.getExecutionRoutes()){
            routes.add(executionRoute.getExecutionRoutes());
        }
    }

    private void setFailedTestList(ExtractLineData extractLineData){
        this.failedTestList = extractLineData.getFailedTestList();
    }

    private void calcCoincidence(){
        for(Integer fail : failedTestList){
            for(int pass = 0; pass < numberOfTest;pass++){
                if(failedTestList.contains(pass)){
                    continue;
                }
                calcWeight(fail,pass);
            }
        }
        for(int i = 0; i < numberOfTest;i++){
            weight.set(i,weight.get(i) / failedTestList.size());
        }
    }

    final public List<Double> getWeight(){
        return this.weight;
    }

    private void calcWeight(int fail,int pass){
        calcInJaccard(fail,pass);
        double jacCoef = getJaccardCoef();
        if(jacCoef <= 0.25){
            weight.set(pass,weight.get(pass)+1-jacCoef);
        }else if(jacCoef >= 0.75){
            weight.set(pass,weight.get(pass)+1+0.75-jacCoef);
        }else{
            weight.set(pass,weight.get(pass)+jacCoef);
        }
        // if(jacCoef < App.threshold){
        //     weight.set(pass,weight.get(pass)+1.0);
        // }else{
        //     weight.set(pass,weight.get(pass) + numberOfTest*jacCoef + 1.0);
        // }
    }

    private void calcInJaccard(int fail,int pass){
        for(List<List<Integer>> route : routes){
            List<Integer> failRoute = route.get(fail);
            List<Integer> passRoute = route.get(pass);
            countUp(failRoute,passRoute);
        }
    }
    private void countUp(List<Integer> failRoute,List<Integer> passRoute){
        for(int i = 0;i < failRoute.size();i++){
            if(failRoute.get(i) == -1){
                if(passRoute.get(i) == 1){
                    countup[2]++;
                }else if(passRoute.get(i) == 0){
                    countup[1]++;
                }
            }else{
                if(passRoute.get(i) == 1){
                    countup[0]++;
                }else if(passRoute.get(i) == 0){
                    countup[3]++;
                }
            }
        }
    }
    private int getNumberOfPassOnly(){
        return this.countup[0];
    }
    private int getNumberOfFailOnly(){
        return this.countup[1];
    }
    private int getNumberOfBoth(){
        return this.countup[2];
    }
    private int getNumberOfNeither(){
        return this.countup[3];
    }
    final public double getJaccardCoef(){
        double mother = getNumberOfPassOnly() + getNumberOfFailOnly()+getNumberOfBoth();
        double son = getNumberOfBoth();
        if(son == 0.0){
            son = 0.001;
        }
        if(!(mother > 0.0)){
            System.err.println("Error : getJaccardCoef invalid mother");
        }
        return son / mother;
    }
}
