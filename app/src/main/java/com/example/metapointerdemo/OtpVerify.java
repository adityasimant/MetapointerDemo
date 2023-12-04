package com.example.metapointerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.metapointerdemo.databinding.ActivityOtpVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class OtpVerify extends AppCompatActivity {

    ActivityOtpVerifyBinding binding;
    public String verificationId;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextInput();

        verificationId = getIntent().getStringExtra("verificationId");
        String name = getIntent().getStringExtra("Name");
        String email = getIntent().getStringExtra("Email");
        String phone = getIntent().getStringExtra("phone");
        String amount = getIntent().getStringExtra("Amount");

        database = FirebaseDatabase.getInstance();

        binding.btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar2.setVisibility(View.VISIBLE);
                if (binding.etc1.getText().toString().trim().isEmpty() ||
                        binding.etc2.getText().toString().trim().isEmpty() ||
                        binding.etc3.getText().toString().trim().isEmpty() ||
                        binding.etc4.getText().toString().trim().isEmpty() ||
                        binding.etc5.getText().toString().trim().isEmpty() ||
                        binding.etc6.getText().toString().trim().isEmpty() ){
                    Toast.makeText(OtpVerify.this, "Required Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (verificationId != null){
                        String code = binding.etc1.getText().toString().trim() +
                                binding.etc2.getText().toString().trim() +
                                binding.etc3.getText().toString().trim() +
                                binding.etc4.getText().toString().trim() +
                                binding.etc5.getText().toString().trim() +
                                binding.etc6.getText().toString().trim() ;

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth .getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    binding.progressbar2.setVisibility(View.VISIBLE);
                                    UserModel user = new UserModel(name, phone, email, amount);

                                    // TODO: Add PhoneNumber as the parent data so it becomes easier to carry out transactions.
                                    // Database Insertion

                                    database.getReference().child("Users").child(phone).setValue(user);
                                    Toast.makeText(OtpVerify.this, "User Created", Toast.LENGTH_SHORT).show();
                                    // Database Ends
                                    Intent intent = new Intent(OtpVerify.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    binding.progressbar2.setVisibility(View.GONE);
                                    Toast.makeText(OtpVerify.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    private void editTextInput() {
        binding.etc1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etc2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etc3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etc3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etc4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etc4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etc5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etc5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etc6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}