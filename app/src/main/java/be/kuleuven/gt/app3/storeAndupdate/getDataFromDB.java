package be.kuleuven.gt.app3.storeAndupdate;

import android.content.Context;
import android.util.Log;
import android.view.View;

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

public class getDataFromDB {
    private RequestQueue requestQueue;

    public interface DataCallback{
        void onSuccess(String data);
        void onError(String error);
    }

    public void getGroupJson(Context context,String account,DataCallback dataCallback)
    {
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a20sd313/peopleWithEmail";

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            String responseString = "";
                            for( int i = 0; i < response.length(); i++ )
                            {
                                JSONObject curObject = response.getJSONObject( i );
                                responseString += curObject.getString( "name" ) + " : " + curObject.getString( "email" ) + "\n";
                            }
                            dataCallback.onSuccess(responseString);
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
                        dataCallback.onError(error.getLocalizedMessage());
                    }
                }
        );

        requestQueue.add(submitRequest);
    }

    public void Login(Context context,String account,String password){
        requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a23pt315/getUser/" + account;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean isAuthenticated = false;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject curObject = response.getJSONObject(i);
                                String name_user = curObject.getString("name_user");
                                String password_user = curObject.getString("password_user");
                                Log.i("taggg",name_user+password_user);
                                if (name_user.equals(account) && password_user.equals(password)) {
                                    isAuthenticated = true;
                                    break;
                                }
                            }


                        } catch (JSONException e) {
                            Log.e("taggg", e.getMessage(), e);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("taggg", "Login error", error);

                    }
                }
        );

        requestQueue.add(submitRequest);

    }

    public boolean Register(String account,String password){
        boolean result = false;


        return result;
    }

    public void addFriend(String FriendAccount){







    }

    public void createTable(){




        
    }

}
