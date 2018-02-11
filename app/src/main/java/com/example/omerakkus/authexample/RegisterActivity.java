package com.example.omerakkus.authexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by omer.akkus on 4.02.2018.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText uyeEmail,uyeParola;
    Button yeniUyeButton,uyeGirisButton;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        uyeEmail = (EditText) findViewById(R.id.uyeEmail);
        uyeParola = (EditText) findViewById(R.id.uyeParola);
        yeniUyeButton = (Button) findViewById(R.id.yeniUyeButton);
        uyeGirisButton = (Button) findViewById(R.id.uyeGirisButton);

        firebaseAuth = FirebaseAuth.getInstance();

        yeniUyeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uyeEmail.getText().toString();
                String parola = uyeParola.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Lütfen E-mail Giriniz!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(parola)){
                    Toast.makeText(getApplicationContext(),"Lütfen PArola Giriniz!",Toast.LENGTH_SHORT).show();
                }

                if(parola.length()<6){
                    Toast.makeText(getApplicationContext(),"Parola en az 6 karakter olmalı",Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(email,parola)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Sorun oluştu",Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });
            }
        });

        uyeGirisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
