package com.example.metapointerdemo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_rv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.senderPhoneTextView.setText(transaction.getSenderPhone());
        holder.receiverPhoneTextView.setText(transaction.getReceiverPhone());
        holder.amountTextView.setText(String.valueOf(transaction.getAmount()));
        holder.timeTextView.setText(transaction.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderPhoneTextView;
        TextView receiverPhoneTextView;
        TextView amountTextView;
        TextView timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            senderPhoneTextView = itemView.findViewById(R.id.rv_sender_phone);
            receiverPhoneTextView = itemView.findViewById(R.id.rv_reciever_phone);
            amountTextView = itemView.findViewById(R.id.rv_amount);
            timeTextView = itemView.findViewById(R.id.rv_time);
        }
    }
}
