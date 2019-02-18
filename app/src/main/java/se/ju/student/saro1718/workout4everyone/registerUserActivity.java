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

    public void registerButtonClickedc(View view){
        System.out.println("1");
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email please!", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password please!", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        }else {
            database.registerUser(username, password, email, this);
        }
    }
    public void registerButtonClicked(){


    }

}
