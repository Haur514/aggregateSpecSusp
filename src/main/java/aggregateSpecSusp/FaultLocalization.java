package aggregateSpecSusp;

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

    FaultLocalization(ExtractLineData extractLineData, CalcProximity calcProximity) {
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.failedTestList = extractLineData.getFailedTestList();
        this.numberOfTest = extractLineData.getNumberOfTest();
        this.calcProximity = calcProximity;
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
                int ef = 0;
                int nf = 0;
                int ep = 0;
                int np = 0;
                for (int i = 0; i < numberOfTest; i++) {
                    if (failedTestList.contains(i)) {
                        switch (executionRoute.getExecutionRoutes().get(i).get(lineNum)) {
                        case 0:
                            nf += 1;
                            break;
                        case -1:
                            ef += 1;
                            break;
                        }
                    } else {
                        switch (executionRoute.getExecutionRoutes().get(i).get(lineNum)) {
                        case 0:
                            np += 1 + weight(i);
                            break;
                        case 1:
                            ep += 1 + weight(i);
                            break;
                        }
                    }
                }
                suspiciousValue.add(ochiai(ef, nf, ep, np));
                suspValueInfos.add(new SuspValueInfo(fileName, executionRoute.getExecutedLineNum().get(lineNum),
                        ochiai(ef, nf, ep, np)));
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
        pw.println(svi.getLineNum() + " " + svi.getFileName());
    }

    private double ochiai(int ef, int nf, int ep, int np) {
        return ef / Math.sqrt((ef + nf) * (ef + ep));
    }
}
