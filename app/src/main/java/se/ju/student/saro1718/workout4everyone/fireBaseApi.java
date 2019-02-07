package se.ju.student.saro1718.workout4everyone;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class fireBaseApi {

    ///////////////////////////////////////////////////////////////////
    //                                                               //
    //                                                               //
    //                   Firebase Api functions                      //
    //                                                               //
    //                                                               //
    ///////////////////////////////////////////////////////////////////


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void registerUser(String username, String password, String email){
        Map<String,Object> user = new HashMap<>();
        user.put("username",username);
        user.put("password",password);
        user.put("email",email);
        this.db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //success
                System.out.println("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failure
                System.out.println("failure");
            }
        });
    }
}
