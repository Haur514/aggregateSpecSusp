package aggregateSpecSusp.proximity;

import aggregateSpecSusp.route.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CalcProximity extends AbstractCalcProximity{
    private List<List<List<Integer>>> exRoute = new ArrayList<>();

    public CalcProximity(ExtractLineData extractLineData) {
        super(extractLineData);
        init();
        for (Integer failedTestNumber : failedTestList) {
            calcurateProximity(failedTestNumber);
        }
        takeWeightAverage();
        printWeight();
        pw.close();
    }

    private void init() {
        // init PointWriter
        file = new File("Weight.txt");
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            throw new RuntimeException("ERROR : FAILED CalcProximity INIT");
        }

        initSpectrumListAndWeight();

        // set exRoutes
        for(BlockedExecutionRoute blockedExecutionRoute : blockedExecutionRoutes){
            exRoute.add(blockedExecutionRoute.getBlockedExecutionRoutes());
        }
        setExecutionRoutes(exRoute);
    }
}
