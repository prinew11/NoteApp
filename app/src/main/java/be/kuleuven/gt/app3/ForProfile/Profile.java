package be.kuleuven.gt.app3.ForProfile;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.getDataFromDB;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;


public class Profile extends Fragment {

    private Toolbar toolbar;
    private View view;

    private TextInputLayout usernameLayout, passwordLayout;
    private TextInputEditText usernameEditText, passwordEditText;
    private TextView registerTextView;
    private Button loginButton, registerButton;
    private TextInputLayout registerUsernameLayout, registerPasswordLayout,registerAccountLayout;
    private TextInputEditText registerUsernameEditText, registerPasswordEditText,registerAccountEditText;
    private getDataFromDB getDataFromDB;
    private storeNote storeNote;
    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ONLINEID = "onlineID";

    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance() {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("taggg","tesst1");
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initData();

        if (isLoggedIn()) {
            // Perform actions as needed, such as navigating to the user's profile page
            String username = getUsername();
            int online = getOnlineID();
            Toast.makeText(getContext(), "Welcome back, " + online + "!", Toast.LENGTH_SHORT).show();
        }


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRegistrationForm();
            }
        });

        // Set up the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Set up the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        return view;
    }

    public void initData(){
        toolbar = view.findViewById(R.id.toolbar_profile);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_profile);
        getDataFromDB = new getDataFromDB();
        storeNote = new storeNote(getContext());

        usernameLayout = view.findViewById(R.id.usernameLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        registerTextView = view.findViewById(R.id.registerTextView);
        loginButton = view.findViewById(R.id.loginButton);
        registerUsernameLayout = view.findViewById(R.id.registerUsernameLayout);
        registerPasswordLayout = view.findViewById(R.id.registerPasswordLayout);
        registerUsernameEditText = view.findViewById(R.id.registerUsernameEditText);
        registerPasswordEditText = view.findViewById(R.id.registerPasswordEditText);
        registerAccountEditText = view.findViewById(R.id.registerAccountEditText);
        registerAccountLayout = view.findViewById(R.id.registerAccountLayout);
        registerButton = view.findViewById(R.id.registerButton);

    }

    private void toggleRegistrationForm() {
        if (registerUsernameLayout.getVisibility() == View.GONE) {
            registerUsernameLayout.setVisibility(View.VISIBLE);
            registerPasswordLayout.setVisibility(View.VISIBLE);
            registerAccountLayout.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        } else {
            registerUsernameLayout.setVisibility(View.GONE);
            registerPasswordLayout.setVisibility(View.GONE);
            registerAccountLayout.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
        }
    }

    private void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        getDataFromDB.Login(getContext(), username, password, new getDataFromDB.LoginCallback() {
            @Override
            public void onSuccess(int onlineID) {
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                saveLoginState(username,onlineID);
                getDataFromDB.getUserFullinfo(getContext(), onlineID, new getDataFromDB.infoBack() {
                    @Override
                    public void onSuccess(ArrayList<FriendUnit> friends) {
                        for(FriendUnit friend:friends){
                            Log.i("taggg","addnewfriend online id:"+friend.getOnlineID());
                            storeNote.addNewFriends(friend);
                        }
                    }

                    @Override
                    public void onError(String error) {
                       Log.i("taggg","error set friend list");
                    }
                });
                storeNote.refreshGroupAndFriend();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void register() {
        String username = registerUsernameEditText.getText().toString();
        String password = registerPasswordEditText.getText().toString();
        String account =  registerAccountEditText.getText().toString();

        // Add your registration logic here
        // For example, save the new user to your server or database
        getDataFromDB.Register(getContext(),username,password,account);

    }




    public String getUsername() {
        Log.i("taggg","tesst2");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }


    public void saveLoginState(String username,int onlineID) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_ONLINEID,onlineID);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getOnlineID(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ONLINEID, 0);
    }

    public void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }






}