package com.example.omerakkus.authexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button kullaniciSil,cikis_yap;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
        kullaniciSil =(Button) findViewById(R.id.kullaniciSil);
        cikis_yap =(Button) findViewById(R.id.cikis_yap);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        final FirebaseUser user  = firebaseAuth.getCurrentUser();
         textView.setText("Merhaba " + user.getDisplayName());

         kullaniciSil.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(user!=null){
                     user.delete()
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         Toast.makeText(getApplicationContext(),"Kullanıcı silindi",Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                                         finish();
                                     }
                                 }
                             });
                 }
             }
         });

         cikis_yap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 firebaseAuth.signOut();
                 startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                 finish();
             }
         });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}