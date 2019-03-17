package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.InputStream;

public class policyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_terms);
        TextView policyTextView = (TextView) findViewById(R.id.policy_activity_policyTextView);
        String policyText = "";
        try{
            InputStream policyRead = getAssets().open("termsfeed-privacy-policy-text-english.txt");
            int size = policyRead.available();
            byte[] buffer = new byte[size];
            policyRead.read(buffer);
            policyRead.close();
            policyText = new String(buffer);
        } catch (Exception e){
            policyText = getResources().getString(R.string.policyCouldntLoad);
        }
        policyTextView.setText(policyText);
    }

}
