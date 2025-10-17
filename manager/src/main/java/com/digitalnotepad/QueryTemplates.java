package com.digitalnotepad;

import com.pair.Pair;

public class QueryTemplates {
    static Pair<String, String> IDEAS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, implemented, description ) VALUES (?, ?, ?, ?);");
    static Pair<String, String> TASKS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, finished, steps ) VALUES (?, ?, ?, ?);");
}
