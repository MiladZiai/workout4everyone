package se.ju.student.saro1718.workout4everyone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.BitSet;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class registerUserActivity extends AppCompatActivity{

 
    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText repeatPasswordInput;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mAuth = database.getFirebaseAuth();
        progressDialog = new ProgressDialog(this);
        emailInput = (EditText) findViewById(R.id.email_input);
        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        repeatPasswordInput = (EditText) findViewById(R.id.password2_input);

    }

    public void registerButtonClicked(View view){

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String password2 = repeatPasswordInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email please!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter a username please!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter a password please!", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, "Repeat password please!", Toast.LENGTH_SHORT).show();
        } else if(!password.equals(password2)){
            Toast.makeText(this, "Password must match!", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            database.registerUser(username, password, email,this);
        }
    }


    public void verifiyRegistration(boolean state,Exception e){
        if(state){
            progressDialog.cancel();
            Toast.makeText(this, "Success creating account", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            progressDialog.cancel();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
