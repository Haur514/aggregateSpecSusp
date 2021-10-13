package aggregateSpecSusp;

import java.util.List;
import java.util.ArrayList;

/**
 * ファイル名と，実行経路を記録するクラス
 * 実行経路はテストケースごとに縦方向に記録
 * 0:PASSで通過
 * 1:FAILで通過
 * -1:通過していない
 */
public class FileNameAndLineNum {
    String fileName;
    List<Integer> executionRoute = new ArrayList<>();
    List<Integer> executedLineNum = new ArrayList<>();
    FileNameAndLineNum(String fileName){
        this.fileName = fileName;
    }

    public void addExecutionRoute(int passedType){

    }
}
