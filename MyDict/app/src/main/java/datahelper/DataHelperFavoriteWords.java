package datahelper;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import model.ListWords;

/**
 * Created by Huy on 6/26/2015.
 */
public class DataHelperFavoriteWords {
    private static DataHelperFavoriteWords _dateHelper = new DataHelperFavoriteWords();
    private List<String> _lstFavoriteWords;

    String path = Environment.getExternalStorageState();
    String pathFile = "/Data/EnVi.en-US/index.txt";

    public static List<String> LoadFavoriteWords(){
        return _dateHelper.loadFavoriteWords();
    }

    private List<String> loadFavoriteWords(){
        _lstFavoriteWords = new ArrayList<String>();

        File file = new File(path + pathFile);

        if(!file.exists()){

            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream iStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(iStream);
            BufferedReader bufReader = new BufferedReader(reader);

            String line = "";
            while((line = bufReader.readLine()) != null){
                _lstFavoriteWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _lstFavoriteWords;
    }

    public static void SaveFavoriteWords(List<String> lstFavoriteWords){
        String strWords = "";
        for(String str : lstFavoriteWords){
            strWords += strWords + str + "\n";
        }
        _dateHelper.saveFavoriteWords(strWords);
    }

    private void saveFavoriteWords(String strWords){
        try{
            File file = new File(path + pathFile);
            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter sWriter = new OutputStreamWriter(stream);

            sWriter.append(strWords);
            sWriter.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
