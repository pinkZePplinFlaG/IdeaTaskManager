package com.digitalnotepad;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.constants.Constants;
import com.digitalnotepad.firestore.FirebaseConnector;
import com.digitalnotepad.gui.IdeaTaskGUI;

public class Main {
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame(Constants.APP_NAME + Constants.DASH + Constants.APP_VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        try {
            frame.add(new IdeaTaskGUI(FirebaseConnector.initializeFirebase()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
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
