package com.chilik1020.creatordb;

import java.sql.*;

public class CreatorDB {

    private final String dbname = "engtestdb.db";
    private final String url = "jdbc:sqlite:/home/chilik1020/IdeaProjects/creatorDB/db/" + dbname;

    private final String TABLE_CHAPTERS = "chapters";
    private final String TABLE_LESSONS = "lessons";
    private final String TABLE_TESTS = "tests";
    private final String TABLE_LESSONS_TESTS = "lessons_tests";
    private final String TABLE_CHAPTERS_LESSONS = "chapters_lessons";

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = this.connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void createTables() {
        String sqlChapters = "CREATE TABLE IF NOT EXISTS " + TABLE_CHAPTERS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "	chapter TEXT NOT NULL\n"
                + ");";

        String sqlLessons = "CREATE TABLE IF NOT EXISTS " + TABLE_LESSONS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "	topic TEXT NOT NULL,\n"
                + "	grammar TEXT NOT NULL\n"
                + ");";

        String sqlTests = "CREATE TABLE IF NOT EXISTS " + TABLE_TESTS +"(\n"
                + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "	question TEXT,\n"
                + "	answer0 TEXT,\n"
                + "	answer1 TEXT,\n"
                + "	answer2 TEXT,\n"
                + "	answer3 TEXT,\n"
                + " right_answer INTEGER NOT NULL\n"
                + ");";

        String sqlLessonsTests = "CREATE TABLE IF NOT EXISTS " + TABLE_LESSONS_TESTS +"(\n"
                + "	lesson_id INTEGER NOT NULL,\n"
                + "	test_id INTEGER NOT NULL,\n"
                + " FOREIGN KEY (lesson_id) REFERENCES " + TABLE_LESSONS + "(_id),\n"
                + " FOREIGN KEY (test_id) REFERENCES " + TABLE_TESTS + "(_id)\n"
                + ");";
        String sqlChapterLessons = "CREATE TABLE IF NOT EXISTS " + TABLE_CHAPTERS_LESSONS +"(\n"
                + "	chapter_id INTEGER NOT NULL,\n"
                + "	lesson_id INTEGER NOT NULL,\n"
                + " FOREIGN KEY (lesson_id) REFERENCES " + TABLE_LESSONS + "(_id),\n"
                + " FOREIGN KEY (chapter_id) REFERENCES " + TABLE_CHAPTERS + "(_id)\n"
                + ");";

        createTable(sqlChapters);
        createTable(sqlLessons);
        createTable(sqlTests);
        createTable(sqlLessonsTests);
        createTable(sqlChapterLessons);
    }

    private void createTable(String sql) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void fillDB() {
        String a0 = "Answer 1";
        String a1 = "Answer 2";
        String a2 = "Answer 3";
        String a3 = "Answer 4";
        int ra = 2;
        for (int m = 1; m < 11; m++) {
            String chapter = "Chapter " + m;
            int chapter_id = insertIntoChapters(chapter);
            System.out.println("Chapter " + chapter_id);
            for (int i = 1; i < 15; i++) {
                String topic = "Topic " + i;
//            String grammar = "Grammar text of topic " + i;
                StringBuilder grammar = new StringBuilder("Grammar text of topic " + i);
                for (int k = 0; k < i + 10; k++)
                    grammar.append(", some text");

                int lesson_id = insertIntoLessons(topic, grammar.toString());

                insertIntoChaptersLessons(chapter_id, lesson_id);

                for (int j = 1; j < 11; j++) {
                    String q = "Text of question " + j + ", topic " + i;
                    int test_id = insertIntoTests(q, a0, a1, a2, a3, ra);

                    insertIntoLessonsTests(lesson_id, test_id);
                }
            }
        }
    }
    private int insertIntoChapters(String chapter) {
        String sql = "INSERT INTO chapters(chapter) VALUES(?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = this.connect();
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

    private int insertIntoLessons(String topic, String grammar) {
        String sql = "INSERT INTO lessons(topic,grammar) VALUES(?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, returnId)) {
            pstmt.setString(1, topic);
            pstmt.setString(2, grammar);
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

    private int insertIntoTests(String q, String a0,String a1,String a2,String a3, int ra) {
        String sql = "INSERT INTO tests(question, answer0,answer1,answer2,answer3,right_answer) VALUES(?,?,?,?,?,?)";
        String[] returnId = { "BATCHID" };
        int id = -1;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, q);
            pstmt.setString(2, a0);
            pstmt.setString(3, a1);
            pstmt.setString(4, a2);
            pstmt.setString(5, a3);
            pstmt.setInt(6, ra);
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

    private void insertIntoLessonsTests(int lessonId, int testId) {
        String sql = "INSERT INTO lessons_tests(lesson_id, test_id) VALUES(?,?);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lessonId);
            pstmt.setInt(2, testId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertIntoLessonsTests: " + e.getMessage());
        }
    }

    private void insertIntoChaptersLessons(int chapterId, int lessonId) {
        String sql = "INSERT INTO chapters_lessons(chapter_id, lesson_id) VALUES(?,?);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, chapterId);
            pstmt.setInt(2, lessonId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertIntoChaptersLessons: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        CreatorDB creatorDB = new CreatorDB();

        creatorDB.createDB();
        creatorDB.createTables();
        creatorDB.fillDB();
    }
}
