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

import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.getDataFromDB;


public class Profile extends Fragment {

    private Toolbar toolbar;
    private View view;

    private TextInputLayout usernameLayout, passwordLayout;
    private TextInputEditText usernameEditText, passwordEditText;
    private TextView registerTextView;
    private Button loginButton, registerButton;
    private TextInputLayout registerUsernameLayout, registerPasswordLayout;
    private TextInputEditText registerUsernameEditText, registerPasswordEditText;
    private getDataFromDB getDataFromDB;
    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";

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

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initData();

        if (isLoggedIn(getContext())) {
            // Perform actions as needed, such as navigating to the user's profile page
            String username = getUsername(getContext());
            Toast.makeText(getContext(), "Welcome back, " + username + "!", Toast.LENGTH_SHORT).show();
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
        registerButton = view.findViewById(R.id.registerButton);

    }

    private void toggleRegistrationForm() {
        if (registerUsernameLayout.getVisibility() == View.GONE) {
            registerUsernameLayout.setVisibility(View.VISIBLE);
            registerPasswordLayout.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        } else {
            registerUsernameLayout.setVisibility(View.GONE);
            registerPasswordLayout.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
        }
    }

    private void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Add your login logic here
        // For example, authenticate with your server or database
        getDataFromDB.Login(getContext(),"account",password);
        saveLoginState(getContext(), username);
        Toast.makeText(getContext(), "Logging in...", Toast.LENGTH_SHORT).show();
    }

    private void register() {
        String username = registerUsernameEditText.getText().toString();
        String password = registerPasswordEditText.getText().toString();

        // Add your registration logic here
        // For example, save the new user to your server or database

        Toast.makeText(getContext(), "Registering...", Toast.LENGTH_SHORT).show();
    }




    public String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }


    public void saveLoginState(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }






}