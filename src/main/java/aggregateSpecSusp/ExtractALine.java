package aggregateSpecSusp;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * TR.txtを読み込み，実行経路情報をテストケースごとにリスト化する．
 */
class ExtractALine{
    private List<FileNameAndLineNum> fileNameAndLineNumList = new ArrayList<>();
    ExtractALine(int lineNum){
            Path file = Paths.get("./TR.txt");
    try(BufferedReader br = Files.newBufferedReader(file)){
            String text;
            /**
             * 次に格納するべきfileNameAndLineNumListのインデックスを記録
             * 
             * 
             */
            int currentIndex=-1;
            while((text = br.readLine()) != null){

                /**
                 * textが実行経路情報を表していた場合の処理
                 */
                if(text.charAt(0) == '|'){
                    fileNameAndLineNumList.get(currentIndex).readALine(text);
                }
                 /**
                 * textがファイル名を表していた場合の処理
                 */
                else{
                    fileNameAndLineNumList.add(new FileNameAndLineNum(text));
                    currentIndex++;
                }
                System.out.println(text);
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }
    public List<FileNameAndLineNum> getFileNameAndLineNumList() {
        return fileNameAndLineNumList;
    }


    /**
     * 実行経路情報を"|"ごとに区切り，テストケースごとの実行経路の情報としてリスト化する
     */
    /*
    private List<List<String>> parse(String text){
        List<List<String>> ret = null;

        return ret;
    }
    */
}