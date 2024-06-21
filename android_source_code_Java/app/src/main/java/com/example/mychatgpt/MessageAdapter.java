package com.example.mychatgpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<Message> messageList;
    public MessageAdapter(List<Message> list) {
        this.messageList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Message message = messageList.get(position);
    if(message.getSentBy().equals(Message.SENT_BY_ME)){
        holder.leftchatView.setVisibility(View.GONE);
        holder.rightchatView.setVisibility(View.VISIBLE);
        holder.righttextView.setText(message.getMessage());
    }
    else{
        holder.rightchatView.setVisibility(View.GONE);
        holder.leftchatView.setVisibility(View.VISIBLE);
        holder.lefttextView.setText(message.getMessage());
    }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout leftchatView,rightchatView;
        TextView lefttextView,righttextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftchatView = itemView.findViewById(R.id.left_chat_view);
            rightchatView =itemView.findViewById(R.id.right_chat_view);
            lefttextView = itemView.findViewById(R.id.left_chat_text_view);
            righttextView = itemView.findViewById(R.id.right_chat_text_view);
        }
    }


}
