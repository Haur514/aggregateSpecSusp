package aggregateSpecSusp.fl;

import aggregateSpecSusp.route.*;
import aggregateSpecSusp.*;
import aggregateSpecSusp.proximity.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public abstract class FaultLocalization {
    private List<Double> suspiciousValue = new ArrayList<>();
    private List<ExecutionRoute> executionRoutes;

    public List<Double> weightTestCase;

    private List<SuspValueInfo> suspValueInfos = new ArrayList<>();

    private Set<Integer> failedTestList;

    CalcProximity calcProximity;

    private int numberOfTest;
    File file;
    private PrintWriter pw;

    FaultLocalization(ExtractLineData extractLineData) {
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.failedTestList = extractLineData.getFailedTestList();
        this.numberOfTest = extractLineData.getNumberOfTest();
        setFileName();
        init();
        run();
        sort();
        print();
        pw.close();
    }

    FaultLocalization(ExtractLineData extractLineData, List<Double> weightTestCase) {
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.failedTestList = extractLineData.getFailedTestList();
        this.numberOfTest = extractLineData.getNumberOfTest();
        this.weightTestCase = weightTestCase;
        setFileName();
        init();
        run();
        sort();
        print();
        pw.close();
    }

    abstract void setFileName();

    private void init() {
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            throw new RuntimeException("ERROR : FAILED SBFL INIT");
        }
    }

    private void run() {
        for (ExecutionRoute executionRoute : executionRoutes) {
            String fileName = executionRoute.getFileName();
            for (int lineNum = 0; lineNum < executionRoute.getExecutionRoutes().get(0).size(); lineNum++) {
                double ef = 0;
                double nf = 0;
                double ep = 0;
                double np = 0;
                for (int i = 0; i < numberOfTest; i++) {
                    if (failedTestList.contains(i)) {
                        switch (executionRoute.getExecutionRoutes().get(i).get(lineNum)) {
                        case 0:
                            nf += 1;
                            break;
                        case -1:
                            ef += 1;
                            break;
                        default:
                            System.err.println("invalid number at executionRoute");
                            System.exit(1);
                            break;
                        }
                    } else {
                        switch (executionRoute.getExecutionRoutes().get(i).get(lineNum)) {
                        case 0:
                            np += weight(i);
                            break;
                        case 1:
                            ep += weight(i);
                            break;
                        default:
                            System.err.println("invalid number at executionRoute");
                            System.exit(1);
                            break;
                        }
                    }
                }
                double susp;
                if(failedTestList.size() == numberOfTest){
                    susp = 1.0;
                }else{
                    susp = formura(ef, nf, ep, np);
                }
                suspiciousValue.add(susp);
                suspValueInfos.add(new SuspValueInfo(fileName, executionRoute.getExecutedLineNum().get(lineNum),susp));
            }
        }
    }

    private void print() {
        for (SuspValueInfo svi : suspValueInfos) {
            registLineNumAndSuspValue(svi);
        }
    }

    /**
     * sort along suspicious value;
     */
    private void sort() {
        Comparator<SuspValueInfo> Comp = Comparator.comparing(SuspValueInfo::getSuspValue, Comparator.reverseOrder());
        suspValueInfos.sort(Comp);
    }

    abstract double weight(int testCaseNum);

    public void registLineNumAndSuspValue(SuspValueInfo svi) {
        pw.println(svi.getLineNum() + " " + svi.getFileName() + " " + svi.getSuspValue());
    }

    private double formura(final double ef, final double nf, final double ep, final double np){
        switch(App.formuraType){
            case 0:
            return ochiai(ef, nf, ep, np);
            case 1:
            return zoltar(ef,nf,ep,np);
            case 2:
            return jaccard(ef,nf,ep,np);
            case 3:
            return ample(ef,nf,ep,np);
            case 4:
            return tarantula(ef, nf, ep, np);
        }
        System.exit(1);
        return ochiai(ef,nf,ep,np);
    }

    private double ochiai(final double ef, final double nf, final double ep, final double np) {
        return ef / Math.sqrt((ef + nf) * (ef + ep));
    }

    private double zoltar(final double ef, final double nf, final double ep, final double np) {
        return ef / (ef + nf + ep + 10000d * nf * ep / ef);
    }

    private double jaccard(final double ef, final double nf, final double ep, final double np) {
        return ef / (ef + nf + ep);
    }

    private double ample(final double ef, final double nf, final double ep, final double np) {
        return Math.abs(ef / (ef + nf) - ep / (ep + np));
    }

    private double tarantula(final double ef, final double nf, final double ep, final double np) {
        return (ef / (ef + nf)) / (ef / (ef + nf) + ep / (ep + np));
    }
}
