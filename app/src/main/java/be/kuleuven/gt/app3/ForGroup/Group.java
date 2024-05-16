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
    private ArrayList<GroupUnit> Groups = new ArrayList<>();

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
        Log.i("Taggg","onCreate");
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

        ArrayList<FriendUnit> friends1 = new ArrayList<>();
        friends1.add(new FriendUnit("Friend 1"));
        friends1.add(new FriendUnit("Friend 2"));
        Groups.add(new GroupUnit("Group 1", friends1));
        Groups.add(new GroupUnit("Group 2z", friends1));

        // 初始化适配器并设置给 ListView
        groupAdapter = new GroupAdapter(getContext(), Groups);
        listView.setAdapter(groupAdapter);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.group_add) {
                    Log.i("Taggg", "add");
                }
                return false;
            }
        });
        return view;
    }

    public void initdata(){
        toolbar = view.findViewById(R.id.toolbar_group);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_group);
        listView = view.findViewById(R.id.listview_group);

    }




}