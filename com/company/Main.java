package com.company;

import cmu.arktweetnlp.Tagger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static Preprocesamiento preprocesamiento = new Preprocesamiento();

    public static void main(String[] args) {

        /*
        Codigo de ejemplo:


        JsonObject jsonObject = null;
        try {
                jsonObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(new
                    FileInputStream(path)))).getAsJsonObject();
                System.out.println(jsonObject.get("text").getAsString());
                System.out.println(jsonObject.get("favorite_count").getAsInt());

                //Una entidad que no tiene nada
                System.out.println(jsonObject.get("hashtags"));

                //Una entidad nested
                System.out.println(jsonObject.get("user").getAsJsonObject().get("friends_count").getAsInt());
                System.out.println(jsonObject.get("user").getAsJsonObject().get("location").getAsString());
                System.out.println(jsonObject.get("user").getAsJsonObject().get("description").getAsString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
        tokenizar();

    }

    public static void tokenizar(){
        String tipoRumor = "rumours";
        ArrayList<File> files = obtenerArchivos(tipoRumor);
        File file;
        String text;
        JsonObject jsonObject = null;

        for (File f : files) {
            file = new File("C:\\Users\\Joaking\\workspace\\MineriaDatos\\charliehebdo\\" + tipoRumor + "\\" + f.getName() + "\\" + "source-tweet\\" + f.getName() + ".json");

            try {
                jsonObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(new
                        FileInputStream(file.getPath())))).getAsJsonObject();

                //Obtengo el texto del tweet y le paso el PosTag
                String tweetText = jsonObject.get("text").getAsString();
                tweetText = preprocesamiento.procesar(tweetText);
                List<Tagger.TaggedToken> listTaggedToken = getPosTag(tweetText);
                for (Tagger.TaggedToken tt : listTaggedToken){
                    System.out.print(" [" +tt.tag +"] " + tt.token);
                }

                System.out.println(" ");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Tagger.TaggedToken> getPosTag(String text){
        Tagger tagger = new Tagger();
        try {
            tagger.loadModel("/cmu/arktweetnlp/model.20120919");
            return tagger.tokenizeAndTag(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //tipoRumor = rumours | non-rumours
    public static ArrayList<File> obtenerArchivos(String tipoRumor){
        //Obtengo todos los archivos que son rumores
        File file = new File("C:\\Users\\Joaking\\workspace\\MineriaDatos\\charliehebdo\\" + tipoRumor);
        return new ArrayList<File>(Arrays.asList(file.listFiles()));
    }

    public int obtenerPromedio(String tipoRumor) {

        //Obtengo todos los archivos que son rumores
        File file;
        ArrayList<File> files = obtenerArchivos(tipoRumor);
        JsonObject jsonObject = null;
        int promedio = 0;
        int cant = 0;

        //Por cada archivo ingreso al tweet
        for (File f : files) {
            file = new File("C:\\Users\\Joaking\\workspace\\MineriaDatos\\charliehebdo\\" + tipoRumor + "\\" + f.getName() + "\\" + "source-tweet\\" + f.getName() + ".json");

            try {
                jsonObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(new
                        FileInputStream(file.getPath())))).getAsJsonObject();

                //Almaceno la cantidad de amigos
                cant++;
                promedio += jsonObject.get("user").getAsJsonObject().get("friends_count").getAsInt();
                System.out.println(jsonObject.get("user").getAsJsonObject().get("friends_count").getAsInt());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //Saco el promedio de amigos que tienen los usuarios que dieron rumores
        promedio = promedio / cant;
        return promedio;
    }

}