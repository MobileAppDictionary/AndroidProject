package dict;

import android.util.Log;
import android.widget.Toast;

import com.example.phungminhhoang.mydict.MainActivity;

import java.util.ArrayList;
import java.util.List;

import datahelper.DataHelper;
import datahelper.DataHelperFavoriteWords;
import datahelper.DataHelperTranslatedWords;
import model.ListWords;

/**
 * Created by Huy on 6/26/2015.
 */
public class Dict {
    private ListWords _lstWords;
    public List<String> LstFavoriteWords;
    public List<String> LstTranslatedWords;

    public ListWords get_lstWords() {
        return _lstWords;
    }

    public Dict(){
        LoadData();
    }

    public void LoadData(){
        // Nếu chưa được tạo thì tạo
        if(get_lstWords() == null)
            _lstWords = new ListWords();

        // Nếu đã load rồi thì thôi
        if(get_lstWords().LstKey.size() != 0)
            return ;

        _lstWords = DataHelper.LoadData();
    }

    public String Search(String key){
        String res = null;
        key = key.toLowerCase();
        int ind = get_lstWords().LstKey.indexOf(key);

        if(ind != -1){
            int offset = GetDemicalValue(get_lstWords().LstInd.get(ind));
            int length = GetDemicalValue(get_lstWords().LstLen.get(ind));
            res = DataHelper.GetMeaning(offset, length);

        }

        return res;
    }

    private int GetDemicalValue(String str)
    {
        String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        int decValue = 0;
        int len = str.length();
        for (int i = 0; i < len; i++)
        {
            int pos = base64.indexOf(str.charAt(i));
            decValue += (int)Math.pow(64, len - i - 1) * pos;
        }
        return decValue;
    }

    public List<String> GetSuggestion(String key){
        key = key.toLowerCase();
        List<String> lstString = new ArrayList<String>();
        int len = key.length();

        if (len < 3)
            return lstString;

        for (String word : get_lstWords().LstKey)
        {
            if (word.length() >= len && word.substring(0,len).equals(key))
            {
                int ind = get_lstWords().LstKey.indexOf(word);
                for (int i = 0; i < 4; i++)
                {
                    lstString.add(get_lstWords().LstKey.get(ind + i));
                }
            }
        }

        return lstString;
    }


    public void LoadFavoriteWords(){
        if(LstFavoriteWords == null)
            LstFavoriteWords = new ArrayList<>();

        _lstWords = DataHelper.LoadData();
    }
    public void AddFavoriteWords(String word){
        addFavoriteWords(word);
    }
    private void addFavoriteWords(String word){
        word = word.toLowerCase();
        if(LstFavoriteWords == null)
            LstFavoriteWords = new ArrayList<>();

        if (LstFavoriteWords.size() == 0)
        {
            LstFavoriteWords = DataHelperFavoriteWords.LoadFavoriteWords();
        }

        int ind = LstFavoriteWords.indexOf(word);
        // Word not found
        if (ind == -1)
        {
            LstFavoriteWords.add(0, word);
        }
        // Have been word
        else
        {
            LstFavoriteWords.remove(ind);
            LstFavoriteWords.add(0, word);
        }

        DataHelperFavoriteWords.SaveFavoriteWords (LstFavoriteWords);
    }


    public void LoadTranslatedWords(){
        if(LstTranslatedWords == null)
            LstTranslatedWords = new ArrayList<>();

        _lstWords = DataHelper.LoadData();
    }
    public void AddTranslatedWords(String word){
        addTranslatedWords(word);
    }
    private void addTranslatedWords(String word){
        word = word.toLowerCase();
        if(LstTranslatedWords == null)
            LstTranslatedWords = new ArrayList<>();

        if (LstTranslatedWords.size() == 0)
        {
            LstTranslatedWords = DataHelperTranslatedWords.LoadTranslatedWords();
        }

        int ind = LstTranslatedWords.indexOf(word);
        // Word not found
        if (ind == -1)
        {
            LstTranslatedWords.add(0, word);
        }
        // Have been word
        else
        {
            LstTranslatedWords.remove(ind);
            LstTranslatedWords.add(0, word);
        }

        DataHelperTranslatedWords.SaveTranslatedWords (LstTranslatedWords);
    }
}
