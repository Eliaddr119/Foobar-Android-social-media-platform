package com.example.foobarpart2;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foobarpart2.databinding.ActivitySignInBinding;


public class SignIn extends AppCompatActivity {
    private ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignIn.setOnClickListener(v -> {
            UserManager userManager = UserManager.getInstance();

            String userName = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            if(userManager.authenticate(userName,password)){
                userManager.setCurrentUser(userName);
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            }else if(!userName.isEmpty() && !password.isEmpty()) {
                Toast.makeText(this, "Incorrect username or password"
                        , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill in all fields"
                        , Toast.LENGTH_SHORT).show();
            }
        });

        binding.retSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });
    }
}