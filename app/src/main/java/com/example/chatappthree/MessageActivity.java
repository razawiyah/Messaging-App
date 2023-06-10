package com.example.chatappthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatappthree.modelclass.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    String id;
    View toolbar;
    TextView username;
    ImageView back,menu;
    public SignOut dialog;
    CircleImageView profile_image;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        id = getIntent().getStringExtra("userId");

        toolbar = findViewById(R.id.toolbar);
        username = toolbar.findViewById(R.id.username);
        back = toolbar.findViewById(R.id.backBtn);
        profile_image = toolbar.findViewById(R.id.profile_image);

        dialog = new SignOut(this);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
                finish();
            }
        });

        Query query = databaseReference.child("User").child(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user  = snapshot.getValue(UserModel.class);
                String imageUrl = user.getImageUrl();
                String name = user.getName();
                username.setText(name);

                if(imageUrl.equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(MessageActivity.this).load(imageUrl).into(profile_image);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MessageActivity.this, MainActivity.class));
        finish();
    }
}