package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 6/26/2015.
 */
public class ListWords {
    public List<String> LstKey;
    public List<String> LstInd;
    public List<String> LstLen;

    public ListWords(){
        LstKey = new ArrayList<>();
        LstInd = new ArrayList<>();
        LstLen = new ArrayList<>();
    }

    public void AddWord(String line){
        String arrStr[] = line.split("\t");
        LstKey.add(arrStr[0]);
        LstInd.add(arrStr[1]);
        LstLen.add(arrStr[2]);
    }
}
