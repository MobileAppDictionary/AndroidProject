package datahelper;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 6/26/2015.
 */
public class DataHelperTranslatedWords {
    private static DataHelperTranslatedWords _dateHelper = new DataHelperTranslatedWords();
    private List<String> _lstTranslatedWords;

    String path = Environment.getExternalStorageState();
    String pathFile = "/Data/EnVi.en-US/";
    String fileName = "translated.txt";

    public static List<String> LoadTranslatedWords(){
        return _dateHelper.loadTranslatedWords();
    }

    private List<String> loadTranslatedWords(){
        _lstTranslatedWords = new ArrayList<String>();

        File file = new File(path + pathFile + fileName);
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
                _lstTranslatedWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _lstTranslatedWords;
    }

    public static void SaveTranslatedWords(List<String> lstTranslatedWords){
        String strWords = "";
        for(String str : lstTranslatedWords){
            strWords += strWords + str + "\n";
        }
        _dateHelper.saveTranslatedWords(strWords);
    }

    private void saveTranslatedWords(String strWords){
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
