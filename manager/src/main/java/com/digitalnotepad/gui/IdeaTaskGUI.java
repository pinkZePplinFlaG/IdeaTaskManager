package com.digitalnotepad.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import com.constants.Constants;
import com.digitalnotepad.sql.SqlQueryBuilderExecutor;
import com.textparser.TextParser;
import com.pair.Pair;
import com.params.Params;

public class IdeaTaskGUI extends JPanel implements ActionListener{

    protected JPanel btnPanel;
    protected JButton ideaBtn;
    protected JButton taskBtn;
    protected JButton showIdeasBtn;
    protected JButton showTasksBtn;
    protected JPanel textAreaPanel;
    protected JTextArea textArea;
    protected JScrollPane scrollPane;
    protected JPanel submitPanel;
    protected JButton submitButton;
    protected JButton delIdeaBtn;
    protected JButton delTaskBtn;

    protected Boolean isCreateTask;
    protected Boolean isCreateIdea;
    protected Boolean isShowTasks;
    protected Boolean isShowIdeas;
    protected Boolean isDelTask;
    protected Boolean isDelIdea;
    protected Boolean isSubmit;

    protected String CREATE_IDEA = "Create Idea";
    protected String CREATE_TASK = "Create Task";
    protected String SHOW_IDEAS = "Show Ideas";
    protected String SHOW_TASKS = "Show Tasks";
    protected String DELETE_IDEA = "Delete Idea";
    protected String DELETE_TASK = "Delete Task";
    protected String SUBMIT = "Submit";

    protected SqlQueryBuilderExecutor be;

    static Pair<String, String> IDEAS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, implemented, description ) VALUES (?, ?, ?, ?);");
    static Pair<String, String> TASKS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, finished, steps ) VALUES (?, ?, ?, ?);");

    public static Integer GAP = 5;

    public static Integer W = 5;
    public static Integer H = 7;
    
    public Integer MAX_FRAME_WIDTH = W * 100;
    public Integer MAX_FRAME_HEIGHT = H * 100;
    public Integer BTN_PANEL_WIDTH = (MAX_FRAME_WIDTH/4) - GAP;
    public Integer BTN_PANEL_HEIGHT = (MAX_FRAME_HEIGHT) - GAP;
    public Integer TA_PANEL_WIDTH = 3*(MAX_FRAME_WIDTH/4) - GAP;
    public Integer TA_PANEL_HEIGHT = (MAX_FRAME_HEIGHT) - GAP;
    public Integer BTN_WIDTH = BTN_PANEL_WIDTH - GAP;
    public Integer BTN_HEIGHT = BTN_PANEL_HEIGHT/7 - GAP;

    public IdeaTaskGUI(){
        be = new SqlQueryBuilderExecutor();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        
        ideaBtn  = new JButton(CREATE_IDEA);
        taskBtn  = new JButton(CREATE_TASK);
        showIdeasBtn  = new JButton(SHOW_IDEAS);
        showTasksBtn  = new JButton(SHOW_TASKS);
        delIdeaBtn  = new JButton(DELETE_IDEA);
        delTaskBtn  = new JButton(DELETE_TASK);
        textAreaPanel = new JPanel();
        btnPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setCaretColor(Color.CYAN);
        textArea.setForeground(Color.CYAN);
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        submitButton = new JButton(SUBMIT);
        
        scrollPane.setPreferredSize(createDimension(TA_PANEL_WIDTH, TA_PANEL_HEIGHT));
        

        ideaBtn.addActionListener(this);
        taskBtn.addActionListener(this);
        showIdeasBtn.addActionListener(this);
        showTasksBtn.addActionListener(this);
        delIdeaBtn.addActionListener(this);
        delTaskBtn.addActionListener(this);
        submitButton.addActionListener(this);

        textAreaPanel.add(scrollPane);
        btnPanel.setPreferredSize(createDimension(BTN_PANEL_WIDTH, BTN_PANEL_HEIGHT));
        ideaBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        taskBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        showTasksBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        showIdeasBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        delIdeaBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        delTaskBtn.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));
        submitButton.setPreferredSize(createDimension(BTN_WIDTH, BTN_HEIGHT));

        btnPanel.add(ideaBtn);
        btnPanel.add(showIdeasBtn);
        btnPanel.add(delIdeaBtn);
        btnPanel.add(taskBtn);
        btnPanel.add(showTasksBtn);
        btnPanel.add(delTaskBtn);

        btnPanel.add(submitButton);
        add(textAreaPanel);
        add(btnPanel);
    }

    private Dimension createDimension(Integer width, Integer height){
        return new Dimension(width, height);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();    
        decideAction(actionCommand);
        
        if(!isSubmit){
            textArea.setText(Constants.EMPTY_STRING);
            if(isCreateIdea){
                appendCreateIdeaTemplateToTextArea();
            }else if(isCreateTask){
                appendCreateTaskTemplateToTextArea();
            }else if(isDelIdea || isDelTask){
                appendDeleteTemplateToTextArea();
            }else if(isShowIdeas || isShowTasks){
                Params<String> params = new Params<String>(new HashMap<>());
                if(isShowIdeas){
                    params.addParam(Constants.TABLE_NAME_KEY, "art");
                    params.addParam("dbConnectionStr", Constants.IDEAS_DB_CONN_STR);
                    appendIdeasToTextArea(params);
                }else{
                    params.addParam(Constants.TABLE_NAME_KEY, "jobsearch");
                    params.addParam("dbConnectionStr", Constants.TASKS_DB_CONN_STR);
                    appendTasksToTextArea(params);
                }
            }
        }else{
            Params<String> params = new Params<String>(new HashMap<>());
            String text = textArea.getText();
            textArea.setText(Constants.EMPTY_STRING);
            String fTitle = TextParser.findTitle(text);
            params.addParam(Constants.TITLE_KEY,fTitle);
            params.addParam(Constants.TABLE_NAME_KEY, TextParser.findCat(text));

            if(isCreateIdea || isCreateTask){
                params.addParam("isIdeaInsert", isCreateIdea.toString());
                if(isCreateIdea){
                    params.addParam(Constants.IMPLEMENTED_KEY, TextParser.findImpl(text));
                    params.addParam(Constants.DESCRIPTION_KEY, TextParser.findDesc(text));
                    params.addParam("insertTemplateRight", IDEAS_INSERT_TEMPLATE.getRight());
                    params.addParam("insertTemplateLeft", IDEAS_INSERT_TEMPLATE.getLeft());
                    params.addParam("dbConnectionStr", Constants.IDEAS_DB_CONN_STR);
                }else{
                    params.addParam(Constants.FINISHED_KEY, TextParser.findFin(text));
                    params.addParam(Constants.STEPS_KEY, TextParser.findSteps(text));
                    params.addParam("insertTemplateRight", TASKS_INSERT_TEMPLATE.getRight());
                    params.addParam("insertTemplateLeft", TASKS_INSERT_TEMPLATE.getLeft());
                    params.addParam("dbConnectionStr", Constants.TASKS_DB_CONN_STR);
                }

                Pair<Boolean, SQLException> response = be.buildAndExecuteInsert(params);
                
                if(response.getLeft() && isCreateIdea){
                    textArea.append(Constants.IDEAS_RESP_MSG_SUCCESS);
                }else if(isCreateIdea){
                    textArea.append(Constants.IDEAS_RESP_MSG_FAIL);
                    textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());   
                    textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
                }else if(response.getLeft() && isCreateTask){
                    textArea.append(Constants.TASKS_RESP_MSG_SUCCESS);
                }else{
                    textArea.append(Constants.TASKS_RESP_MSG_FAIL);
                    textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());
                    textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
                }
            }

            if(isDelIdea || isDelTask){
                params.addParam("isDelIdea", isDelIdea.toString());
                if(isDelIdea){
                    params.addParam("dbConnectionStr", Constants.IDEAS_DB_CONN_STR);
                }else{
                    params.addParam("dbConnectionStr", Constants.TASKS_DB_CONN_STR);
                }
                
                Pair<Boolean, SQLException> response = be.buildAndExecuteDelete(params);
                
                if(response.getLeft() && isDelIdea){
                    textArea.append("Idea deleted successfully");
                }else if(isDelIdea){
                    textArea.append("Idea not deleted");
                    textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());
                    textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
                }else if(response.getLeft() && isDelTask){
                    textArea.append("Task deleted successfully");
                }else{
                    textArea.append("Task not deleted");
                    textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());
                    textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
                }
            }
        }

    }

    private void appendIdeasToTextArea(Params<String> params){
        Pair<ResultSet, SQLException> response = be.buildAndExecuteSelectAll(params);
        ResultSet resultSet = response.getLeft();
        ArrayList<String> displayStrs = new ArrayList<>();
        if(response.getLeft() != null){
            try {
                while (resultSet.next()) {
                    String title = resultSet.getString("Title");
                    Date created = resultSet.getDate("Created");
                    String impl = resultSet.getString("Implemented");
                    String desc = resultSet.getString("Description");
                    displayStrs.add(
                        "Title: " + title + Constants.NEW_LINE + 
                        "Created: " + created.toString() + Constants.NEW_LINE +
                        "Implemented: " + impl + Constants.NEW_LINE + 
                        "Description: " + desc + Constants.NEW_LINE + 
                        Constants.NEW_LINE
                    );
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            for(String dispStr : displayStrs){
                textArea.append(dispStr);
            }
        }
    }

    private void appendTasksToTextArea(Params<String> params){
        Pair<ResultSet, SQLException> response = be.buildAndExecuteSelectAll(params);
        ResultSet resultSet = response.getLeft();
        ArrayList<String> displayStrs = new ArrayList<>();
        if(response.getLeft() != null){
            try {
                while (resultSet.next()) {
                    String title = resultSet.getString("Title");
                    Date created = resultSet.getDate("Created");
                    String fin = resultSet.getString("Finished");
                    String steps = resultSet.getString("Steps");
                    displayStrs.add(
                        "Title: " + title + Constants.NEW_LINE + 
                        "Created: " + created.toString() + Constants.NEW_LINE +
                        "Finished: " + fin + Constants.NEW_LINE + 
                        "Steps: " +Constants.NEW_LINE+ steps + Constants.NEW_LINE + 
                        Constants.NEW_LINE
                    );
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            for(String dispStr : displayStrs){
                textArea.append(dispStr);
            }
        }
    }

    private void appendDeleteTemplateToTextArea(){
        textArea.append(Constants.TITLE);
        textArea.append(Constants.CATEGORY);
    }

    private void appendCreateIdeaTemplateToTextArea(){
        textArea.append(Constants.TITLE);
        textArea.append(Constants.CATEGORY);
        textArea.append(Constants.CREATED+ LocalDate.now().toString()+Constants.NEW_LINE);
        textArea.append(Constants.IMPLEMENTED);
        textArea.append(Constants.DESCRIPTION);
    }

    private void appendCreateTaskTemplateToTextArea(){
        textArea.append(Constants.TITLE);
        textArea.append(Constants.CATEGORY);
        textArea.append(Constants.CREATED+ LocalDate.now().toString()+Constants.NEW_LINE);
        textArea.append(Constants.FINISHED);
        textArea.append(Constants.STEPS);
    }

    private void decideAction(String actionCommand){
        if(actionCommand.equals(DELETE_TASK)){
            isShowIdeas = false;
            isShowTasks = false;
            isCreateIdea = false;
            isCreateTask = false;
            isDelIdea= false;
            isDelTask = true;
            isSubmit = false;
        }

        if(actionCommand.equals(DELETE_IDEA)){
            isShowIdeas = false;
            isShowTasks = false;
            isCreateIdea = false;
            isCreateTask = false;
            isDelIdea= true;
            isDelTask = false;
            isSubmit = false;
        }

        if(actionCommand.equals(SHOW_TASKS)){
            isShowIdeas = false;
            isShowTasks = true;
            isCreateIdea = false;
            isCreateTask = false;
            isDelIdea= false;
            isDelTask = false;
            isSubmit = false;
        }

        if(actionCommand.equals(SHOW_IDEAS)){
            isShowIdeas = true;
            isShowTasks = false;
            isCreateIdea = false;
            isCreateTask = false;
            isDelIdea= false;
            isDelTask = false;
            isSubmit = false;
        }

        if(actionCommand.equals(CREATE_TASK)){
            isCreateIdea = false;
            isCreateTask = true;
            isShowIdeas = false;
            isShowTasks = false;
            isDelIdea= false;
            isDelTask = false;
            isSubmit = false;
        }

        if(actionCommand.equals(CREATE_IDEA)){
            isCreateIdea = true;
            isCreateTask = false;
            isShowIdeas = false;
            isShowTasks = false;
            isDelIdea= false;
            isDelTask = false;
            isSubmit = false;
        }

        if(actionCommand.equals(SUBMIT)){
            isShowIdeas = false;
            isShowTasks = false;
            isSubmit = true;
        }
    }
}
