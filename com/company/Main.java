package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

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

        //Obtengo todos los archivos que son rumores
        File file = new File("C:\\Users\\Joaking\\workspace\\MineriaDatos\\charliehebdo\\non-rumours");
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));

        JsonObject jsonObject = null;
        int promedio = 0;
        int cant = 0;
        //Por cada archivo ingreso al tweet
        for (File f : files){
            file = new File("C:\\Users\\Joaking\\workspace\\MineriaDatos\\charliehebdo\\non-rumours\\" + f.getName() + "\\" + "source-tweet\\" + f.getName() + ".json");

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
        System.out.println(promedio);
    }
}
