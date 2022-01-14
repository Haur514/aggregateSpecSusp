package aggregateSpecSusp.route;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract a line and get information about TestResult : PASS / FAIL / Spectrum
 */
public class ExtractLineData {
    private int numberOfTests;
    private List<ExecutionRoute> executionRoutes = new ArrayList<>();
    private List<BlockedExecutionRoute> blockedExecutionRoutes = new ArrayList<>();

    private Set<Integer> failedTestList = new HashSet<>();

    private PrintWriter pwBlockedExecutionRoutes;

    public ExtractLineData() {
        List<String> text = readTRText();
        numberOfTests = getNumberOfTests(text.get(0));
        setExecutionRoutes(text);
        setBlockedExecutionRoutes(text);
        setFailedTestNumber();
        initPW();
        printBlockedExecutedRoutes();
    }
    public Set<Integer> getFailedTestList() {
        return this.failedTestList;
    }

    private void initPW(){
        File file = new File("BlockedExecutionRoute.txt");
        try {
            pwBlockedExecutionRoutes = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            throw new RuntimeException("ERROR : FAILED SBFL INIT");
        }
    }

    private void printBlockedExecutedRoutes(){
        for(BlockedExecutionRoute blockedExecutionRoute:blockedExecutionRoutes){
            pwBlockedExecutionRoutes.println(blockedExecutionRoute.getFileName());
            for(int j = 0;j < blockedExecutionRoute.getBlockedExecutionRoutes().get(0).size();j++){
                for(int i = 0; i < blockedExecutionRoute.getBlockedExecutionRoutes().size();i++){
                    pwBlockedExecutionRoutes.print(blockedExecutionRoute.getBlockedExecutionRoutes().get(i).get(j));
                    pwBlockedExecutionRoutes.print(",");
                }
                pwBlockedExecutionRoutes.println();
            }
        }
        pwBlockedExecutionRoutes.close();
    }

    private void setFailedTestNumber() {
        for (int i = 0; i < numberOfTests; i++) {
            for (BlockedExecutionRoute ber : blockedExecutionRoutes) {
                for (int line = 0; line < ber.getBlockedExecutionRoutes().get(0).size(); line++) {
                    if (ber.getBlockedExecutionRoutes().get(i).get(line) == -1) {
                        failedTestList.add(i);
                        continue;
                    } else if (ber.getBlockedExecutionRoutes().get(i).get(line) == 1) {
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

    public int getNumberOfTest() {
        return this.numberOfTests;
    }

    public List<ExecutionRoute> getExecutionRoutes() {
        return executionRoutes;
    }
}