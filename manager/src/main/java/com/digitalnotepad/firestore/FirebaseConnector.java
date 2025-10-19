package com.digitalnotepad.firestore;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConnector {
    public static Firestore initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("/home/woody/DigitalNotepad/manager/src/main/resources/ideataskdb-firebase-adminsdk-fbsvc-ba228fd541.json"); // Replace with your key path

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // .setDatabaseUrl("https://ideataskdb.firebaseio.com") // For Realtime Database
                .setProjectId("ideataskdb") // For Cloud Firestore
                .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase initialized successfully.");
        return com.google.firebase.cloud.FirestoreClient.getFirestore();
    }
}
