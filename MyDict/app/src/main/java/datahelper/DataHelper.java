package datahelper;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;

import model.ListWords;

/**
 * Created by Huy on 6/26/2015.
 */
public class DataHelper {
    private static DataHelper _dataHelper = new DataHelper();

    String path = Environment.getExternalStorageState();
    String pathFileInd = "/Data/EnVi.en-US/index.txt";
    String getFileMean = "/Data/EnVi.en-US/dict.txt";

    private ListWords _lstWords;

    public static ListWords LoadData(){
        return _dataHelper.loadData();
    }

    private ListWords loadData(){
        _lstWords = new ListWords();

        File file = new File(path + pathFileInd);
        try {
            FileInputStream iStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(iStream);
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

    public static String GetMeaning(int offset, int length){
        return _dataHelper.getMeaning(offset,length);
    }

    private String getMeaning(int offset, int length){
        String res = null;
        File file = new File(path + getFileMean);
        try {
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            reader.seek(offset);
            byte[] readBytes = new byte[length];
            reader.read(readBytes, offset,length);

            res = readBytes.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
