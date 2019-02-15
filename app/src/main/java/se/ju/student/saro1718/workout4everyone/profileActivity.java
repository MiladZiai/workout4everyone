package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class profileActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;

    private Button logInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);

        logInButton = (Button) findViewById(R.id.sign_in_button);



    }

    public void signInButtonClicked(View view){

    }

    private void startSignIn(){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();


    }


}
