package be.kuleuven.gt.app3.ForCalendar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kuleuven.gt.app3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Canlader#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Canlader extends Fragment {

    private Toolbar toolbar;

    public Canlader() {
        // Required empty public constructor
    }

    public static Canlader newInstance() {
        Canlader fragment = new Canlader();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Taggg","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i("Taggg","onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_canlader, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Taggg", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_canlader, container, false);
        toolbar = view.findViewById(R.id.toolbar_canlader);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_canlader);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.canlader_item1) {
                    Log.i("Taggg", "c1");
                } else if (item.getItemId() == R.id.canlader_item2) {
                    Log.i("Taggg", "c2");
                }
                return false;
            }
        });
        return view;
    }
}