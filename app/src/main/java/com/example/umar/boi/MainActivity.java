package com.example.umar.boi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public final int mainTologin = 12;
    public final int mainTopostbook = 13;
    String uId;

    DatabaseReference database;


    RecyclerView recyclerView;
    bookList Adapter;

    List<Posts> postsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        postsList = new ArrayList<Posts>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// Default Vertical


        database = FirebaseDatabase.getInstance().getReference().child("Posts");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    System.out.println("Asche");
                    Posts post = Snapshot.getValue(Posts.class);
                    postsList.add(post);
                }

                Adapter = new bookList(getApplicationContext(), postsList);
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Hoi Nai", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onloginClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, mainTologin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.postsmenu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchview = (SearchView) searchItem.getActionView();

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    public void onPostClicked(View view) {
        Intent intent = null;
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        else
            intent = new Intent(getApplicationContext(), postBook.class);
        startActivityForResult(intent, mainTologin);
    }
}
