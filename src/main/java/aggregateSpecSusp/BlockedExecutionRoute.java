package aggregateSpecSusp;

import java.util.List;
import java.util.ArrayList;

/**
 * Regist Blocked Execution Routes Per File; PASS : 1 FAIL : -1 NOT EXECUTED : 0
 */
public class BlockedExecutionRoute {
    private String fileName;
    private int numberOfTests;
    // regist execution route vertically
    private List<List<Integer>> blockedExecutionRoutes = new ArrayList<List<Integer>>();

    private String beforeTestResult = "";

    BlockedExecutionRoute(String fileName, int numberOfTests) {
        this.fileName = fileName;
        this.numberOfTests = numberOfTests;
        initBlockedExecutionRoutes();
    }

    private int getNumberOfBlocks() {
        return blockedExecutionRoutes.get(0).size();
    }

    private void initBlockedExecutionRoutes() {
        for (int i = 0; i < numberOfTests; i++) {
            List<Integer> tmp = new ArrayList<>();
            blockedExecutionRoutes.add(tmp);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void regist(String text) {
        String[] segment = text.split(",");
        if (isIdenticalWithBeforeLine(text, Integer.parseInt(segment[0]))) {
            return;
        }
        transBlockedExecutionRoutesToVertical(segment);
    }

    /**
     * check if a line's execution route is identical with before line
     */
    private boolean isIdenticalWithBeforeLine(String text, int lineNum) {
        if (!text.substring(7, (2 * numberOfTests)).equals(beforeTestResult)) {
            beforeTestResult = text.substring(7, (2 * numberOfTests));
            return false;
        }
        return true;
    }

    private void transBlockedExecutionRoutesToVertical(String[] segment) {
        for (int i = 0; i < numberOfTests; i++) {
            blockedExecutionRoutes.get(i).add(transExecutionRouteStringToInt(segment[i + 1]));
        }
    }

    public void addBlock(String text) {
        String[] segment = text.split(",");
        for (int i = 0; i < segment.length; i++) {
            blockedExecutionRoutes.get(i).add(transExecutionRouteStringToInt(segment[i]));
        }
    }

    public void printBlockedExecutionRoutes() {
        for (int i = 0; i < blockedExecutionRoutes.size(); i++) {
            System.out.println(blockedExecutionRoutes.get(i));
        }
    }

    public int transExecutionRouteStringToInt(String c) {
        char ch = c.charAt(0);
        switch (ch) {
            case ('o'):
                return 1;
            case ('x'):
                return -1;
            case ('-'):
                return 0;
        }
        throw new RuntimeException("ERROR in TR file");
    }

    public List<List<Integer>> getBlockedExecutionRoutes() {
        return blockedExecutionRoutes;
    }

    public void setBlockedExecutionRoutes(List<Integer> blockedExecutionRoute) {
        blockedExecutionRoutes.add(blockedExecutionRoute);
    }

    public void printBlockedExecutedRoutes() {
        System.out.println(fileName);
        for (int i = 0; i < numberOfTests; i++) {
            System.out.println(blockedExecutionRoutes.get(i));
        }
    }
}