package se.ju.student.saro1718.workout4everyone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
            Toast.makeText(this, getText(R.string.Enter_email_please), Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(username)){
            Toast.makeText(this, getText(R.string.Enter_a_username_please), Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, getText(R.string.Enter_a_password_please), Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, getText(R.string.Repeat_password_please), Toast.LENGTH_SHORT).show();
        } else if(!password.equals(password2)){
            Toast.makeText(this, getText(R.string.Password_must_match), Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getText(R.string.Registering_user));
            progressDialog.show();
            progressDialog.setCancelable(false);
            database.registerUser(username, password, email,this);
        }
    }


    public void verifiyRegistration(boolean success,Exception e){
        if(success){
            progressDialog.cancel();
            Toast.makeText(this, getText(R.string.Success_creating_account), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            progressDialog.cancel();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
