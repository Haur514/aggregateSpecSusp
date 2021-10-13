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

    public void readALine(String text){
        String[] split;
        split = text.split("|");
        System.out.println(split[0]);
        executedLineNum.add(Integer.parseInt(split[0]));
        for(int i = 1; i < split.length;i++){
            if(split[i].equals("o")){
                executionRoute.add(0);
            }else if(split[i].equals("x")){
                executionRoute.add(1);
            }else{
                executionRoute.add(-1);
            }
        }
    }
}
