package com.digitalnotepad.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.constants.Constants;

public class Main {
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

    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(Constants.APP_NAME + Constants.DASH + Constants.APP_VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new IdeaTaskGUI());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }  
}
