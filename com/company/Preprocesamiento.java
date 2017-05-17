package com.company;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Joaking on 5/16/2017.
 */

public class Preprocesamiento {

    private HashMap<String, Boolean> stopWords = new HashMap<String, Boolean>();
    private Stemmer stemmer = new Stemmer();
    public Preprocesamiento(){
        cargarStopWords();
    };

    public void cargarStopWords(){
        FileReader in = null;
        try {
            in = new FileReader("stopwords");
            BufferedReader br = new BufferedReader(in);
            String stopword;
            while ((stopword = br.readLine()) != null) {
                stopWords.put(stopword, true);
                System.out.println(stopword);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String stemming(String word){
        stemmer.add(word.toCharArray(),word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    public boolean isStepWord(String word){
        if (stopWords.containsKey(word))
            return true;
        else
            return false;
    }

    public String procesar(String text){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder resultado = new StringBuilder();

        for (Character c : text.toCharArray()) {
            //Si encuentra un espacio, es una palabra nueva.
            if (c.equals(' ')) {
                if (!isStepWord(stringBuilder.toString())) {
                    resultado.append(stemming(stringBuilder.toString()));
                    resultado.append(" ");
                }
                stringBuilder.delete(0, stringBuilder.length());
            }else
                stringBuilder.append(c);
        }
        return resultado.toString();
    }

}
