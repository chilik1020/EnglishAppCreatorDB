package com.chilik1020.creatordb;

import com.chilik1020.creatordb.models.*;

import java.sql.*;

public class DBHelper {


    private static final String dbGeneralname = "eng_grammar_tests_general_data_v1_0.db";
    private static final String urlGeneral = "jdbc:sqlite:" + CreatorDB.fileLocation + "creatorDB/db/" + dbGeneralname;

    private static final String dbPersonalname = "eng_grammar_tests_personal_data_v1_0.db";
    private static final String urlPersonal = "jdbc:sqlite:" + CreatorDB.fileLocation + "creatorDB/db/" + dbPersonalname;

    private static final String TABLE_PURCHASES = "purchases";
    private static final String TABLE_USER_STATS = "userStats";
    private static final String TABLE_CHAPTERS = "chapters";
    private static final String TABLE_LESSONS_GRAMMAR = "lessonsGrammar";
    private static final String TABLE_LESSONS_TEST = "lessonsTest";
    private static final String TABLE_TESTS = "tests";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_SCORES = "scores";

    private static Connection connect(String url) {
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
        try (Connection conn = connect(urlGeneral)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Connection conn = connect(urlPersonal)) {
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
        String sqlUserStats= "CREATE TABLE IF NOT EXISTS " + TABLE_USER_STATS +"(\n"
                + " _id INTEGER NOT NULL  PRIMARY KEY,\n"
                + "	numberOfAppStarts INTEGER NOT NULL,\n"
                + " statusRate INTEGER NOT NULL \n"
                + ");";

        String sqlPurchases = "CREATE TABLE IF NOT EXISTS " + TABLE_PURCHASES +"(\n"
                + " _id INTEGER NOT NULL,\n"
                + "	name TEXT NOT NULL  PRIMARY KEY,\n"
                + " status INTEGER NOT NULL \n"
                + ");";

        String sqlChapters = "CREATE TABLE IF NOT EXISTS " + TABLE_CHAPTERS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY,\n"
                + "	chapter TEXT NOT NULL\n"
                + ");";

        String sqlLessonsGrammar = "CREATE TABLE IF NOT EXISTS " + TABLE_LESSONS_GRAMMAR +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY,\n"
                + " chapterId INTEGER NOT NULL,\n"
                + "	topic TEXT NOT NULL,\n"
                + "	grammar TEXT NOT NULL\n"
                + ");";

        String sqlLessonsTest = "CREATE TABLE IF NOT EXISTS " + TABLE_LESSONS_TEST +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY,\n"
                + " chapterId INTEGER NOT NULL,\n"
                + "	topic TEXT NOT NULL\n"
                + ");";

        String sqlTests = "CREATE TABLE IF NOT EXISTS " + TABLE_TESTS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY,\n"
                + " chapterId INTEGER NOT NULL,\n"
                + " lessonId INTEGER NOT NULL,\n"
                + "	topic TEXT NOT NULL,\n"
                + " price INTEGER NOT NULL\n"
                + ");";

        String sqlQuestions = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY,\n"
//                + " chapterId INTEGER NOT NULL,\n"
                + " lessonId INTEGER NOT NULL,\n"
                + " testId INTEGER NOT NULL,\n"
                + "	question TEXT,\n"
                + "	answer0 TEXT,\n"
                + "	answer1 TEXT,\n"
                + "	answer2 TEXT,\n"
                + "	answer3 TEXT,\n"
                + " rightAnswer INTEGER NOT NULL\n"
                + ");";

        String sqlScores = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + " typeTest INTEGER NOT NULL,\n"
                + " typeResult INTEGER NOT NULL,\n"
                + " chapterId INTEGER NOT NULL,\n"
                + " lessonId INTEGER NOT NULL,\n"
                + " testId INTEGER NOT NULL,\n"
                + " result INTEGER NOT NULL\n"
                + ");";

        createTable(urlPersonal, sqlUserStats);
        createTable(urlPersonal, sqlPurchases);
        createTable(urlPersonal, sqlScores);

        createTable(urlGeneral, sqlChapters);
        createTable(urlGeneral, sqlLessonsGrammar);
        createTable(urlGeneral, sqlLessonsTest);
        createTable(urlGeneral, sqlTests);
        createTable(urlGeneral, sqlQuestions);

    }

    private static void createTable(String urlDB, String sql) {
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int insertIntoUserStats(UserStat us) {
        String sql = "INSERT INTO " + TABLE_USER_STATS + " (_id, numberOfAppStarts, statusRate) VALUES(?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlPersonal);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, us.get_id());
            pstmt.setLong(2, us.getNumberOfAppStarts());
            pstmt.setLong(3, us.getStatusRate());
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
            System.out.println("insertIntoUserStats: " + e.getMessage());
        }
        return id;
    }

    public static int insertIntoPurchases(Purchase p) {
        String sql = "INSERT INTO " + TABLE_PURCHASES + " (_id, name, status) VALUES(?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlPersonal);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, p.getId());
            pstmt.setString(2, p.getName());
            pstmt.setLong(3, p.getStatus());
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
            System.out.println("insertIntoPurchases: " + e.getMessage());
        }
        return id;
    }


    public static int insertIntoChapters(Chapter c) {
        String sql = "INSERT INTO " + TABLE_CHAPTERS + " (_id, chapter) VALUES(?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlGeneral);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, c.get_id());
            pstmt.setString(2, c.getChapter());
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

    public static int insertIntoLessonsGrammar(LessonGrammar l) {
        String sql = "INSERT INTO " + TABLE_LESSONS_GRAMMAR + "(_id, chapterId,topic,grammar) VALUES(?,?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlGeneral);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, l.getId());
            pstmt.setLong(2, l.getChapterId());
            pstmt.setString(3, l.getTopic());
            pstmt.setString(4, l.getGrammar());
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
            System.out.println("insertIntoLessonsGrammar: " + e.getMessage());
        }
        return id;
    }

    public static int insertIntoLessonsTest(LessonTest l) {
        String sql = "INSERT INTO " + TABLE_LESSONS_TEST + "(_id, chapterId,topic) VALUES(?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlGeneral);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, l.getId());
            pstmt.setLong(2, l.getChapterId());
            pstmt.setString(3, l.getTopic());
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
            System.out.println("insertIntoLessonsGrammar: " + e.getMessage());
        }
        return id;
    }



    public static int insertIntoTests(Test t) {
        String sql = "INSERT INTO " + TABLE_TESTS + "(_id,chapterId,lessonId,topic,price) VALUES(?,?,?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlGeneral);
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setLong(1, t.getId());
            pstmt.setLong(2, t.getChapterId());
            pstmt.setLong(3, t.getLessonId());
            pstmt.setString(4, t.getTopic());
            pstmt.setLong(5,t.getPrice());
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

    public static int insertIntoQuestions(Question q) {
        String sql = "INSERT INTO " + TABLE_QUESTIONS + " (lessonId,testId,question, answer0,answer1,answer2,answer3,rightAnswer) VALUES(?,?,?,?,?,?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = connect(urlGeneral);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, q.getLessonId());
            pstmt.setLong(2, q.getTestId());
            pstmt.setString(3, q.getQuestion());
            pstmt.setString(4, q.getAnswer0());
            pstmt.setString(5, q.getAnswer1());
            pstmt.setString(6, q.getAnswer2());
            pstmt.setString(7, q.getAnswer3());
            pstmt.setLong(8, q.getRightAnswer());
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
