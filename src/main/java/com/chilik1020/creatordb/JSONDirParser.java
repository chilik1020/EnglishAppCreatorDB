package com.chilik1020.creatordb;

import com.chilik1020.creatordb.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JSONDirParser {
    private final static String DIRPATH = CreatorDB.fileLocation + "creatorDB/jsons/done";

    private static List<Chapter> chaptersAll = new ArrayList<>();
    private static List<LessonGrammar> lessonsGrammarAll = new ArrayList<>();
    private static List<LessonTest> lessonsTestAll = new ArrayList<>();
    private static List<Test> testsAll = new ArrayList<>();
    private static List<Question> questionsAll = new ArrayList<>();

    public static void jsonParseDir() {
        List<String> jsonsList = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(Paths.get(DIRPATH))) {

            jsonsList = walk
                    .filter(Files::isRegularFile)
                    .map(x -> x.toString())
                    .filter(f -> f.endsWith(".json"))
                    .filter(s-> !s.endsWith("generated.json"))
                    .collect(Collectors.toList());

            jsonsList.forEach(System.out::println);
            System.out.println("---------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonsList.forEach(JSONDirParser::jsonParseFile);

        chaptersAll.forEach(c -> System.out.println(c.toString()));
        System.out.println("---------------------------------");
        lessonsGrammarAll.forEach(l -> System.out.println(l.toString()));
        System.out.println("---------------------------------");
        lessonsTestAll.forEach(l -> System.out.println(l.toString()));
        System.out.println("---------------------------------");
        testsAll.forEach(t -> System.out.println(t.toString()));
        System.out.println("---------------------------------");
        questionsAll.forEach(q -> System.out.println(q.toString()));

        System.out.print("Inserting chapters...");
        chaptersAll.forEach(c -> DBHelper.insertIntoChapters(c));
        System.out.println("done!");

        System.out.print("Inserting lessons grammar...");
        lessonsGrammarAll.forEach(l -> DBHelper.insertIntoLessonsGrammar(l));
        System.out.println("done!");

        System.out.print("Inserting lessons test...");
        lessonsTestAll.forEach(l -> DBHelper.insertIntoLessonsTest(l));
        System.out.println("done!");

        System.out.print("Inserting tests...");
        testsAll.forEach(t -> DBHelper.insertIntoTests(t));
        System.out.println("done!");

        System.out.print("Inserting questions...");
        questionsAll.forEach(q -> DBHelper.insertIntoQuestions(q));
        System.out.println("done!");

    }

    public static void jsonParseFile(String path) {
        System.out.println(path);
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONParser jsonParser = new JSONParser();


        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long chapterId = (Long) jsonObject.get("chapterId");
        String chapter = (String) jsonObject.get("chapter");
        JSONArray lessonsGrammar = (JSONArray) jsonObject.get("lessonsGrammar");
        JSONArray lessonsTest = (JSONArray) jsonObject.get("lessonsTest");
        JSONArray tests = (JSONArray) jsonObject.get("tests");
        JSONArray questions = (JSONArray) jsonObject.get("questions");

        chaptersAll.add(new Chapter(chapterId,chapter));

        lessonsGrammar.forEach(l -> lessonsGrammarAll.add(new LessonGrammar(
                (Long)((JSONObject)l).get("lessonGrammarId"),
                chapterId,
                (String)((JSONObject)l).get("topic"),
                (String)((JSONObject)l).get("path"))));

        lessonsTest.forEach(l -> lessonsTestAll.add(new LessonTest(
                (Long)((JSONObject)l).get("lessonTestId"),
                chapterId,
                (String)((JSONObject)l).get("topic"))));

        tests.forEach(t -> testsAll.add(new Test(
                (Long)((JSONObject)t).get("testId"),
                chapterId,
                (Long)((JSONObject)t).get("lessonTestId"),
                (String)((JSONObject)t).get("topic"),
                (Long)((JSONObject)t).get("price")
                )));

        questions.forEach(q -> questionsAll.add(new Question(
                (Long)((JSONObject)q).get("lessonTestId"),
                (Long)((JSONObject)q).get("testId"),
                (String)((JSONObject)q).get("question"),
                (String)((JSONObject)q).get("answer0"),
                (String)((JSONObject)q).get("answer1"),
                (String)((JSONObject)q).get("answer2"),
                (String)((JSONObject)q).get("answer3"),
                (Long)((JSONObject)q).get("rightAnswer")
        )));
    }

}
