package aggregateSpecSusp;

import java.util.ArrayList;
import java.util.List;

public class CalcProximity {
    private ExtractLineData extractLineData;
    private List<ExecutionRoute> executionRoutes;
    private List<BlockedExecutionRoute> blockedExecutionRoutes;

    CalcProximity(ExtractLineData extractLineData) {
        this.extractLineData = extractLineData;
        this.executionRoutes = extractLineData.getExecutionRoutes();
        this.blockedExecutionRoutes = extractLineData.getBlockedExecutionRoutes();

    }

    private void calcurateProximity(int failedTestNumber, List<BlockedExecutionRoute> blockedExecutionRoutes) {

    }

}
