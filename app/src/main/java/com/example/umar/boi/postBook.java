package com.example.umar.boi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.umar.boi.api.network.GoodreadsScrapper;
import com.example.umar.boi.api.service.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class postBook extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    DatabaseReference posts;
    String uid, author, description, name, reviews, price;
    String rating;
    EditText bookNameEditText, priceEditText;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private String link;
    private String imageUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_book);


        btnChoose = findViewById(R.id.picSelectButton);
        imageView = findViewById(R.id.imageView2);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        Intent intent = getIntent();
        uid = intent.getStringExtra("id");

        bookNameEditText = findViewById(R.id.bookNameEditText);
        priceEditText = findViewById(R.id.priceEditText);


        posts = FirebaseDatabase.getInstance().getReference("Posts");
        storageReference = FirebaseStorage.getInstance().getReference();


    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onPostClicked(View view) {
        uploadImage();
//        getInfo();

        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            imageUid = UUID.randomUUID().toString();

            final StorageReference ref = storageReference.child(imageUid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    link = uri.toString();
                                    Log.d("postBook::uploadImage", link);
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                    getInfo();
                                }
                            });
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void getInfo() {

        name = bookNameEditText.getText().toString();
        Call<List<Book>> books = GoodreadsScrapper.getBookList(name);

        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> bookList = response.body();
                /*for (Book b : bookList) {
                    name = b.getTitle();
                    rating = b.getRating();
                    author = b.getAuthor();
                    break;
                }*/
                Book firstBook = bookList.get(0);
                Log.d("postBook::getInfo", "Response Successful!");
                Log.i("postBook::getInfo:fBook", firstBook.toString());

                price = priceEditText.getText().toString();
                addPosts(firstBook.getTitle(), price, link, firstBook.getAuthor(), firstBook.getRating());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("postBook::getInfo", "Response Error!");
            }
        });
    }

    private void addPosts(String name, String price, String imageUrl, String author, String rating) {
        Posts post = new Posts(name, price, imageUrl, author, rating);
        Log.d("postBook::addPosts", post.toString());
        String id = posts.push().getKey();
        posts.child(id).setValue(post);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}
