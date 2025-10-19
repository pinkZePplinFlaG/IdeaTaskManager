package com.digitalnotepad.firestore;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.params.Params;

public class FirebaseFirestoreAdapter {
    public static ApiFuture<WriteResult> insertIntoDB(Firestore db, Params<String> params){
        ApiFuture<WriteResult> result = null;
        String collectionName = params.getParam("collectionName");
        DocumentReference docRef = db.collection(collectionName).document();
            Map<String, Object> data = new HashMap<>();
            data.put("title", params.getParam("title"));
            data.put("created", LocalDate.now().toString());
        if(collectionName.equals("ideas")){
            data.put("implemented", params.getParam("implemented"));
            data.put("description", params.getParam("description"));
        }else{
            data.put("implemented", params.getParam("finished"));
            data.put("description", params.getParam("steps"));
        }
        result = docRef.set(data);
        return result;
    }

    public static ApiFuture<QuerySnapshot> selectAllFromDb(Firestore db, Params<String> params){
        return db.collection(params.getParam("collectionName")).get();
    }

    public static ApiFuture<WriteResult> deleteFromDb(Firestore db, Params<String> params){
        return db.collection(params.getParam("collectionName"))
                    .document(params.getParam("documentId"))
                    .delete();
    }
}
