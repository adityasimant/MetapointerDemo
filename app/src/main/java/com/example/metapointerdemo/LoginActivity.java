package com.example.metapointerdemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.metapointerdemo.databinding.ActivityLoginBinding;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etphnologin.getText().toString().trim().isEmpty()
                ||binding.etphnologin.getText().toString().trim().length() != 10 ||
                binding.etloginName.getText().toString().isEmpty() ||
                binding.etloginEmail.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Invalid Data Provided", Toast.LENGTH_SHORT).show();
                }
                else{
                    otpSend();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser!=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void otpSend() {

        binding.progressBar.setVisibility(View.VISIBLE);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, OtpVerify.class);
                intent.putExtra("phone", binding.etphnologin.getText().toString());
                intent.putExtra("verificationId", verificationId);
                intent.putExtra("Name", binding.etloginName.getText().toString());
                intent.putExtra("Email", binding.etloginEmail.getText().toString());
                intent.putExtra("Amount", binding.etloginAmt.getText().toString());
                startActivity(intent);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+ binding.etphnologin.getText().toString().trim() )     // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}