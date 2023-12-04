package com.example.metapointerdemo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metapointerdemo.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        binding.btnlogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        binding.paycard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MoneyTransfer.class);
            startActivity(intent);
        });

        binding.addMoneyCard.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMoney.class);
            startActivity(intent);
        });

        if (currentUser != null) {
            fetchUserData(currentUser.getPhoneNumber());
        }
    }

    private void fetchUserData(String phone) {

        String number = phone;
        String num = number.substring(3, number.length());
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(num);
        Log.d("aditya", phone);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String amount = dataSnapshot.child("amount").getValue(String.class);
                    Log.d("FirebaseData", "Amount: " + amount);

                    if (amount != null) {
                        binding.tvAmountBalanceMain.setText(amount);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
