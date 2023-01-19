package com.example.ggaming_frontend;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggaming_frontend.models.PaymentCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button registerBtn;
    EditText usernameEditText, ageEditText, countryEditText, emailEditText, passwordEditText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        linkViewElements();
        setRegisterBtnAction();
        setMoveToLoginBtnAction();
    }

    private void setMoveToLoginBtnAction() {
        TextView signIn = (TextView) findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setRegisterBtnAction() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //Check if username and password fields are empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, Constants.ToastMessage.emptyInputError, Toast.LENGTH_SHORT).show();
                    return;
                }

                //Call FireStoreAuth for authentication process
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //If successful
                            Toast.makeText(SignUpActivity.this, Constants.ToastMessage.registerSuccess, Toast.LENGTH_SHORT).show();
                            try {
                                saveUserInfo();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            moveToLoginActivity(); // go to login activity
                        } else {
                            Toast.makeText(SignUpActivity.this, Constants.ToastMessage.registerFailure,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //Save user data to 'users' collection on firebase
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveUserInfo() throws ParseException {
        String username = usernameEditText.getText().toString();
        Integer age = Integer.parseInt(ageEditText.getText().toString());
        String country = countryEditText.getText().toString();
        String email = emailEditText.getText().toString();

        //Create data hashmap to push to FireStore db
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.FSUser.usernameField, username);
        data.put(Constants.FSUser.ageField, age);
        data.put(Constants.FSUser.countryField, country);
        data.put(Constants.FSUser.emailField, email);
        data.put(Constants.FSUser.paymentCardsField, new ArrayList<PaymentCard>());
        db.collection(Constants.FSUser.userCollection).add(data);
    }

    //Redirect to LoginActivity
    private void moveToLoginActivity() {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    //Get View variables from xml id
    private void linkViewElements() {
        registerBtn = (Button) findViewById(R.id.registerBtn);
        usernameEditText = (EditText) findViewById(R.id.singupUsernameEditText);
        ageEditText = (EditText) findViewById(R.id.signupAgeEditText);
        countryEditText = (EditText) findViewById(R.id.signupCountryEditText);
        emailEditText = (EditText) findViewById(R.id.signupEmailEditText);
        passwordEditText = (EditText) findViewById(R.id.signupPasswordEditText);
    }
}