package com.chilik1020.creatordb;

import java.sql.*;

public class DBHelper {

    private static final String dbname = "engtestdb.db";
    private static final String url = "jdbc:sqlite:D:/loboda/androidprojects/creatorDB/db/" + dbname;

    private static final String TABLE_CHAPTERS = "chapters";
    private static final String TABLE_LESSONS = "lessons";
    private static final String TABLE_TESTS = "tests";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_SCORES = "scores";

    private static Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createTables() {
        String sqlChapters = "CREATE TABLE IF NOT EXISTS " + TABLE_CHAPTERS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "	chapter TEXT NOT NULL\n"
                + ");";

        String sqlLessons = "CREATE TABLE IF NOT EXISTS " + TABLE_LESSONS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + " chapter_id INTEGER NOT NULL,\n"
                + "	topic TEXT NOT NULL,\n"
                + "	grammar TEXT NOT NULL\n"
                + ");";

        String sqlTests = "CREATE TABLE IF NOT EXISTS " + TABLE_TESTS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + " chapter_id INTEGER NOT NULL,\n"
                + " topic_id INTEGER NOT NULL,\n"
                + "	topic TEXT NOT NULL\n"
                + ");";

        String sqlQuestions = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + " chapter_id INTEGER NOT NULL,\n"
                + " topic_id INTEGER NOT NULL,\n"
                + " test_id INTEGER NOT NULL,\n"
                + "	question TEXT,\n"
                + "	answer0 TEXT,\n"
                + "	answer1 TEXT,\n"
                + "	answer2 TEXT,\n"
                + "	answer3 TEXT,\n"
                + " right_answer INTEGER NOT NULL\n"
                + ");";

        String sqlScores = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + " type_test INTEGER NOT NULL,\n"
                + " type_result INTEGER NOT NULL,\n"
                + " chapter_id INTEGER NOT NULL,\n"
                + " lesson_id INTEGER NOT NULL,\n"
                + " result INTEGER NOT NULL\n"
                + ");";


        createTable(sqlChapters);
        createTable(sqlLessons);
        createTable(sqlTests);
        createTable(sqlQuestions);
        createTable(sqlScores);
    }

    private static void createTable(String sql) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void fillDB() {
        String a0 = "Answer 1";
        String a1 = "Answer 2";
        String a2 = "Answer 3";
        String a3 = "Answer 4";
        int ra = 2;
        for (int m = 1; m < 11; m++) { ///////////////////////////////////////// COUNT OF CHAPTERS
            String chapter = "Chapter " + m;
            int chapter_id = insertIntoChapters(chapter);
            System.out.println("Chapter " + chapter_id);
            for (int i = 1; i < 15; i++) { ///////////////////////////////////// COUNT OF TOPICS
                String topic = chapter +  " : Topic " + i;
//            String grammar = "Grammar text of topic " + i;
                StringBuilder grammar = new StringBuilder("ch_");
                grammar.append(chapter_id)
                        .append("_les_")
                        .append(i)
                        .append(".html");

                int lesson_id = insertIntoLessons(chapter_id,topic, grammar.toString());
                for (int d = 1; d < 4; d++) { ////////////////////////////////// COUNT OF TESTS
                    int test_id = insertIntoTests(chapter_id, lesson_id, "Test: " + topic);
                    for (int j = 1; j < 11; j++) { ///////////////////////////// COUNT OF QUESTIONS
                        String q = topic + " : Test " + test_id +  " : Text of question " + j;
                        int question_id = insertIntoQuestions(chapter_id, lesson_id,test_id, q, a0, a1, a2, a3, ra);
                    }
                }
            }
        }
    }
    private static int insertIntoChapters(String chapter) {
        String sql = "INSERT INTO " + TABLE_CHAPTERS + " (chapter) VALUES(?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setString(1, chapter);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("insertIntoChapters: " + e.getMessage());
        }
        return id;
    }

    private static int insertIntoLessons(int chapter_id, String topic, String grammar) {
        String sql = "INSERT INTO " + TABLE_LESSONS + "(chapter_id,topic,grammar) VALUES(?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setInt(1, chapter_id);
            pstmt.setString(2, topic);
            pstmt.setString(3, grammar);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("insertIntoLessons: " + e.getMessage());
        }
        return id;
    }

    private static int insertIntoTests(int chapter_id, int topic_id, String topic) {
        String sql = "INSERT INTO " + TABLE_TESTS + "(chapter_id,topic_id,topic) VALUES(?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setInt(1, chapter_id);
            pstmt.setInt(2, topic_id);
            pstmt.setString(3, topic);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("insertIntoTests: " + e.getMessage());
        }
        return id;
    }

    private static int insertIntoQuestions(int chapter_id, int topic_id, int test_id,  String q, String a0, String a1, String a2, String a3, int ra) {
        String sql = "INSERT INTO " + TABLE_QUESTIONS + " (chapter_id,topic_id,test_id,question, answer0,answer1,answer2,answer3,right_answer) VALUES(?,?,?,?,?,?,?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chapter_id);
            pstmt.setInt(2, topic_id);
            pstmt.setInt(3, test_id);
            pstmt.setString(4, q);
            pstmt.setString(5, a0);
            pstmt.setString(6, a1);
            pstmt.setString(7, a2);
            pstmt.setString(8, a3);
            pstmt.setInt(9, ra);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("insertIntoQuestions: " + e.getMessage());
        }
        return id;
    }
}
