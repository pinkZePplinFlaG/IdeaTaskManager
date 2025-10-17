package com.digitalnotepad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.pair.Pair;
import com.params.Params;


public class SqlQueryBuilderExecutor {
    private Boolean executionSuccess;
    private SQLException dbEx;

    public Pair<Boolean, SQLException> buildInsertStatementAndExecute( Params<String> params ){
        if(params.getParam(Constants.TABLE_NAME_KEY).equals("art"))
            return buildAndExecuteIdeaInsert(params);
        else
            return buildAndExecuteTaskInsert(params); 
    }

    private Pair<Boolean, SQLException> buildAndExecuteIdeaInsert(Params<String> params){
        try {
            Connection conn = DriverManager.getConnection(Constants.IDEAS_DB_CONN_STR, Constants.JAVA, Constants.PASSWORD);            
            PreparedStatement pstmt = 
                conn.prepareStatement(
                    QueryTemplates.IDEAS_INSERT_TEMPLATE.getLeft() + 
                    params.getParam("tableName") + 
                    QueryTemplates.IDEAS_INSERT_TEMPLATE.getRight()
                );
            pstmt.setString(1, params.getParam("title"));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
            pstmt.setString(3, params.getParam("implemented"));
            pstmt.setString(4, params.getParam("description"));
            pstmt.execute();
            executionSuccess = true;
        } catch (SQLException e) {
            executionSuccess = false;
            // System.out.println(e.getMessage());
        }
        
        return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    }

    private Pair<Boolean, SQLException> buildAndExecuteTaskInsert(Params<String> params){
        try {
            Connection conn = DriverManager.getConnection(Constants.TASKS_DB_CONN_STR, "java", "password"); 
            PreparedStatement pstmt = 
                conn.prepareStatement(
                    QueryTemplates.TASKS_INSERT_TEMPLATE.getLeft() + 
                    params.getParam("tableName") + 
                    QueryTemplates.TASKS_INSERT_TEMPLATE.getRight()
                );
            pstmt.setString(1, params.getParam("title"));
            pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
            pstmt.setString(3, params.getParam("finished"));
            pstmt.setString(4, params.getParam("steps"));
            pstmt.execute();
            executionSuccess = true;
        } catch (SQLException e) {
            executionSuccess = false;
            // System.out.println(e.getMessage());
        }
        return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    }
}

