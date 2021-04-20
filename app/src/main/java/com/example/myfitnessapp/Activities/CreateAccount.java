package com.example.myfitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CreateAccount extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btn_Login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        etEmail = findViewById(R.id.edt_user_name);
        etPassword = findViewById(R.id.edt_password);
        btn_Login = findViewById(R.id.btn_sign_in);
        findViewById(R.id.txtNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(CreateAccount.this,Login.class));
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
                    Toast.makeText( CreateAccount.this,  "Fill up all the entries", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    final ProgressDialog pb=new ProgressDialog(CreateAccount.this);
                    pb.setMessage("Creating Account..");
                    pb.show();
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    pb.dismiss();
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(CreateAccount.this,  "Account Created Successfully",Toast.LENGTH_LONG ).show();
                                        finish();
                                        startActivity(new Intent(CreateAccount.this, MainActivity.class));

                                    }
                                    else
                                    {
                                        Toast.makeText(CreateAccount.this,  "Created Account  failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }

}
