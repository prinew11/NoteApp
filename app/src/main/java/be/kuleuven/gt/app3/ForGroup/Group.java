package be.kuleuven.gt.app3.ForGroup;

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

import be.kuleuven.gt.app3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Group#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Group extends Fragment {

    private Toolbar toolbar;

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
        Log.i("Taggg", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        toolbar = view.findViewById(R.id.toolbar_group);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_group);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.group_item1) {
                    Log.i("Taggg", "g1");
                }
                return false;
            }
        });
        return view;
    }
}