package com.example.foobarpart2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foobarpart2.databinding.ActivitySignUpBinding;
import kotlin.text.Regex;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.uploadPhotoBtn.setOnClickListener(v -> showImageSourceDialog());

        binding.retSignIn.setOnClickListener(v -> {
            Intent i = new Intent(this,SignIn.class);
            startActivity(i);
        });

        binding.btnSignUp.setOnClickListener(v -> {

            String userName = binding.usernameTextSignup.getText().toString();
            String password = binding.passwordTextSignup.getText().toString();
            String confirmPassword = binding.confirmPasswordTextSignup.getText().toString();
            String displayName = binding.displayNameTextSignup.getText().toString();

            if (validateInput(userName,password,confirmPassword,displayName)){
                Intent i = new Intent(this, SignIn.class);
                startActivity(i);
            }
        });


    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new String[]{"Gallery", "Camera"}, (dialog, which) -> {
            if (which == 0) {
                // Open gallery
                openGallery();
            } else if (which == 1) {
                // Open camera
                openCamera();
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("SignUp", "Exception occurred: " + e.getMessage(), e);
        }
    }
    private void openCamera() {
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }catch (Exception e){
            Toast.makeText(this, "no camera", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                binding.profileImage.setImageBitmap(imageBitmap);
            } else {
                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Set the selected image URI to the ImageView
                binding.profileImage.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean validateInput(String username, String password, String confirmPassword,
                          String displayName){
        if(username.isEmpty() || password.isEmpty()
            || confirmPassword.isEmpty() || displayName.isEmpty()){
            Toast.makeText(this, "Please fill in all fields"
                    , Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not match"
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        Regex passwordRegex = new Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$");
        if(!passwordRegex.matches(password)){
            Toast.makeText(this,
                    "Password must be at least 8 characters with uppercase, " +
                            "lowercase, and numeric characters"
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}