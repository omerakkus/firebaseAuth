package com.example.omerakkus.authexample;

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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by omer.akkus on 4.02.2018.
 */

public class YeniParolaActivity extends AppCompatActivity {
    EditText uyeEmail;
    Button yeniParolaGonder;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yeni_parola_activity);
        uyeEmail = (EditText) findViewById(R.id.uyeEmail);
        yeniParolaGonder = (Button) findViewById(R.id.yeniParolaGonder);

        firebaseAuth = FirebaseAuth.getInstance();

        yeniParolaGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uyeEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill e-mail",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Mail adresinize gönderidli",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Mail gönderme hatası",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}
