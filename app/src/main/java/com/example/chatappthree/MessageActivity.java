package com.example.chatappthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatappthree.modelclass.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,userId;
    View toolbar;
    TextView username;
    ImageView back;
    CircleImageView profile_image;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ImageButton send_btn;
    EditText text_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        userId = getIntent().getStringExtra("userId");

        send_btn = findViewById(R.id.send_btn);
        text_send = findViewById(R.id.text_send);


        toolbar = findViewById(R.id.toolbar);
        username = toolbar.findViewById(R.id.username);
        back = toolbar.findViewById(R.id.backBtn);
        profile_image = toolbar.findViewById(R.id.profile_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
                finish();
            }
        });

        Query query = databaseReference.child("User").child(userId);
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

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString().trim();
                if(!msg.equals("")){
                    sendMessage(id,userId,msg);
                }else {
                    errorPopup();
                }
                text_send.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MessageActivity.this, MainActivity.class));
        finish();
    }

    private void sendMessage(String sender, String reciever, String message){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciever",reciever);
        hashMap.put("message",message);

        databaseReference.child("Chats").setValue(hashMap);
    }

    private void errorPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cannot send empty text!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirect to the homepage
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}