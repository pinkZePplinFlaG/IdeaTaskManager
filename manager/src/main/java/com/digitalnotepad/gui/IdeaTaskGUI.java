package com.digitalnotepad.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import com.digitalnotepad.Constants;
import com.digitalnotepad.SqlQueryBuilderExecutor;
import com.textparser.TextParser;
import com.pair.Pair;
import com.params.Params;

public class IdeaTaskGUI extends JPanel implements ActionListener{

    protected JPanel btnPanel;
    protected JButton ideaBtn;
    protected JButton taskBtn;
    protected JPanel textAreaPanel;
    protected JTextArea textArea;
    protected JPanel submitPanel;
    protected JButton submitButton;

    protected ArrayList<String> actions;

    protected Boolean isTaskSelected;
    protected Boolean isIdeaSelected;

    protected Params<String> params;

    protected SqlQueryBuilderExecutor be;

    public IdeaTaskGUI(){
        be = new SqlQueryBuilderExecutor();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        btnPanel = new JPanel();
        ideaBtn  = new JButton(Constants.POPULATE_IDEA_TEXT);
        taskBtn  = new JButton(Constants.POPULATE_TASK_TEXT);
        textAreaPanel = new JPanel();
        textArea = new JTextArea();
        submitPanel = new JPanel();
        submitButton = new JButton(Constants.SUBMIT);

        ideaBtn.setPreferredSize(createDimension(Constants.IDEA_BTN_WIDTH, Constants.IDEA_BTN_HEIGHT));
        taskBtn.setPreferredSize(createDimension(Constants.TASK_BTN_WIDTH, Constants.TASK_BTN_HEIGHT));
        btnPanel.setPreferredSize(createDimension(Constants.BTN_PANEL_WIDTH, Constants.BTN_PANEL_HEIGHT));
        textArea.setPreferredSize(createDimension(Constants.TEXTAREA_WIDTH, Constants.TEXTAREA_HEIGHT));
        textAreaPanel.setPreferredSize(createDimension(Constants.TEXTAREA_PANEL_WIDTH, Constants.TEXTAREA_PANEL_HEIGHT));
        submitButton.setPreferredSize(createDimension(Constants.SUBMIT_BTN_WIDTH, Constants.SUBMIT_BTN_HEIGHT));
        submitPanel.setPreferredSize(createDimension(Constants.SUBMIT_PANEL_WIDTH, Constants.SUBMIT_PANEL_HEIGHT));

        ideaBtn.addActionListener(this);
        taskBtn.addActionListener(this);
        submitButton.addActionListener(this);

        btnPanel.add(ideaBtn);
        btnPanel.add(taskBtn);

        textAreaPanel.add(textArea);

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
        if(actionCommand.equals(Constants.POPULATE_TASK_TEXT) || actionCommand.equals(Constants.POPULATE_IDEA_TEXT)){
            textArea.setText(Constants.EMPTY_STRING);
            textArea.append(Constants.TITLE);
            textArea.append(Constants.CATEGORY);
            textArea.append(Constants.CREATED+ LocalDate.now().toString()+Constants.NEW_LINE);
            
            if(actionCommand.equals(Constants.POPULATE_TASK_TEXT)){
                textArea.append(Constants.FINISHED);
                textArea.append(Constants.STEPS);
                isIdeaSelected = false;
                isTaskSelected = true;
            }

            if(actionCommand.equals(Constants.POPULATE_IDEA_TEXT)){
                textArea.append(Constants.IMPLEMENTED);
                textArea.append(Constants.DESCRIPTION);
                isIdeaSelected = true;
                isTaskSelected = false;
            }
        }else{
            params = new Params<String>(new HashMap<>());
            String text = textArea.getText();
            params.addParam(Constants.TABLE_NAME_KEY, TextParser.findCat(text));
            params.addParam(Constants.TITLE_KEY,TextParser.findTitle(text));

            Boolean isTask = false;
            Boolean isIdea = false;
            if(isIdeaSelected){//insert idea from textarea into database
                params.addParam(Constants.IMPLEMENTED_KEY, TextParser.findImpl(text));
                params.addParam(Constants.DESCRIPTION_KEY, TextParser.findDesc(text));
                isTaskSelected = false;
                isIdea = true;
            }else if(isTaskSelected){//insert task from textarea into database
                params.addParam(Constants.FINISHED_KEY, TextParser.findFin(text));
                params.addParam(Constants.STEPS_KEY, TextParser.findSteps(text));
                isIdeaSelected = false;
                isTask = true;
            }
            
            Pair<Boolean, SQLException> response = be.buildInsertStatementAndExecute(params);
            textArea.setText(Constants.EMPTY_STRING);
            
            if(isIdea && response.getLeft())
                textArea.append(Constants.IDEAS_RESP_MSG_SUCCESS);
            else if(isIdea)
                textArea.append(Constants.IDEAS_RESP_MSG_FAIL);
            
            if(isTask && response.getLeft())
                textArea.append(Constants.TASKS_RESP_MSG_SUCCESS);
            else if(isTask)
                textArea.append(Constants.TASKS_RESP_MSG_FAIL);
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
