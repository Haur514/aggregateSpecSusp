package aggregateSpecSusp;

import aggregateSpecSusp.route.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract a line and get information about TestResult : PASS / FAIL / Spectrum
 */
class ExtractLineDataForOnlySBFL {
    private int numberOfTests;
    private List<ExecutionRoute> executionRoutes = new ArrayList<>();

    private Set<Integer> failedTestList = new HashSet<>();

    ExtractLineDataForOnlySBFL() {
        List<String> text = readTRText();
        numberOfTests = getNumberOfTests(text.get(0));
        setExecutionRoutes(text);
        setFailedTestNumber();
    }

    public Set<Integer> getFailedTestList() {
        return this.failedTestList;
    }

    private void setFailedTestNumber() {
        for (int i = 0; i < numberOfTests; i++) {
            for (ExecutionRoute ber : executionRoutes) {
                for (int line = 0; line < ber.getExecutionRoutes().get(0).size(); line++) {
                    if (ber.getExecutionRoutes().get(i).get(line) == -1) {
                        failedTestList.add(i);
                        continue;
                    } else if (ber.getExecutionRoutes().get(i).get(line) == 1) {
                        continue;
                    }
                }
            }
        }
    }

    private void setExecutionRoutes(List<String> text) {
        ExecutionRoute currentExecutionRoute = new ExecutionRoute(text.get(1), numberOfTests);
        for (int lineNum = 2; lineNum < text.size(); lineNum++) {
            if (isFileName(text.get(lineNum))) {
                executionRoutes.add(currentExecutionRoute);
                currentExecutionRoute = new ExecutionRoute(text.get(lineNum), numberOfTests);
            } else {
                currentExecutionRoute.regist(text.get(lineNum));
            }
        }
        executionRoutes.add(currentExecutionRoute);
    }

    private List<String> readTRText() {
        Path file = Paths.get("./TR.txt");
        List<String> text;
        try {
            text = Files.readAllLines(file);
        } catch (IOException e) {
            text = null;
            e.printStackTrace();
        }
        if (text.isEmpty()) {
            System.err.println("There is no data in TR.txt");
            System.exit(1);
        }
        return text;
    }

    private boolean isFileName(String text) {
        if ('0' <= text.charAt(0) && text.charAt(0) <= '9') {
            return false;
        }
        return true;
    }

    private int getNumberOfTests(String text) {
        return text.split(",").length;
    }

    public int getNumberOfTest() {
        return this.numberOfTests;
    }

    public List<ExecutionRoute> getExecutionRoutes() {
        return executionRoutes;
    }
}