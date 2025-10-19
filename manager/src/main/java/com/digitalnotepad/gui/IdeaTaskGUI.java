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

    protected ArrayList<String> actions;

    protected Boolean isTaskSelected;
    protected Boolean isIdeaSelected;
    protected Boolean isShowTasksSelected;
    protected Boolean isShowIdeasSelected;
    protected Boolean isDelTaskSelected;
    protected Boolean isDelIdeaSelected;

    protected Params<String> params;

    protected SqlQueryBuilderExecutor be;

    static Pair<String, String> IDEAS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, implemented, description ) VALUES (?, ?, ?, ?);");
    static Pair<String, String> TASKS_INSERT_TEMPLATE = new Pair<String, String>("INSERT INTO ", " ( title, created, finished, steps ) VALUES (?, ?, ?, ?);");
    

    public IdeaTaskGUI(){
        be = new SqlQueryBuilderExecutor();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        btnPanel = new JPanel();
        ideaBtn  = new JButton(Constants.POPULATE_IDEA_TEXT);
        taskBtn  = new JButton(Constants.POPULATE_TASK_TEXT);
        showIdeasBtn  = new JButton("Show Ideas");
        showTasksBtn  = new JButton("Show Tasks");
        delIdeaBtn  = new JButton("Delete Idea");
        delTaskBtn  = new JButton("Delete Task");
        textAreaPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setCaretColor(Color.CYAN);
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        submitPanel = new JPanel();
        submitButton = new JButton(Constants.SUBMIT);

        ideaBtn.setPreferredSize(createDimension(Constants.IDEA_BTN_WIDTH, Constants.IDEA_BTN_HEIGHT));
        taskBtn.setPreferredSize(createDimension(Constants.TASK_BTN_WIDTH, Constants.TASK_BTN_HEIGHT));
        showTasksBtn.setPreferredSize(createDimension(Constants.TASK_BTN_WIDTH, Constants.TASK_BTN_HEIGHT));
        showIdeasBtn.setPreferredSize(createDimension(Constants.IDEA_BTN_WIDTH, Constants.IDEA_BTN_HEIGHT));
        delIdeaBtn.setPreferredSize(createDimension(Constants.IDEA_BTN_WIDTH, Constants.IDEA_BTN_HEIGHT));
        delIdeaBtn.setPreferredSize(createDimension(Constants.DELETE_IDEA_BTN_WIDTH, Constants.DELETE_IDEA_BTN_HEIGHT));
        delTaskBtn.setPreferredSize(createDimension(Constants.DELETE_TASK_BTN_WIDTH, Constants.DELETE_TASK_BTN_HEIGHT));
        textAreaPanel.setPreferredSize(createDimension(Constants.TEXTAREA_PANEL_WIDTH, Constants.TEXTAREA_PANEL_HEIGHT));
        scrollPane.setPreferredSize(createDimension(Constants.TEXTAREA_PANEL_WIDTH, Constants.TEXTAREA_PANEL_HEIGHT));
        submitButton.setPreferredSize(createDimension(Constants.SUBMIT_BTN_WIDTH, Constants.SUBMIT_BTN_HEIGHT));
        submitPanel.setPreferredSize(createDimension(Constants.SUBMIT_PANEL_WIDTH, Constants.SUBMIT_PANEL_HEIGHT));

        ideaBtn.addActionListener(this);
        taskBtn.addActionListener(this);
        showIdeasBtn.addActionListener(this);
        showTasksBtn.addActionListener(this);
        delIdeaBtn.addActionListener(this);
        delTaskBtn.addActionListener(this);
        submitButton.addActionListener(this);

        btnPanel.add(ideaBtn);
        btnPanel.add(showIdeasBtn);
        btnPanel.add(delIdeaBtn);
        btnPanel.add(taskBtn);
        btnPanel.add(showTasksBtn);
        btnPanel.add(delTaskBtn);

        textAreaPanel.add(scrollPane);

        submitPanel.add(submitButton);

        add(btnPanel);
        add(textAreaPanel);
        add(submitPanel);

        actions = new ArrayList<String>();
        actions.add(Constants.POPULATE_TASK_TEXT);
        actions.add(Constants.POPULATE_IDEA_TEXT);
        actions.add(Constants.SUBMIT);

        isIdeaSelected = false;
        isTaskSelected = false;
    }

    private Dimension createDimension(Integer width, Integer height){
        return new Dimension(width, height);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();    
        if(actionCommand.equals("Delete Task")){
            isShowIdeasSelected = false;
            isShowTasksSelected = false;
            isIdeaSelected = false;
            isTaskSelected = false;
            isDelIdeaSelected = true;
            isDelTaskSelected = false;
        }

        if(actionCommand.equals("Delete Idea")){
            isShowIdeasSelected = false;
            isShowTasksSelected = false;
            isIdeaSelected = false;
            isTaskSelected = false;
            isDelIdeaSelected = true;
            isDelTaskSelected = false;
        }

        if(actionCommand.equals("Show Tasks")){
            isShowIdeasSelected = false;
            isShowTasksSelected = true;
            isIdeaSelected = false;
            isTaskSelected = false;
            isDelIdeaSelected = false;
            isDelTaskSelected = false;
        }

        if(actionCommand.equals("Show Ideas")){
            isShowIdeasSelected = true;
            isShowTasksSelected = false;
            isIdeaSelected = false;
            isTaskSelected = false;
            isDelIdeaSelected = false;
            isDelTaskSelected = false;
        }

        if(actionCommand.equals(Constants.POPULATE_TASK_TEXT) || actionCommand.equals(Constants.POPULATE_IDEA_TEXT)){
            textArea.setForeground(Color.CYAN);
            textArea.setText(Constants.EMPTY_STRING);
            textArea.append(Constants.TITLE);
            textArea.append(Constants.CATEGORY);
            textArea.append(Constants.CREATED+ LocalDate.now().toString()+Constants.NEW_LINE);
            
            if(actionCommand.equals(Constants.POPULATE_TASK_TEXT)){
                textArea.append(Constants.FINISHED);
                textArea.append(Constants.STEPS);
                isIdeaSelected = false;
                isTaskSelected = true;
                isShowIdeasSelected = false;
                isShowTasksSelected = false;
            }

            if(actionCommand.equals(Constants.POPULATE_IDEA_TEXT)){
                textArea.append(Constants.IMPLEMENTED);
                textArea.append(Constants.DESCRIPTION);
                isIdeaSelected = true;
                isTaskSelected = false;
                isShowIdeasSelected = false;
                isShowTasksSelected = false;
            }
        }else if(isIdeaSelected || isTaskSelected){
            String text = textArea.getText();
            
            params = new Params<String>(new HashMap<>());
            String fTitle = TextParser.findTitle(text);
            params.addParam(Constants.TITLE_KEY,fTitle);
            params.addParam(Constants.TABLE_NAME_KEY, TextParser.findCat(text));

            if(isIdeaSelected){
                params.addParam(Constants.IMPLEMENTED_KEY, TextParser.findImpl(text));
                params.addParam(Constants.DESCRIPTION_KEY, TextParser.findDesc(text));
                params.addParam("insertTemplateRight", IDEAS_INSERT_TEMPLATE.getRight());
                params.addParam("insertTemplateLeft", IDEAS_INSERT_TEMPLATE.getLeft());
                params.addParam("dbConnectionStr", Constants.IDEAS_DB_CONN_STR);
                params.addParam("isIdeaInsert", "true");
                
            }else if(isTaskSelected){
                params.addParam(Constants.FINISHED_KEY, TextParser.findFin(text));
                params.addParam(Constants.STEPS_KEY, TextParser.findSteps(text));
                params.addParam("insertTemplateRight", TASKS_INSERT_TEMPLATE.getRight());
                params.addParam("insertTemplateLeft", TASKS_INSERT_TEMPLATE.getLeft());
                params.addParam("dbConnectionStr", Constants.TASKS_DB_CONN_STR);
                params.addParam("isIdeaInsert", "false");
            }
            
            Pair<Boolean, SQLException> response = be.buildAndExecuteInsert(params);
            textArea.setText(Constants.EMPTY_STRING);
            
            if(isIdeaSelected && response.getLeft()){
                textArea.append(Constants.IDEAS_RESP_MSG_SUCCESS);
            }else if(isIdeaSelected){
                textArea.append(Constants.IDEAS_RESP_MSG_FAIL);
                textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());
                textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
            }

            if(isTaskSelected && response.getLeft()){
                textArea.append(Constants.TASKS_RESP_MSG_SUCCESS);
            }else if(isTaskSelected){
                textArea.append(Constants.TASKS_RESP_MSG_FAIL);
                textArea.append(Constants.NEW_LINE + response.getRight().getErrorCode());
                textArea.append(Constants.NEW_LINE + response.getRight().getLocalizedMessage());
            }
            isTaskSelected = false;
            isIdeaSelected = false;
        }else if(isShowIdeasSelected || isShowTasksSelected){
            textArea.setText(Constants.EMPTY_STRING);
            params = new Params<String>(new HashMap<>());
            if(isShowIdeasSelected){
                params.addParam("tableName", "art");
                params.addParam("dbConnectionStr", Constants.IDEAS_DB_CONN_STR);
                Pair<ResultSet, SQLException> response = be.buildAndExecuteSelectAll(params);
                ArrayList<String> displayStrs = new ArrayList<>();
                ResultSet resultSet = response.getLeft();
                try {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        Date created = resultSet.getDate("created");
                        String impl = resultSet.getString("implemented");
                        String desc = resultSet.getString("description");
                        displayStrs.add(
                            "title: " + title + Constants.NEW_LINE + 
                            "created: " + created.toString() + Constants.NEW_LINE +
                            "implemented: " + impl + Constants.NEW_LINE + 
                            "description: " + desc + Constants.NEW_LINE + 
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
            }else{
                params.addParam("tableName", "jobsearch");
                params.addParam("dbConnectionStr", Constants.TASKS_DB_CONN_STR);
                Pair<ResultSet, SQLException> response = be.buildAndExecuteSelectAll(params);
                ResultSet resultSet = response.getLeft();
                ArrayList<String> displayStrs = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        Date created = resultSet.getDate("created");
                        String fin = resultSet.getString("finished");
                        String steps = resultSet.getString("steps");
                        displayStrs.add(
                            "title: " + title + Constants.NEW_LINE + 
                            "created: " + created.toString() + Constants.NEW_LINE +
                            "finished: " + fin + Constants.NEW_LINE + 
                            "steps: " + steps + Constants.NEW_LINE + 
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
        }else if(isDelIdeaSelected|| isDelTaskSelected){
            
        }
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(Constants.APP_NAME + Constants.DASH + Constants.APP_VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new IdeaTaskGUI());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }  

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
