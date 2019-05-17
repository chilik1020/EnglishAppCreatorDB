package com.chilik1020.creatordb;

public class CreatorDB {

    public static void main(String[] args) {
        DBHelper.createDB();
        DBHelper.createTables();
//        DBHelper.fillDB();

        JSONDirParser.jsonParseDir();
    }
}
