package com.example.umar.boi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// https://medium.com/mobiletech/firebase-authentication-sample-371b5940ba93

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView signupTextView;
    FirebaseAuth firebaseAuth;
    EditText emailEditText, passwordEditText;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        signupTextView = findViewById(R.id.signupTextView);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onSignUpClicked(View view) {

        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);

    }

    public void onLoginClicked(View view) {
        signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            id = user.getUid();
                            Toast.makeText(getApplicationContext(), "Auth success! User: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), postBook.class).putExtra("id", id));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
