package be.kuleuven.gt.app3.storeAndupdate;

import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import java.io.IOException;

public class polish {
    //enter the API key
    private static final String OPENAI_API_KEY = "";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public polish(){

    }

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface PolishCallback {
        void onSuccess(String polishedText);
        void onFailure(String errorMessage);
    }

    public void polishText(String text, PolishCallback callback) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
            messages.put(new JSONObject().put("role", "user").put("content", "Polish the following text: " + text));
            jsonObject.put("messages", messages);
            jsonObject.put("max_tokens", 100);
        } catch (JSONException e) {
            e.printStackTrace();
            mainHandler.post(() -> callback.onFailure("JSON error: " + e.getMessage()));
            Log.i("taggg","JSON error: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray choices = jsonResponse.getJSONArray("choices");
                        String polishedText = choices.getJSONObject(0).getJSONObject("message").getString("content");
                        mainHandler.post(() -> callback.onSuccess(polishedText.trim()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mainHandler.post(() -> callback.onFailure("Failed to parse response: " + e.getMessage()));
                        Log.i("taggg","Failed to parse response: " + e.getMessage());
                    }
                } else {
                    String errorResponse = response.body() != null ? response.body().string() : "Unknown error";
                    mainHandler.post(() -> callback.onFailure("Failed to polish text: " + response.message() + ". Response: " + errorResponse));
                    Log.i("taggg","Failed to polish text: " + response.message() + ". Response: " + errorResponse);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.i("taggg",e.getMessage());
                mainHandler.post(() -> callback.onFailure("Network error: " + e.getMessage()));
            }
        });
    }

}
