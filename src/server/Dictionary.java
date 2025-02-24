package server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import util.Constants;

public class Dictionary {

    public static String queryWord(String dictionaryFile, String word) {
        JSONObject dictionary = loadDictionary(dictionaryFile);
        word = word.toLowerCase().trim();
        if (dictionary != null && dictionary.has(word)) {
            JSONArray meanings = dictionary.getJSONArray(word);
            StringBuilder response = new StringBuilder("Meaning(s): ");
            for (int i = 0; i < meanings.length(); i++) {
                response.append("(").append(i + 1).append(") ").append(meanings.getString(i)).append(" ");
            }
            return response.toString() + "\n";
        }
        return Constants.WORD_NOT_FOUND;
    }

    public static String addWord(String dictionaryFile, String wordAndMeaning) {
        JSONObject dictionary = loadDictionary(dictionaryFile);
        if (dictionary == null) {
            dictionary = new JSONObject();
        }

        String[] wordMeaning = wordAndMeaning.split(":", 2);
        String word = wordMeaning[0].toLowerCase().trim();
        String[] meanings = wordMeaning[1].trim().split("\\|");

        if (dictionary.has(word)) {
            return Constants.WORD_ALREADY_EXISTS;
        } else {
            dictionary.put(word, new JSONArray(meanings));
            if (saveDictionary(dictionary, dictionaryFile)) {
                return Constants.WORD_ADDED_SUCCESS;
            } else {
                return Constants.WORD_ADD_FAILURE;
            }
        }
    }

    public static String removeWord(String dictionaryFile, String word) {
        JSONObject dictionary = loadDictionary(dictionaryFile);
        word = word.toLowerCase().trim();

        if (dictionary != null && dictionary.has(word)) {
            dictionary.remove(word);
            if (saveDictionary(dictionary, dictionaryFile)) {
                return Constants.WORD_REMOVED_SUCCESS;
            }
            return Constants.WORD_REMOVE_FAILURE;
        }
        return Constants.WORD_NOT_FOUND_REMOVE;
    }

    public static String updateWord(String dictionaryFile, String wordAndMeaning) {
        JSONObject dictionary = loadDictionary(dictionaryFile);

        String[] wordMeaning = wordAndMeaning.split(":", 2);
        String word = wordMeaning[0].toLowerCase().trim();
        String[] newMeanings = wordMeaning[1].trim().split("\\|");

        if (dictionary != null && dictionary.has(word)) {
            JSONArray meanings = dictionary.getJSONArray(word);
            for (String newMeaning : newMeanings) {
                if (!meanings.toList().contains(newMeaning)) {
                    meanings.put(newMeaning);
                }
            }
            dictionary.put(word, meanings);
            if (saveDictionary(dictionary, dictionaryFile)) {
                return Constants.WORD_UPDATE_SUCCESS;
            } else {
                return Constants.WORD_UPDATE_FAILURE;
            }
        }
        return Constants.WORD_NOT_FOUND_UPDATE;
    }

    private static JSONObject loadDictionary(String dictionaryFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return new JSONObject(content.toString());
        } catch (IOException e) {
            System.out.println(Constants.DICTIONARY_ERROR_LOADING + e.getMessage());
            return null;
        }
    }

    private static boolean saveDictionary(JSONObject dictionary, String dictionaryFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile))) {
            writer.write(dictionary.toString(Constants.JSON_INDENT_FACTOR));
            return true;
        } catch (IOException e) {
            System.out.println(Constants.DICTIONARY_ERROR_SAVING + e.getMessage());
            return false;
        }
    }
}
