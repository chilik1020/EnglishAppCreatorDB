package com.chilik1020.creatordb;

import com.chilik1020.creatordb.models.Purchase;
import com.chilik1020.creatordb.models.UserStat;

public class CreatorDB {

    public static String fileLocation = "E:/loboda/";

    public static void main(String[] args) {
        DBHelper.createDB();
        DBHelper.createTables();
        DBHelper.insertIntoPurchases(new Purchase(0L, "eng_a2_b2_all_tests", 0));
        DBHelper.insertIntoUserStats(new UserStat(0L, 0, 0));

        JSONDirParser.jsonParseDir();
    }
}
