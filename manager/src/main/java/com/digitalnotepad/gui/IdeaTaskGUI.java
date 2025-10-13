package com.digitalnotepad.gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;

public class IdeaTaskGUI extends JPanel implements ActionListener{

    protected static String APP_NAME = "Digital Notepad";
    protected static String APP_VERSION = "1.0-SNAPSHOT";
    protected static String POPULATE_TASK_TEXT = "Task";
    protected static String POPULATE_IDEA_TEXT = "Idea";
    protected static String SUBMIT = "Submit";
    protected static String EMPTY_STRING = "";
    protected static String TITLE = "Title: \n";
    protected static String CREATED = "Created: ";
    protected static String FINISHED = "Finished: TBD\n\n";
    protected static String STEPS = "Steps: \n1. ";
    protected static String IMPLEMENTED = "Implemented: TBD\n\n";
    protected static String DESCRIPTION = "Description: \n";
    protected static String NEW_LINE = "\n";
    protected static String DASH = " - ";

    protected JPanel btnPanel;
    protected JButton ideaBtn;
    protected JButton taskBtn;
    protected JPanel textAreaPanel;
    protected JTextArea textArea;
    protected JPanel submitPanel;
    protected JButton submitButton;
    
    protected Integer GAP = 10;

    protected Integer W = 8;
    protected Integer H = 10;
    
    protected Integer MAX_FRAME_WIDTH = W * 100;
    protected Integer MAX_FRAME_HEIGHT = H * 100;
    
    protected Integer BTN_PANEL_WIDTH = (MAX_FRAME_WIDTH) - GAP;
    protected Integer BTN_PANEL_HEIGHT = (MAX_FRAME_HEIGHT/8) - GAP;

    protected Integer SUBMIT_PANEL_WIDTH = MAX_FRAME_WIDTH - GAP;
    protected Integer SUBMIT_PANEL_HEIGHT = MAX_FRAME_HEIGHT/8 - GAP;

    protected Integer TEXTAREA_PANEL_WIDTH = MAX_FRAME_WIDTH - GAP;
    protected Integer TEXTAREA_PANEL_HEIGHT = 3*(MAX_FRAME_HEIGHT/8) - GAP;

    protected Integer IDEA_BTN_WIDTH = BTN_PANEL_WIDTH/2 - GAP;
    protected Integer IDEA_BTN_HEIGHT = BTN_PANEL_HEIGHT - GAP;

    protected Integer TASK_BTN_WIDTH = BTN_PANEL_WIDTH/2 - GAP;
    protected Integer TASK_BTN_HEIGHT = BTN_PANEL_HEIGHT - GAP;
    
    protected Integer TEXTAREA_WIDTH = TEXTAREA_PANEL_WIDTH - GAP;
    protected Integer TEXTAREA_HEIGHT = TEXTAREA_PANEL_HEIGHT - GAP;

    protected Integer SUBMIT_BTN_WIDTH = SUBMIT_PANEL_WIDTH - GAP;
    protected Integer SUBMIT_BTN_HEIGHT = SUBMIT_PANEL_HEIGHT - GAP;

    protected ArrayList<String> actions;

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();    
        if(actionCommand.equals(POPULATE_TASK_TEXT) || actionCommand.equals(POPULATE_IDEA_TEXT)){
            textArea.setText(EMPTY_STRING);
            textArea.append(TITLE);
            textArea.append(CREATED+ LocalDate.now().toString()+NEW_LINE);
            if(actionCommand.equals(POPULATE_TASK_TEXT)){
                textArea.append(FINISHED);
                textArea.append(STEPS);
            }
            if(actionCommand.equals(POPULATE_IDEA_TEXT)){
                textArea.append(IMPLEMENTED);
                textArea.append(DESCRIPTION);
            }
        }else{
            textArea.setText(EMPTY_STRING);
        }
    }

    public IdeaTaskGUI(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        btnPanel = new JPanel();
        ideaBtn  = new JButton(POPULATE_IDEA_TEXT);
        taskBtn  = new JButton(POPULATE_TASK_TEXT);
        textAreaPanel = new JPanel();
        textArea = new JTextArea();
        submitPanel = new JPanel();
        submitButton = new JButton(SUBMIT);

        ideaBtn.setPreferredSize(createDimension(IDEA_BTN_WIDTH, IDEA_BTN_HEIGHT));
        taskBtn.setPreferredSize(createDimension(TASK_BTN_WIDTH, TASK_BTN_HEIGHT));
        btnPanel.setPreferredSize(createDimension(BTN_PANEL_WIDTH, BTN_PANEL_HEIGHT));
        textArea.setPreferredSize(createDimension(TEXTAREA_WIDTH, TEXTAREA_HEIGHT));
        textAreaPanel.setPreferredSize(createDimension(TEXTAREA_PANEL_WIDTH, TEXTAREA_PANEL_HEIGHT));
        submitButton.setPreferredSize(createDimension(SUBMIT_BTN_WIDTH, SUBMIT_BTN_HEIGHT));
        submitPanel.setPreferredSize(createDimension(SUBMIT_PANEL_WIDTH, SUBMIT_PANEL_HEIGHT));

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
        actions.add(POPULATE_TASK_TEXT);
        actions.add(POPULATE_IDEA_TEXT);
        actions.add(SUBMIT);
    }

    private Dimension createDimension(Integer width, Integer height){
        return new Dimension(width, height);
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(APP_NAME + DASH + APP_VERSION);
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
