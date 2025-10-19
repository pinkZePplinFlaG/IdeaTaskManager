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
    static Pair<String, String> SELECT_ALL_TEMPLATE = new Pair<String, String>("SELECT * FROM ", ";");

    public Pair<ResultSet, SQLException> buildAndExecuteSelectAll(Params<String> params){
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(params.getParam("dbConnectionStr"), Constants.JAVA, Constants.PASSWORD); 
            PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_TEMPLATE.getLeft() + params.getParam(Constants.TABLE_NAME_KEY) + SELECT_ALL_TEMPLATE.getRight());
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            dbEx = e;
        }
        return new Pair<ResultSet, SQLException>(rs, dbEx);
    }

    public Pair<Boolean, SQLException> buildAndExecuteInsert(Params<String> params){
    try {
            Connection conn = DriverManager.getConnection(params.getParam("dbConnectionStr"), Constants.JAVA, Constants.PASSWORD);            
            
            if(params.getParam("isIdeaInsert").equals("true")){
                PreparedStatement pstmt = conn.prepareStatement( "INSERT INTO art ( title, created, implemented, description) VALUES (?,?,?,?);");
                pstmt.setString(1, params.getParam("title"));
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
                pstmt.setString(3, params.getParam("implemented"));
                pstmt.setString(4, params.getParam("description"));
                pstmt.execute();
            }else{
                PreparedStatement pstmt = conn.prepareStatement( "INSERT INTO jobsearch ( title, created, finished, steps) VALUES (?,?,?,?);");
                pstmt.setString(1, params.getParam("title"));
                pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
                pstmt.setString(3, params.getParam(Constants.FINISHED_KEY));
                pstmt.setString(4, params.getParam(Constants.STEPS_KEY));
                pstmt.execute();
            }
            executionSuccess = true;
        } catch (SQLException e) {
            executionSuccess = false;
            dbEx = e;
        }
        return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    }
        //     try {
    //         Connection conn = DriverManager.getConnection(params.getParam("dbConnectionStr"), Constants.JAVA, Constants.PASSWORD);            
    //         PreparedStatement pstmt = 
    //             conn.prepareStatement( 
    //                 params.getParam("insertTemplateLeft") + 
    //                 params.getParam(Constants.TABLE_NAME_KEY) + 
    //                 params.getParam("insertTemplateRight")
    //             );
    //         pstmt.setString(1, params.getParam(Constants.TITLE_KEY));
    //         pstmt.setDate(2, java.sql.Date.valueOf(LocalDate.now().toString()));
            
    //         if(params.getParam("isIdeaInsert").equals("true")){
    //             pstmt.setString(3, params.getParam(Constants.IMPLEMENTED_KEY));
    //             pstmt.setString(4, params.getParam(Constants.DESCRIPTION_KEY));
    //         }else{
    //             pstmt.setString(3, params.getParam(Constants.FINISHED_KEY));
    //             pstmt.setString(4, params.getParam(Constants.STEPS_KEY));
    //         }
    //         pstmt.execute();
    //         executionSuccess = true;
    //     } catch (SQLException e) {
    //         executionSuccess = false;
    //         dbEx = e;
    //     }
    //     return new Pair<Boolean, SQLException>(executionSuccess, dbEx);
    // }
}
