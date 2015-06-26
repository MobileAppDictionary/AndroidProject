package datahelper;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;

import model.ListWords;

/**
 * Created by Huy on 6/26/2015.
 */
public class DataHelper {
    private static DataHelper _dataHelper = new DataHelper();
//
//    String path = "file:///android_asset/";
//    String pathFileInd = "data/EnVi.en-US/index.txt";
//    String getFileMean = "data/EnVi.en-US/dict.txt";
    TypeDict _typeDict;
    private ListWords _lstWords;

    public static ListWords LoadData(TypeDict typeDict){
        _dataHelper._typeDict = typeDict;

        return _dataHelper.loadData();
    }

    //lấy toàn bộ file index
    private ListWords loadData(){
        _lstWords = new ListWords();


        try {
            InputStreamReader reader = new InputStreamReader(_typeDict.isWords);
            BufferedReader bufReader = new BufferedReader(reader);

            String line = "";
            while((line = bufReader.readLine()) != null){
                _lstWords.AddWord(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _lstWords;
    }

    //lấy nghĩa của từ thông qua vị trí và độ dài trong file dict
    public static String GetMeaning(int offset, int length){
        return _dataHelper.getMeaning(offset,length);
    }

    private String getMeaning(int offset, int length){
        String res = null;

        try {

            byte[] readBytes = new byte[length];
            //_typeDict.isMeaning.read(readBytes, offset,length);
            _typeDict.isMeaning.skip(offset);
            _typeDict.isMeaning.read(readBytes, 0, length);
            res = new String(readBytes, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
