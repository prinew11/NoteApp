package be.kuleuven.gt.app3.ForGroup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import be.kuleuven.gt.app3.MainActivity;
import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.getDataFromDB;


public class addFriendPage extends Fragment {
    private View view;
    private SearchView searchView;
    private LinearLayout linearLayout;
    private View FriendView;
    private ImageView IconView;
    private TextView FriendName;


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
        initdata();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchFriend(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
                        }
                    });
                builder.setNegativeButton("No",null);
                builder.create().show();

                }

        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });




        return view;
    }


    public void initdata(){
        ((MainActivity)getActivity()).hideNav();
         searchView = view.findViewById(R.id.afsearch);
         linearLayout = view.findViewById(R.id.linearLayout_friend);
         FriendView = LayoutInflater.from(getContext()).inflate(R.layout.groupitembar,linearLayout,false);
         IconView = view.findViewById(R.id.iconFriend);
         FriendName = view.findViewById(R.id.ItemName);

    }

    public void searchFriend(String account){
        getDataFromDB search = new getDataFromDB();
        search.getGroupJson(getContext(),account,new getDataFromDB.DataCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onError(String error) {

            }
        });


    }









}