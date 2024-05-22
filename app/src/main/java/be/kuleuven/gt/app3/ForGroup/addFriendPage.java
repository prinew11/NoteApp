package be.kuleuven.gt.app3.ForGroup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import be.kuleuven.gt.app3.MainActivity;
import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.getDataFromDB;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;


public class addFriendPage extends Fragment {
    private View view;
    private SearchView search;
    private LinearLayout linearLayout;
    private View FriendView;
    private ImageView IconView;
    private TextView FriendName;
    private storeNote store;
    private getDataFromDB getDataFromDB;
    private FriendUnit addFriend;


    public addFriendPage() {
        // Required empty public constructor
    }


    public static addFriendPage newInstance() {
        addFriendPage fragment = new addFriendPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_friend_page, container, false);
        Log.i("taggg","in");
        initdata();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("taggg",query);
                searchFriend(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        Log.i("taggg","in1");
        FriendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Hint");
                builder.setMessage("Add to Friend?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //addFriend instruction;
                            store.addNewFriends(addFriend);
                            int onlineID = getActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE).getInt("onlineID", 0);
                            Log.i("taggg",onlineID+"!");
                            getDataFromDB.addFriend(getContext(), addFriend.getID(),onlineID);
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                builder.setNegativeButton("No",null);
                builder.create().show();

                }

        });

        Log.i("taggg","in2");
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        Log.i("taggg","in3");

        return view;
    }


    public void initdata(){
        ((MainActivity)getActivity()).hideNav();
         linearLayout = view.findViewById(R.id.linearLayout_friend);
         search = view.findViewById(R.id.afsearching);
         FriendView = LayoutInflater.from(getContext()).inflate(R.layout.groupitembar,linearLayout,false);
         IconView = FriendView.findViewById(R.id.iconFriend);
         FriendName = FriendView.findViewById(R.id.ItemName);
         store = new storeNote(getContext());
         getDataFromDB = new getDataFromDB();

    }

    public void searchFriend(String account){
        getDataFromDB search = new getDataFromDB();
        search.getUserInfo(getContext(),account,new getDataFromDB.DataCallback() {
            @Override
            public void onSuccess(FriendUnit friendUnit) {
                linearLayout.removeAllViews();
                String name = friendUnit.getName();
                int ID = friendUnit.getID();
                String account = friendUnit.getAccount();
                addFriend = friendUnit;
                FriendName.setText(name);
                linearLayout.addView(FriendView);
                Log.i("taggg","addFriend");
            }

            @Override
            public void onError(String error) {
                Log.i("taggg","error");
            }
        });


    }









}