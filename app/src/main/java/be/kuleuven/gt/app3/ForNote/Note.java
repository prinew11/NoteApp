package be.kuleuven.gt.app3.ForNote;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.ForData.SpaceItem;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Note#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Note extends Fragment implements AddNote.passData {

    private Toolbar toolbar1;
    private SearchView searchView;
    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private ReAdapter adapter;
    private ArrayList<NoteUnit> Notes = new ArrayList<>();
    private String word;
    private AddNote addNote;




    public Note() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Note newInstance() {
        Note fragment = new Note();
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
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Taggg","onCreateView");
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        toolbar1 = view.findViewById(R.id.toolbar_list);
        searchView = view.findViewById(R.id.searchView);
        button = view.findViewById(R.id.floatingActionButton);

        //Notes = storeNote.queryNotesAll(0);

        recyclerView = view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        //set the gap on horizon
        SpaceItem spaceItem = new SpaceItem(0);
        recyclerView.addItemDecoration(spaceItem);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        adapter = new ReAdapter(this.getContext(),Notes);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        toolbar1.inflateMenu(R.menu.menu_list);

        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.list_item1){
                    Log.i("Taggg","item1");
                }
                else if(item.getItemId() == R.id.list_item2){
                    Log.i("Taggg","item2");
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame,new AddNote(),null).addToBackStack(null)
                                .commit();
                Log.i("Taggg","+");

            }
        });

        adapter.setOnItemClickListener(new ReAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, NoteUnit noteUnit) {
                Log.i("Taggg","edit");
                Bundle bundle = new Bundle();
                bundle.putSerializable("note",noteUnit);
                NoteInfo noteInfo = new NoteInfo();
                noteInfo.setArguments(bundle);
                Toast.makeText(getContext(), noteUnit.getTitle(), Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, noteInfo,null).addToBackStack(null)
                        .commit();


            }
        });

        adapter.setOnItemLongClickListener(new ReAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, NoteUnit noteUnit) {
                Log.i("Taggg","+");
            }
        });

        return view;
    }



    public void DataPasser(NoteUnit mNote){
        Log.i("Taggg",mNote.getId()+"afpasser");
        if(mNote.getFlag()==0){
            mNote.setFlag(1);
            Toast.makeText(getContext(), mNote.getTitle(), Toast.LENGTH_SHORT).show();
            adapter.addCard(mNote);
        } else if (mNote.getFlag()==1) {
            Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();
            adapter.editCard(mNote);
        }

    }

    //store the note int the database when close the app
    public void onExitToStore(){


    }





}