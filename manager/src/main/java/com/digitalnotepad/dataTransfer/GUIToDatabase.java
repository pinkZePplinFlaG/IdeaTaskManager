package com.digitalnotepad.dataTransfer;

import java.util.List;

import com.digitalnotepad.database.MySQLConnector;

public class GUIToDatabase {

    private String SELECT = "SELECT"; 
    private static String STAR = "*"; 
    private static String FROM = "FROM";
    private static String SPACE = " ";
    
    public List<String> queryDatabase(List queryParams, String queryType){
        String sqlQuery = buildQuery(queryParams, queryType);
        List<String> responseData = MySQLConnector.connectToDatabase(sqlQuery);
        return responseData;
    }

    private String buildQuery(List<String> queryParams, String queryType){
        StringBuilder queryBuiler = new StringBuilder();
        if(queryType.equals("selectAll")){
            queryBuiler.append(SELECT).append(STAR).append(FROM).append(SPACE);
            for(String param : queryParams){
                queryBuiler.append(param);
            }
        }
        return queryBuiler.toString();
    }
}
