package be.kuleuven.gt.app3.storeAndupdate;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.ForNote.NoteUnit;

public class getDataFromDB {
    private RequestQueue requestQueue;

    public interface DataCallback{
        void onSuccess(FriendUnit friendUnit);
        void onError(String error);
    }

    public void getUserInfo(Context context,String account,DataCallback dataCallback)
    {
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23pt315/getUser/" + account;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            String name="";
                            String icon="";
                            String account="";
                            int id = 0;
                            for( int i = 0; i < response.length(); i++ )
                            {
                                JSONObject curObject = response.getJSONObject( i );
                                id = curObject.getInt("id_user");
                                name = curObject.getString("name_user");
                                icon = curObject.optString("icon_user", null); // Handle null values
                                account = curObject.getString("account_user");



                            }
                            FriendUnit friend = new FriendUnit();
                            friend.setName(name);
                            friend.setID(id);
                            friend.setAccount(account);
                            dataCallback.onSuccess(friend);
                        }
                        catch( JSONException e )
                        {
                            Log.e( "Database", e.getMessage(), e );
                            dataCallback.onError("error");
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        dataCallback.onError("error");
                    }
                }
        );

        requestQueue.add(submitRequest);
    }

    public interface LoginCallback {
        void onSuccess(int onlineID);
        void onFailure();
    }

    public void Login(Context context,String account,String password,LoginCallback callback){
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23pt315/getUser/" + account;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean isAuthenticated = false;
                            int ID = 0 ;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                String account_user = curObject.getString("account_user");
                                String password_user = curObject.getString("password_user");
                                ID = curObject.getInt("id_user");
                                Log.i("taggg",account_user+password_user+ID);
                                if (account_user.equals(account) && password_user.equals(password)) {
                                    isAuthenticated = true;
                                    break;
                                }
                            }

                            if (isAuthenticated) {
                                callback.onSuccess(ID);
                            } else {
                                callback.onFailure();
                            }
                        } catch (JSONException e) {
                            Log.e("taggg", e.getMessage(), e);
                            callback.onFailure();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("taggg", "Login error", error);
                        callback.onFailure();
                    }
                }
        );

        requestQueue.add(submitRequest);
    }



    public void Register(Context context,String name,String account,String password){

        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23pt315/register/"+name+"/"+password+"/"+account;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.i("taggg", "sucess");
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("taggg", "Error: " + error.toString());

                    }
                }
        );

        requestQueue.add(submitRequest);
    }

    public void addFriend(Context context,int friendID,int userID){
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23PT315/addFriend/"+userID+"/"+friendID;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.i("taggg", "sucess");
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("taggg", "Error: " + error.toString());

                    }
                }
        );

        requestQueue.add(submitRequest);
    }

    public interface infoBack{
        void onSuccess(ArrayList<FriendUnit> friends);
        void onError(String error);
    }

    public void getUserFullinfo(Context context,int onlineID,infoBack back){
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23PT315/getinfoUser/"+onlineID;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            ArrayList<FriendUnit> friends = new ArrayList<>();
                            String name="";
                            String icon="";
                            String account="";
                            int id = 0;
                            for( int i = 0; i < response.length(); i++ )
                            {
                                JSONObject curObject = response.getJSONObject( i );
                                id = curObject.getInt("id_user");
                                name = curObject.getString("name_user");
                                icon = curObject.optString("icon_user", null); // Handle null values
                                account = curObject.getString("account_user");
                                FriendUnit friend = new FriendUnit();
                                friend.setName(name);
                                friend.setID(id);
                                friend.setAccount(account);
                                friends.add(friend);
                            }

                            back.onSuccess(friends);
                        }
                        catch( JSONException e )
                        {
                            Log.e( "Database", e.getMessage(), e );
                            back.onError("error");
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("taggg", "Error: " + error.toString());

                    }
                }
        );

        requestQueue.add(submitRequest);


    }

    public void shareNote(Context context,int localID, int recieverId, NoteUnit note){

        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23PT315/shareNote/"+localID+"/"+recieverId+"/"+note.getTitle()+"/"+note.getContent();
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.i("taggg", "Send！");
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("taggg", "Error: " + error.toString());

                    }
                }
        );

        requestQueue.add(submitRequest);

    }

    public interface receiveBack{
        void onSuccess(NoteUnit noteUnit);
        void onError(String error);

    }

    public void receiveNote(Context context,int localID,receiveBack receiveBack){
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23PT315/receiveNote/"+localID;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            String title="";
                            String context="";
                            for( int i = 0; i < response.length(); i++ )
                            {
                                JSONObject curObject = response.getJSONObject( i );
                                title = curObject.getString("note_title");
                                context = curObject.getString("note_context");
                            }

                            NoteUnit note = new NoteUnit();
                            note.setContent(context);
                            note.setTitle(title);
                            receiveBack.onSuccess(note);
                        }
                        catch( JSONException e )
                        {
                            Log.e( "Database", e.getMessage(), e );
                            receiveBack.onError("error");
                        }

                        Log.i("taggg", "receive!！");
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("taggg", "Error: " + error.toString());

                    }
                }
        );

        requestQueue.add(submitRequest);
    }


}
