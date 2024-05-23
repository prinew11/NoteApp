package be.kuleuven.gt.app3.ForNote;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.util.ArrayList;

import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.ForGroup.addFriendPage;
import be.kuleuven.gt.app3.MainActivity;
import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.ForData.SpaceItem;
import be.kuleuven.gt.app3.storeAndupdate.getDataFromDB;
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
    private storeNote storeNote;
    private boolean isDelete;
    private View view;
    private BottomNavigationView deleteNav;
    private BottomNavigationView mainNav;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isSearch;
    private View overlay;
    private getDataFromDB getDataFromDB;
    private ArrayList<NoteUnit> select;




    public Note() {
        // Required empty public constructor
    }

    public static Note newInstance() {
        Note fragment = new Note();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_note, container, false);
        initData();
        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.list_item1){
                    Log.i("Taggg","item1");
                }
                else if(item.getItemId() == R.id.list_item2){
                    Log.i("Taggg","item2");
                }
                else if (item.getItemId() == R.id.exit) {
                    exitDelete();
                }else if (item.getItemId() == R.id.selectAll) {
                    selectAll();
                    Log.i("Taggg","selectAllclick");
                } else if (item.getItemId() == R.id.refresh) {
                    receive();
                }
                return false;
            }
        });

        deleteNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.delete){
                    deleteNote();
                }
                else if (menuItem.getItemId()==R.id.share) {
                    shareNote();

                }
                else if(menuItem.getItemId()==R.id.setTop){

                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("flag","new");
                AddNote addNote = new AddNote();
                addNote.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame,addNote,null).addToBackStack(null)
                                .commit();
                Log.i("Taggg","+");

            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchMode();
                    searchView.setIconified(false);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(isSearch) {
                    performSearch(query);
                    Log.i("Taggg", query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(isSearch) {
                    overlay.setVisibility(View.GONE);
                    performSearch(newText);
                    Log.i("Taggg", newText);
                }
                return false;
            }
        });

        adapter.setOnItemClickListener(new ReAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, NoteUnit noteUnit) {
//                exitSearch();
                Log.i("Taggg","ID=" + noteUnit.getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("note",noteUnit);
                bundle.putString("flag","edited");
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
//                exitSearch();
                select = new ArrayList<NoteUnit>();
                select.add(noteUnit);
                adapter.setSelect(select);
                deleteMode();
                Log.i("Taggg","ID="+noteUnit.getId());
                Log.i("Taggg","Title="+noteUnit.getTitle());
                Log.i("Taggg","Con="+noteUnit.getContent());
                Log.i("Taggg","CT="+noteUnit.getCreateTime());
                Log.i("Taggg","ET="+noteUnit.getUpdateTime());
                Log.i("Taggg","en="+noteUnit.getIsEncrypt());
                Log.i("Taggg","type="+noteUnit.getType());
                Log.i("Taggg","color="+noteUnit.getBgColor());
                Log.i("Taggg","GN="+noteUnit.getGroupName());
                Log.i("Taggg","GID="+noteUnit.getGroupId());
                //storeNote.deleteNote(noteUnit.getId());
                //refreshNote();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isDelete&&!isSearch){
                    exitDelete();}
                else if(isSearch&&!isDelete){
                    exitSearch();
                }
                else if (isSearch&&isDelete) {
                    exitSearch();
                    exitDelete();

                } else{
                    if (doubleBackToExitPressedOnce) {
                        requireActivity().finish(); // return second time,exit the app
                    }else {
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(requireContext(), "BACK again to exit", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000); // exit the app by back twice in 2s
                    }
                }
            }
        });

        return view;
    }




    public void DataPasser(NoteUnit mNote){
        Log.i("Taggg",mNote.getId()+"afpasser");
        if(mNote.getFlag()==0){
            mNote.setFlag(1);
            Toast.makeText(getContext(), mNote.getTitle(), Toast.LENGTH_SHORT).show();
            storeNote.insertNote(mNote);
            adapter.addCard(mNote);
        } else if (mNote.getFlag()==1) {
            Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();
            Log.i("Taggg","ID="+mNote.getId());
            Log.i("Taggg","Title="+mNote.getTitle());
            Log.i("Taggg","Con="+mNote.getContent());
            Log.i("Taggg","CT="+mNote.getCreateTime());
            Log.i("Taggg","ET="+mNote.getUpdateTime());
            Log.i("Taggg","en="+mNote.getIsEncrypt());
            Log.i("Taggg","type="+mNote.getType());
            Log.i("Taggg","color="+mNote.getBgColor());
            Log.i("Taggg","GN="+mNote.getGroupName());
            Log.i("Taggg","GID="+mNote.getGroupId());
            storeNote.updateNote(mNote);
            adapter.editCard(mNote);
        }

    }


    public void initData(){
        toolbar1 = view.findViewById(R.id.toolbar_list);
        searchView = view.findViewById(R.id.searchView1);
        button = view.findViewById(R.id.floatingActionButton);
        deleteNav = view.findViewById(R.id.deleteNav);
        mainNav = view.findViewById(R.id.bottomNavigationView);
        overlay = view.findViewById(R.id.overlays);
        //search the notes store in the database,display them in create adapter
        storeNote = new storeNote(this.getContext());
        //get the note store in the database
        Notes = storeNote.queryNotesAll(1);
        recyclerView = view.findViewById(R.id.recyclerViews);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        //set the gap on horizon
        SpaceItem spaceItem = new SpaceItem(1);
        recyclerView.addItemDecoration(spaceItem);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        adapter = new ReAdapter(this.getContext(),Notes,this.getActivity());
        recyclerView.setAdapter(adapter);
        ((MainActivity)getActivity()).showNav();
        setHasOptionsMenu(true);
        toolbar1.inflateMenu(R.menu.menu_list);
        Log.i("taggg","delete"+isDelete);
        Log.i("taggg","search"+isSearch);
        getDataFromDB = new getDataFromDB();

        if(isSearch){
            searchMode();
        }


    }


    public void refreshNote(){
        Notes = storeNote.queryNotesAll(1);
        adapter.setNotes(Notes);
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectAll(){
        if (adapter.getSelect().size() < Notes.size()) {
            ArrayList<NoteUnit> selectSet = new ArrayList<>(Notes);
            // 创建一个新的 ArrayList 对象，并将 Notes 中的所有元素添加到其中
            //create a arraylist and store selected Note in it
            adapter.setSelect(selectSet);
            adapter.notifyDataSetChanged();

        } else {
            adapter.setSelect(new ArrayList<NoteUnit>());
            adapter.notifyDataSetChanged();

        }

    }

    public void deleteMode(){
        CheckBox selectButton = view.findViewById(R.id.checkBox);
        selectButton.setVisibility(View.VISIBLE);
        isDelete = true;
        adapter.setDeleteMode(isDelete);
        button.setVisibility(View.GONE);
        deleteNav.setVisibility(View.VISIBLE);
        toolbar1.setTitle("Select:");
        ((MainActivity)getActivity()).hideNav();
        toolbar1.getMenu().clear();
        toolbar1.inflateMenu(R.menu.menu_deletetoolbar);


    }

    public void exitDelete(){

        deleteNav.setVisibility(View.GONE);
        isDelete = false;
        adapter.setDeleteMode(isDelete);
        if(!isSearch) {
            button.setVisibility(View.VISIBLE);
            toolbar1.setTitle("Note");
            ((MainActivity) getActivity()).showNav();
            toolbar1.getMenu().clear();
            toolbar1.inflateMenu(R.menu.menu_list);
            refreshNote();
        }
//        if(isSearch){
//            performSearch(searchView.getQuery().toString());
//        }
    }

    public void deleteNote(){
        select = adapter.getSelect();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(select!=null){
            builder.setTitle("Hint");
            builder.setMessage("Sure to delete?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i =0;i<select.size();i++){
                        storeNote.deleteNote(select.get(i).getId());
                    }
                    if(!isSearch){refreshNote();}
                    else{ performSearch(searchView.getQuery().toString());}
                }
            });
            builder.setNegativeButton("No",null);
            builder.create().show();

        }
    }

    public void searchMode(){
        isSearch = true;
        overlay.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        toolbar1.setVisibility(View.GONE);
        ((MainActivity)getActivity()).hideNav();
         Log.i("taggg","search");




    }

    public void exitSearch(){
        isSearch = false;
        toolbar1.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.GONE);
        searchView.setQuery("", false); // clear text
        searchView.clearFocus(); // clear focus
        ((MainActivity)getActivity()).showNav();
        refreshNote();
        Log.i("taggg","Exitsearch");
    }

    public void performSearch(String query){
        ArrayList<NoteUnit> outcome = new ArrayList<>();
        Log.i("taggg","enter");
        overlay.setVisibility(View.GONE);
        if(!query.isEmpty()){outcome = storeNote.searchNote(query);}
        adapter.setNotes(outcome);
        adapter.notifyDataSetChanged();


    }

    public void receive(){
        int localID = getActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE).getInt("onlineID", 0);
        Log.i("taggg",localID+"");
        getDataFromDB.receiveNote(getContext(), localID, new getDataFromDB.receiveBack() {
            @Override
            public void onSuccess(NoteUnit note,String friendName) {
                if(!note.getContent().isEmpty()||!note.getTitle().isEmpty()){
                note.setTitle("From "+friendName+": "+note.getTitle());
                note.setContent(note.getContent());
                storeNote.insertNote(note);
                refreshNote();
                getDataFromDB.receiveNote(getContext(),localID);}
            }

            @Override
            public void onError(String error) {
               Log.i("taggg","nothing");
            }
        });


    }

    public void shareNoteWithFriend(int friendId){
        int localID = getActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE).getInt("onlineID", 0);
        select = adapter.getSelect();
        if (select != null && !select.isEmpty()) {
            for(int i=0;i<select.size();i++) {
                NoteUnit note = select.get(i); // 假设一次只分享一个笔记
                Log.i("taggg", "shared title:" + select.get(i).getTitle());
                Log.i("taggg", "shared context:" + select.get(i).getContent());
                getDataFromDB.shareNoteToFriend(getContext(), localID, friendId, note);
            }
        }
    }


    public void shareNote() {
        select = adapter.getSelect();
        if (select != null && !select.isEmpty()) {
            shareFriend fragment = new shareFriend();
            fragment.setOnFriendSelectedListener(new shareFriend.OnFriendSelectedListener() {
                @Override
                public void onFriendSelected(ArrayList<FriendUnit> friends) {
                    Log.i("taggg","select size from note:"+friends.size());
                    for(int i =0;i<friends.size();i++) {
                        Log.i("taggg","friend id:"+friends.get(i).getOnlineID());
                        shareNoteWithFriend(friends.get(i).getOnlineID());
                    }
                }
            }); // Set the listener
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, fragment, null).addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Please select a note to share", Toast.LENGTH_SHORT).show();
        }
    }



}
