package be.kuleuven.gt.app3.ForNote;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import be.kuleuven.gt.app3.ForData.SpaceItem;
import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;

public class shareFriend extends Fragment {


    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;
    private ArrayList<FriendUnit> friends;
    private storeNote storeNote;
    private Toolbar toolbar;
    public shareFriend() {
        // Required empty public constructor
    }

    public static shareFriend newInstance() {
        shareFriend fragment = new shareFriend();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Taggg","onCreate");
        super.onCreate(savedInstanceState);
    }

    public interface OnFriendSelectedListener {
        void onFriendSelected(ArrayList<FriendUnit> friends);
    }
    private OnFriendSelectedListener onFriendSelectedListener;

    public void setOnFriendSelectedListener(OnFriendSelectedListener listener) {
        this.onFriendSelectedListener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share_friend, container, false);

        recyclerView = view.findViewById(R.id.share_recyclerView);
        toolbar = view.findViewById(R.id.sharef_toolbar);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_sharemenu);
        storeNote = new storeNote(getContext());
        friends = storeNote.getAllGroupInfo().isEmpty() ? new ArrayList<>() : storeNote.getAllGroupInfo().get(0).getFriends();
        Log.i("taggg","friend size:"+storeNote.getAllGroupInfo().get(0).getFriends().size());

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        friendAdapter = new FriendAdapter(getContext(),friends);
        recyclerView.setAdapter(friendAdapter);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.share2friend) {
                    // Get selected friends
                    ArrayList<FriendUnit> selectedFriends = friendAdapter.getSelect();
                    if (selectedFriends.isEmpty()) {
                        Toast.makeText(getContext(), "Please select a friend to share", Toast.LENGTH_SHORT).show();
                    } else {
                            if (onFriendSelectedListener != null) {
                                Log.i("taggg","select size:"+selectedFriends.size());
                                onFriendSelectedListener.onFriendSelected(selectedFriends);
                            }
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });



        return view;
    }
}