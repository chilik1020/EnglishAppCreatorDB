package com.chilik1020.creatordb;

import com.chilik1020.creatordb.models.Purchase;

public class CreatorDB {

    public static String fileLocation = "D:/loboda/";

    public static void main(String[] args) {
        DBHelper.createDB();
        DBHelper.createTables();
        DBHelper.insertIntoPurchases(new Purchase(0L, "eng_a2_b2_all_tests", 0));

        JSONDirParser.jsonParseDir();
    }
}
