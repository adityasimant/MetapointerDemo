package com.example.metapointerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.metapointerdemo.databinding.ActivityAddMoneyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMoney extends AppCompatActivity {

    ActivityAddMoneyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoneyToDatabase();

            }
        });
    }

    private void addMoneyToDatabase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String number = currentUser.getPhoneNumber();
            String num = number.substring(3, number.length());
            Log.d("Aditya", num);

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(num);
            // Get the amount from the EditText
            String enteredAmount = binding.etAddMoney.getText().toString().trim();

            if (!TextUtils.isEmpty(enteredAmount)) {
                // Retrieve the current amount from the database
                userRef.child("amount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String currentAmount = dataSnapshot.getValue(String.class);

                            int n1= Integer.parseInt(currentAmount);
                            Log.d("Current", currentAmount);
                            int n2 = Integer.parseInt(enteredAmount);
                            Log.d("Entered", enteredAmount);
                            int result = n1+n2;
                            String newAmount = String.valueOf(result);

                            userRef.child("amount").setValue(newAmount);
                            Toast.makeText(AddMoney.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddMoney.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddMoney.this, "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Entered Amount is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}