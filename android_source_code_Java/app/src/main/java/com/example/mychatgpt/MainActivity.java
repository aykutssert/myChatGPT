package com.example.mychatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    EditText editText;
    ImageButton imageButton;

    ArrayList<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        textView = findViewById(R.id.welcome_text);
        editText = findViewById(R.id.message_edit_text);
        imageButton = findViewById(R.id.send_btn);

        //setup recylcer view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);


        imageButton.setOnClickListener((v) ->{
            String question = editText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            editText.setText("");
            callApi(question);
            textView.setVisibility(View.GONE);
        });
    }
    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //this thing will change the UI
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }
    void addResponse(String string){
        messageList.remove(messageList.size()-1);
        addToChat(string,Message.SENT_BY_BOT);
    }
    void callApi(String question){
        //okhttp
        messageList.add(new Message("Typing...",Message.SENT_BY_BOT));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model","gpt-3.5-turbo");
            jsonObject.put("prompt",question);
            JSONArray messagesArray = new JSONArray();
            JSONObject messageObject = new JSONObject();
            messageObject.put("role","user");
            messageObject.put("content",question);
            messagesArray.put(messageObject);

            jsonObject.put("messages",messagesArray);
            jsonObject.put("temperature", 0.7);
        }
        catch (JSONException e){
            e.printStackTrace();

        }
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer YOUR_API_KEY")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            addResponse("OnFailure Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if(response.isSuccessful()){


                try {
                    String responseBodyString = response.body().string();

                    JSONObject jsonObject1 = new JSONObject(responseBodyString);
                    JSONArray choicesArray = jsonObject1.getJSONArray("choices");


                    JSONObject firstChoice = choicesArray.getJSONObject(0);
                    JSONObject messageObject = firstChoice.getJSONObject("message");


                    String content = messageObject.getString("content");
                    addResponse(content.trim());
                }
                catch (JSONException e){
                        e.printStackTrace();
                }

            }
            else{
                addResponse("OnResponse Failed to load response due to "+response.body().string());
            }
            }
        });
    }
}

