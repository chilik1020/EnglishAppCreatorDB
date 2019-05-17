package com.chilik1020.creatordb;

import com.chilik1020.creatordb.models.Chapter;
import com.chilik1020.creatordb.models.Lesson;
import com.chilik1020.creatordb.models.Question;
import com.chilik1020.creatordb.models.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONDirParser {
    private final static String FILEPATH = "D:/loboda/androidprojects/creatorDB/jsons/template.json";

    private static List<Chapter> chapters = new ArrayList<>();
    private static List<Lesson> lessons = new ArrayList<>();
    private static List<Test> tests = new ArrayList<>();
    private static List<Question> questions = new ArrayList<>();

    public static void jsonParseDir() {

    }

    private static void jsonParseFile() {
        FileReader reader = null;
        try {
            reader = new FileReader(FILEPATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();


        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String chapter = (String) jsonObject.get("chapter");
        JSONArray lessons = (JSONArray) jsonObject.get("lessons");
        JSONArray tests = (JSONArray) jsonObject.get("tests");
        JSONArray questions = (JSONArray) jsonObject.get("questions");

        System.out.println(chapter);
        System.out.println(lessons);
        System.out.println(tests);
        System.out.println(questions);
    }

}
