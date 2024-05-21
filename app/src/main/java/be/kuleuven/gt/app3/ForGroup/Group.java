package be.kuleuven.gt.app3.ForGroup;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import be.kuleuven.gt.app3.ForNote.AddNote;
import be.kuleuven.gt.app3.ForNote.NoteUnit;
import be.kuleuven.gt.app3.MainActivity;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;
import java.util.ArrayList;
import java.util.Arrays;

import be.kuleuven.gt.app3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Group#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Group extends Fragment {

    private Toolbar toolbar;
    private View view;
    private ListView listView;
    private GroupAdapter groupAdapter;
    private ArrayList<GroupUnit> Groups;
    private storeNote storeNote;

    public Group() {
        // Required empty public constructor
    }

    public static Group newInstance() {
        Group fragment = new Group();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("Taggg","onCreate123");
        super.onCreate(savedInstanceState);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i("Taggg","onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_group, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group, container, false);
        initdata();

        // 初始化适配器并设置给 ListView
        //init adapter and set it to listView
        groupAdapter = new GroupAdapter(getContext(), Groups);
        listView.setAdapter(groupAdapter);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.group_add) {
//                    addFriend();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame,new addFriendPage(),null).addToBackStack(null)
                            .commit();
                }
                return false;
            }
        });
        return view;
    }

    public void initdata(){
        toolbar = view.findViewById(R.id.toolbar_group);
        storeNote = new storeNote(this.getContext());
        ((MainActivity)getActivity()).showNav();
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_group);
        listView = view.findViewById(R.id.listview_group);
        Groups = storeNote.getAllGroupInfo();
    }

    public void refreshList(){
        Groups = storeNote.getAllGroupInfo();
        groupAdapter.setGroups(Groups);
        groupAdapter.notifyDataSetChanged();
    }

    public void addFriend(){
        FriendUnit f = new FriendUnit();
        f.setLable("");
        f.setName("name");
        storeNote.addNewFriends(f);
        refreshList();
    }




}