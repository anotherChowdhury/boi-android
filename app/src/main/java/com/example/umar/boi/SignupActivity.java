package com.example.umar.boi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference users;


    EditText emailEditText, passwordEditText, nameEditText;

    Button signupButton;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton = findViewById(R.id.signupButton);

        nameEditText = findViewById(R.id.signupEditText_Name);
        emailEditText = findViewById(R.id.signupEditText_Email);
        passwordEditText = findViewById(R.id.signupEditText_Password);

        firebaseAuth = FirebaseAuth.getInstance();

        users = FirebaseDatabase.getInstance().getReference("Users");


    }


    public void onSignUpClicked(View view) {

        signup(nameEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim()
        );


    }

    private void signup(String name, String email, String password) {

        addUser(name, email, password);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Log.w("SignupActivity", "User registered!");
                        } else {
                            //display some message here
                            Log.e("SignupActivity", "User registration failed!");
                        }

                    }
                });

        startActivity(new Intent(this, LoginActivity.class));

    }

    private void addUser(String name, String email, String password) {

        String id = users.push().getKey();
        Users user = new Users(id, name, email, password);
        users.child(id).setValue(user);

        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
    }
}
