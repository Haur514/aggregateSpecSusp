package aggregateSpecSusp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract a line and get information about TestResult : PASS / FAIL / Spectrum
 */
class ExtractLineData {
    private int numberOfTests;
    private List<ExecutionRoute> executionRoutes = new ArrayList<>();
    private List<BlockedExecutionRoute> blockedExecutionRoutes = new ArrayList<>();

    private List<Integer> failedTestNumber = new ArrayList<>();

    ExtractLineData() {
        List<String> text = readTRText();
        numberOfTests = getNumberOfTests(text.get(0));
        setFailedTestNumber(text.get(2));
        setExecutionRoutes(text);
        setBlockedExecutionRoutes(text);
    }

    private void setFailedTestNumber(String text) {
        String[] segment = text.split(",");
        for (int i = 0; i < numberOfTests; i++) {
            if (segment[i + 1].equals("-")) {
                failedTestNumber.add(i);
            }
        }
    }

    public List<Integer> getFailedTestNumber() {
        return this.failedTestNumber;
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

    private void setBlockedExecutionRoutes(List<String> text) {
        BlockedExecutionRoute currentBlockedExecutionRoute = new BlockedExecutionRoute(text.get(1), numberOfTests);
        for (int lineNum = 2; lineNum < text.size(); lineNum++) {
            if (isFileName(text.get(lineNum))) {
                blockedExecutionRoutes.add(currentBlockedExecutionRoute);
                currentBlockedExecutionRoute = new BlockedExecutionRoute(text.get(lineNum), numberOfTests);
            } else {
                currentBlockedExecutionRoute.regist(text.get(lineNum));
            }
        }
        blockedExecutionRoutes.add(currentBlockedExecutionRoute);
    }

    public List<BlockedExecutionRoute> getBlockedExecutionRoutes() {
        return this.blockedExecutionRoutes;
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

    public List<ExecutionRoute> getExecutionRoutes() {
        return executionRoutes;
    }
}