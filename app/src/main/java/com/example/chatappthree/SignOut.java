package com.example.chatappthree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOut extends Dialog implements View.OnClickListener {

    public Context c;
    public Dialog d;
    public CardView yes, no;
    public FirebaseAuth mAuth;
    public FirebaseUser user;

    public SignOut(@NonNull Context context) {
        super(context);

        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_out);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        yes =  findViewById(R.id.yesBtn);
        no =  findViewById(R.id.noBtn);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (user != null){
            int viewId = v.getId();

            if (viewId == R.id.yesBtn) {
                if (user != null) {
                    mAuth.signOut();
                    Intent i = new Intent(c, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    c.startActivity(i);
                }
            } else if (viewId == R.id.noBtn) {
                dismiss();
            }
        }
        dismiss();
    }

    public SignOut(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SignOut(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}