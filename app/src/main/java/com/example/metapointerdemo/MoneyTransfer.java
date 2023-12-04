package com.example.metapointerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.metapointerdemo.databinding.ActivityMoneyTransferBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoneyTransfer extends AppCompatActivity {

    ActivityMoneyTransferBinding binding;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoneyTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferMoney();
            }
        });
    }

    private void transferMoney() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String senderPhone = currentUser.getPhoneNumber();
            String receiverPhone = binding.etRecieverPhone.getText().toString().trim();
            String amountStr = binding.etSendAmount.getText().toString().trim();

            if (TextUtils.isEmpty(receiverPhone) || TextUtils.isEmpty(amountStr)) {
                Toast.makeText(this, "Please enter receiver's phone number and amount", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount = Integer.parseInt(amountStr);

            // Reference to the sender's data
            String number = senderPhone;
            String num = number.substring(3, number.length());
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(num);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int senderBalance = Integer.parseInt(dataSnapshot.child("amount").getValue(String.class));

                        if (senderBalance >= amount) {
                            // Deduct the amount from sender's balance
                            int newSenderBalance = senderBalance - amount;
                            userRef.child("amount").setValue(String.valueOf(newSenderBalance));

                            // Reference to the receiver's data
                            DatabaseReference receiverRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverPhone);

                            receiverRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot receiverSnapshot) {
                                    if (receiverSnapshot.exists()) {
                                        int receiverBalance = Integer.parseInt(receiverSnapshot.child("amount").getValue(String.class));

                                        // Add the amount to receiver's balance
                                        int newReceiverBalance = receiverBalance + amount;
                                        receiverRef.child("amount").setValue(String.valueOf(newReceiverBalance));

                                        Toast.makeText(MoneyTransfer.this, "Money transferred successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MoneyTransfer.this, "Receiver not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(MoneyTransfer.this, "Error fetching receiver data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(MoneyTransfer.this, "Insufficient balance", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MoneyTransfer.this, "Sender not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MoneyTransfer.this, "Error fetching sender data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}