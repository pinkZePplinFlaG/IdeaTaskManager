package com.digitalnotepad.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.constants.Constants;
import com.pair.Pair;
import com.params.Params;


public class SqlQueryBuilderExecutor {
    private Boolean executionSuccess;
    private SQLException dbEx;

    static Pair<String, String> IDEAS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, implemented, description ) VALUES (?, ?, ?, ?);");
    static Pair<String, String> TASKS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, finished, steps ) VALUES (?, ?, ?, ?);");
    static Pair<String, String> SELECT_ALL_TEMPLATE = new Pair<String, String>("SELECT * FROM ", ";");

    public Pair<Boolean, SQLException> buildInsertStatementAndExecute( Params<String> params ){
        if(params.getParam(Constants.TABLE_NAME_KEY).equals("art"))
            return buildAndExecuteIdeaInsert(params);
        else
            return buildAndExecuteTaskInsert(params); 
    }

    public Pair<ResultSet, SQLException> buildAndExecuteSelectAll(Params<String> params){
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(Constants.TASKS_DB_CONN_STR, Constants.JAVA, Constants.PASSWORD); 
            PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_TEMPLATE.getLeft() + params.getParam(Constants.TABLE_NAME_KEY) + SELECT_ALL_TEMPLATE.getRight());
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            dbEx = e;
        }
        return new Pair<ResultSet, SQLException>(rs, dbEx);
    }

    private Pair<Boolean, SQLException> buildAndExecuteIdeaInsert(Params<String> params){
        try {
            Connection conn = DriverManager.getConnection(Constants.IDEAS_DB_CONN_STR, Constants.JAVA, Constants.PASSWORD);            
            PreparedStatement pstmt = 
                conn.prepareStatement(
                    IDEAS_INSERT_TEMPLATE.getLeft() + 
                    params.getParam(Constants.TABLE_NAME_KEY) + 
                    IDEAS_INSERT_TEMPLATE.getRight()
                );
            pstmt.setString(1, params.getParam(Constants.TITLE_KEY));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
            pstmt.setString(3, params.getParam(Constants.IMPLEMENTED_KEY));
            pstmt.setString(4, params.getParam(Constants.DESCRIPTION_KEY));
            pstmt.execute();
            executionSuccess = true;
        } catch (SQLException e) {
            executionSuccess = false;
            dbEx = e;
        }
        return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    }

    private Pair<Boolean, SQLException> buildAndExecuteTaskInsert(Params<String> params){
        try {
            Connection conn = DriverManager.getConnection(Constants.TASKS_DB_CONN_STR, Constants.JAVA, Constants.PASSWORD); 
            PreparedStatement pstmt = 
                conn.prepareStatement(
                    TASKS_INSERT_TEMPLATE.getLeft() + 
                    params.getParam(Constants.TABLE_NAME_KEY) + 
                    TASKS_INSERT_TEMPLATE.getRight()
                );
            pstmt.setString(1, params.getParam(Constants.TITLE_KEY));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
            pstmt.setString(3, params.getParam(Constants.FINISHED_KEY));
            pstmt.setString(4, params.getParam(Constants.STEPS_KEY));
            pstmt.execute();
            executionSuccess = true;
        } catch (SQLException e) {
            executionSuccess = false;
            dbEx = e;
        }
        return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    }
}
