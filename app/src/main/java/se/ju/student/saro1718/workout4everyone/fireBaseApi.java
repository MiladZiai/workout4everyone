package se.ju.student.saro1718.workout4everyone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.Bytes;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class fireBaseApi {

    ///////////////////////////////////////////////////////////////////
    //                                                               //
    //                                                               //
    //                   Firebase Api functions                      //
    //                                                               //
    //                                                               //
    ///////////////////////////////////////////////////////////////////

    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;
    private static FirebaseStorage storage ;


    fireBaseApi(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

    }

    ///////////////////////////////////////////////////////////////
    //                      firebase cloud                       //
    ///////////////////////////////////////////////////////////////

    //creates workout
    public String createWorkout(workoutsData.workoutVariables workout){
        Map<String, Object> workoutToBeMade = new HashMap<>();
        workoutToBeMade.put("ownerId",workout.getOwnerId());
        workoutToBeMade.put("title",workout.getWorkoutTitle());
        workoutToBeMade.put("exerciseTitles",workout.getWorkoutExerciseTitle());
        workoutToBeMade.put("exerciseDescription",workout.getWorkoutExerciseDescription());
        workoutToBeMade.put("workoutLevel",workout.getWorkoutLevel());

        final String[] documentId = new String[1];

        db.collection("workoutsTable").add(workoutToBeMade).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                documentId[0] = documentReference.getId();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"error adding document" ,e);
                documentId[0] = null;
            }
        });
        return documentId[0];
    }

    //read all data from cloud
    public void readAllDocuments(){
        workoutsData.workoutList.clear();
        db.collection("workoutsTable").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        printDataToArray(document,true);
                    }
                } else {
                    Log.w(TAG, "error", task.getException());
                }
            }
        });
    }

    //read specific documents by ownerID
    public void readSpecificDocuments(String searchedOwnerId){
        db.collection("workoutsTable").whereEqualTo("ownerId",searchedOwnerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        printDataToArray(document,false);
                    }
                }else{
                    Log.w(TAG, "error", task.getException());
                }
            }
        });

    }

    //user by other functions to print data to arrayList (used by readData -true-, readOneData -false-)
    private void printDataToArray(QueryDocumentSnapshot document, boolean allOrNot){
        String ownerID = document.get("ownerId").toString();
        String title = document.get("title").toString();
        ArrayList<String[]> exerciseTitles = (ArrayList<String[]>) document.get("exerciseTitles");
        ArrayList<String[]> exerciseDescription = (ArrayList<String[]>) document.get("exerciseDescription");
        String workoutLevel = document.get("workoutLevel").toString();
        String workoutImage = document.getId();

        if(allOrNot) {
            workoutsData.workoutList.add(new workoutsData.workoutVariables(ownerID, title, exerciseTitles, exerciseDescription, workoutLevel, workoutImage));
        }else{
            workoutsData.specoficSearchedWorkoutList.add(new workoutsData.workoutVariables(ownerID, title, exerciseTitles, exerciseDescription, workoutLevel, workoutImage));
        }
    }

    ///////////////////////////////////////////////////////////////
    //                     firebase storage                      //
    ///////////////////////////////////////////////////////////////


    //inserts image to firebase storage
    public void insertImage(Bitmap image,String imageId){
        //imageView.setDrawingCacheEnabled(true);
        //imageView.buildDrawingCache();

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + imageId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    private Uri getUrl(UploadTask uploadTask, final StorageReference imageRef){
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        return null;
    }

    public Bitmap downloadImage(String imageId){
        String path = "images/" + imageId;
        final StorageReference storageReference = storage.getReference();
        final Bitmap[] bitmap = new Bitmap[1];
        storageReference.child(path).getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return bitmap[0];
    }



    ///////////////////////////////////////////////////////////////
    //                      firebase auth                        //
    ///////////////////////////////////////////////////////////////
    public FirebaseAuth getFirebaseAuth(){
        return mAuth;
    }

    //register user
    public void registerUser(final String username, String password, String email){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                UserProfileChangeRequest setDisplayName = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                                user.updateProfile(setDisplayName);
                                addFollowingTable(user.getUid().toString());
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }
    //   |  connected to this function, user to create connected table for followers
    //   |
    //   |
    //   V
    private void addFollowingTable(String id){
        Map<String, Object> followersTable = new HashMap<>();
        followersTable.put("ownerId",id);
        followersTable.put("followingID",(ArrayList<String[]>)null);

        db.collection("followingTable").add(followersTable).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"error adding document" ,e);
            }
        });
    }

    //login function
    public void login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }


    //returns user that is logged in
    public FirebaseUser getLoggedInUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        return user;
    }
}
