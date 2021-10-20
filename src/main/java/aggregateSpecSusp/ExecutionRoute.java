package aggregateSpecSusp;

import java.util.List;
import java.util.ArrayList;

/**
 * Regist Execution Routes Per File; PASS : 1 FAIL : -1 NOT EXECUTED : 0
 */
public class ExecutionRoute {
    private String fileName;
    private List<List<Integer>> executionRoutes = new ArrayList<List<Integer>>();
    private List<Integer> executedLineNum = new ArrayList<>();

    private int numberOfTest;

    ExecutionRoute(String fileName, int numberOfTest) {
        this.numberOfTest = numberOfTest;
        this.fileName = fileName;
        init(numberOfTest);
    }

    public void init(int numberOfTest) {
        for (int i = 0; i < numberOfTest; i++) {
            List<Integer> executionRoute = new ArrayList<Integer>();
            executionRoutes.add(executionRoute);
        }
    }

    /**
     * Regist the Execution Route of a line
     * 
     * @param text
     */
    public void regist(String text) {
        String[] segment = text.split(",");
        executedLineNum.add(Integer.parseInt(segment[0]));
        transExecutionRoutesToVertical(segment);
        System.out.println(text);
    }

    /**
     * while kGP regists Execution Route horizontally, but transform it regists
     * vertically.
     * 
     * @param segment
     */
    public void transExecutionRoutesToVertical(String[] segment) {
        for (int i = 0; i < executionRoutes.size(); i++) {
            if (segment[i + 1].equals("o")) {
                executionRoutes.get(i).add(1);
            } else if (segment[i + 1].equals("x")) {
                executionRoutes.get(i).add(-1);
            } else {
                executionRoutes.get(i).add(0);
            }
        }
    }

    private int getIndexAtLineNum(int lineNum) {
        for (int i = 0; i < executedLineNum.size(); i++) {
            if (executedLineNum.get(i) == lineNum) {
                return i;
            }
        }
        throw new RuntimeException("Cannnot find");
    }

    public void printExecutedRoutes() {
        System.out.println(fileName);
        for (int j = 0; j < executedLineNum.size(); j++) {
            System.out.print(executedLineNum.get(j) + ",");
        }
        System.out.println();
        for (int j = 0; j < executionRoutes.size(); j++) {
            System.out.println(executionRoutes.get(j));
        }
    }
}
