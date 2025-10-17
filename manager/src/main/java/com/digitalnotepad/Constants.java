package com.digitalnotepad;

public class Constants {
    public static String APP_NAME = "Digital Notepad";
    public static String APP_VERSION = "1.0-SNAPSHOT";
    public static String POPULATE_TASK_TEXT = "Task";
    public static String POPULATE_IDEA_TEXT = "Idea";
    public static String SUBMIT = "Submit";
    public static String EMPTY_STRING = "";
    public static String NEW_LINE = "\n";
    public static String CATEGORY = "Category: "+NEW_LINE;
    public static String TITLE = "Title: "+NEW_LINE;
    public static String CREATED = "Created: ";
    public static String FINISHED = "Finished: TBD"+NEW_LINE+NEW_LINE;
    public static String STEPS = "Steps: "+NEW_LINE+"1. ";
    public static String IMPLEMENTED = "Implemented: TBD"+NEW_LINE+NEW_LINE;
    public static String DESCRIPTION = "Description: "+NEW_LINE;
    public static String DASH = " - ";
    public static String TABLE_NAME_KEY = "tableName";
    public static String TITLE_KEY = "title";
    public static String CREATED_KEY = "created";
    public static String DESCRIPTION_KEY = "description";
    public static String FINISHED_KEY = "finished";
    public static String IMPLEMENTED_KEY = "implemented";
    public static String STEPS_KEY = "steps";
    public static String JAVA = "java";
    public static String PASSWORD = "password";
    public static String IDEAS_DB_CONN_STR = "jdbc:mysql://localhost:3306/ideas?serverTimezone=UTC";
    public static String TASKS_DB_CONN_STR = "jdbc:mysql://localhost:3306/tasks?serverTimezone=UTC";
    public static String TASKS_RESP_MSG_SUCCESS = "Task Submitted Successfully";
    public static String IDEAS_RESP_MSG_SUCCESS = "Idea Submited Successfully";
    public static String IDEAS_RESP_MSG_FAIL = "Idea Submission Failed";
    public static String TASKS_RESP_MSG_FAIL = "Task Submission Failed";
    
    public static Integer GAP = 10;

    public static Integer W = 8;
    public static Integer H = 10;
    
    public static Integer MAX_FRAME_WIDTH = W * 100;
    public static Integer MAX_FRAME_HEIGHT = H * 100;
    
    public static Integer BTN_PANEL_WIDTH = (MAX_FRAME_WIDTH) - GAP;
    public static Integer BTN_PANEL_HEIGHT = (MAX_FRAME_HEIGHT/8) - GAP;

    public static Integer SUBMIT_PANEL_WIDTH = MAX_FRAME_WIDTH - GAP;
    public static Integer SUBMIT_PANEL_HEIGHT = MAX_FRAME_HEIGHT/8 - GAP;

    public static Integer TEXTAREA_PANEL_WIDTH = MAX_FRAME_WIDTH - GAP;
    public static Integer TEXTAREA_PANEL_HEIGHT = 3*(MAX_FRAME_HEIGHT/8) - GAP;

    public static Integer IDEA_BTN_WIDTH = BTN_PANEL_WIDTH/2 - GAP;
    public static Integer IDEA_BTN_HEIGHT = BTN_PANEL_HEIGHT - GAP;

    public static Integer TASK_BTN_WIDTH = BTN_PANEL_WIDTH/2 - GAP;
    public static Integer TASK_BTN_HEIGHT = BTN_PANEL_HEIGHT - GAP;
    
    public static Integer TEXTAREA_WIDTH = TEXTAREA_PANEL_WIDTH - GAP;
    public static Integer TEXTAREA_HEIGHT = TEXTAREA_PANEL_HEIGHT - GAP;

    public static Integer SUBMIT_BTN_WIDTH = SUBMIT_PANEL_WIDTH - GAP;
    public static Integer SUBMIT_BTN_HEIGHT = SUBMIT_PANEL_HEIGHT - GAP;
}
