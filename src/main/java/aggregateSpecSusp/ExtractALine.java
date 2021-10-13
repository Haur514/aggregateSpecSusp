package aggregateSpecSusp;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ExtractALine{
    ExtractALine(int lineNum){
            Path file = Paths.get("./TR.txt");
    try(BufferedReader br = Files.newBufferedReader(file)){
            String text;
            while((text = br.readLine()) != null){
                System.out.println(text);
            }
        }catch(Exception e){
            System.err.println(e);
        }
    }
}