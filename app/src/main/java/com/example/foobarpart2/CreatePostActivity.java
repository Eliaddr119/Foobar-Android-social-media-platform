package com.example.foobarpart2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {

    private EditText editTextPostContent;
    private Button buttonSubmitPost;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 100;
    private Button btnUpic;
    private ImageView photo;
    private Uri photoUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        btnUpic = findViewById(R.id.btn_upload_pic_post);
        editTextPostContent = findViewById(R.id.editTextPostContent);
        buttonSubmitPost = findViewById(R.id.buttonSubmitPost);
        photo = findViewById(R.id.post_pic);

        buttonSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        btnUpic.setOnClickListener(v -> showImageSourceDialog());
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
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        photoUri = null;
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                photoUri = data.getData();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                String savedImagePath = storeImageBitmap(imageBitmap);
                photoUri = Uri.parse("file://" + savedImagePath);
                photo.setImageBitmap(imageBitmap);
            } else{
                photo.setImageBitmap(null);
                Toast.makeText(this, "Failed to retrieve image"
                        , Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Set the selected image URI to the ImageView
                photoUri = selectedImageUri;
                photo.setImageURI(selectedImageUri);
            } else {
                photo.setImageBitmap(null);
                Toast.makeText(this, "Failed to retrieve image"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String storeImageBitmap(Bitmap imageBitmap) {
        // Create a unique file name
        String fileName = "temp_image_" + System.currentTimeMillis() + ".jpg";

        // Get the external storage directory
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the file
        File imageFile = new File(storageDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if saving failed
        }

        // Return the saved image path
        return imageFile.getAbsolutePath();
    }
    private void submitPost() {
        String content = editTextPostContent.getText().toString().trim();
        if (!content.isEmpty()) {
            UserManager userManager = UserManager.getInstance();
            User user = userManager.getUserByUsername(userManager.getCurrentUser());
            Uri profileImage = user != null ? user.getProfileImage() : null;

            Intent returnIntent = new Intent();
            returnIntent.putExtra("author", user.getDisplayName());
            returnIntent.putExtra("content", content);
            if(photoUri !=null) {
                returnIntent.putExtra("picResource", photoUri.toString());
            }else {
                returnIntent.putExtra("picResource", "null");
            }
            returnIntent.putExtra("profileUri", profileImage.toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

}
