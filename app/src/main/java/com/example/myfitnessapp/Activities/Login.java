package com.example.myfitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfitnessapp.MainActivity;
import com.example.myfitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login  extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btn_Login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.edt_user_name);
        etPassword = findViewById(R.id.edt_password);
        btn_Login = findViewById(R.id.btn_sign_in);
        findViewById(R.id.txtNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(Login.this,CreateAccount.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();



        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (email.isEmpty()||password.isEmpty())
                {
                    Toast.makeText( Login.this,  "Fill up all the entries", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    final ProgressDialog pb=new ProgressDialog(Login.this);
                    pb.setMessage("Authenticating..");
                    pb.show();
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pb.dismiss();
                                    if (task.isSuccessful())
                                    {
                                       finish();
                                       startActivity(new Intent(Login.this, MainActivity.class));

                                    }
                                    else
                                        {
                                        Toast.makeText(Login.this,  "Login failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                      }

                                }
                            });
                }
            }
        });
    }

}
