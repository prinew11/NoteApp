package be.kuleuven.gt.app3.ForNote;

//add
import android.content.Context;

import static android.media.ThumbnailUtils.createImageThumbnail;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import be.kuleuven.gt.app3.R;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;


public class AddNote extends Fragment{
    private Toolbar toolbar;
    private EditText title;
    private EditText word;
    private NoteUnit mNote;
    private NoteUnit pNote;// the note pass to fragment
    private Calendar calendar;
    private int day;
    private int month;
    private int year;
    private String stitle;
    private String scontext;
    private String editDate;
    private Bundle bundle;
    private View view;
    private ScrollView scroll;
    private LinearLayout linearLayout;
    private BottomNavigationView bottomNavigationView;



    public AddNote() {
        // Required empty public constructor
    }

    public static AddNote newInstance() {
        AddNote fragment = new AddNote();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public interface passData{
        void DataPasser(NoteUnit noteUnit);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("Taggg","oncreateAddNote");
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Taggg","oncreateViewAddNote");
        view = inflater.inflate(R.layout.fragment_add_note, container, false);
        initData();
        //showDetail();



        //add note by return button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();
                TopassData(mNote);
                bottomNavigationView.setVisibility(View.VISIBLE);
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.addPicture){
                    Log.i("Taggg","addPicture");
                    callGallery();
                } else if (item.getItemId() == R.id.editIcon1) {
                    setInfo();

                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("note",mNote);
                    NoteInfo noteInfo = new NoteInfo();
                    noteInfo.setArguments(bundle1);

                    Log.i("Taggg","info");
                    requireActivity().getSupportFragmentManager().popBackStack();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, noteInfo,null).addToBackStack(null)
                            .commit();
                }
                return false;
            }
        });

        //back to information page and save the change
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.i("Taggg","enddd");
                Bundle bundle = new Bundle();
                setInfo();
                TopassData(mNote);
                bundle.putSerializable("note",mNote);
                NoteInfo noteInfo = new NoteInfo();
                noteInfo.setArguments(bundle);

                Log.i("Taggg","info");
                requireActivity().getSupportFragmentManager().popBackStack();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, noteInfo,null).addToBackStack(null)
                        .commit();

            }
        });


        return view;
    }





    private void callGallery(){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum,1);
    }

    public void initData(){
        toolbar = view.findViewById(R.id.toolbar_add);
        title = view.findViewById(R.id.editTitle);
        word = view.findViewById(R.id.editContext);
        scroll = view.findViewById(R.id.scroll);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        mNote = new NoteUnit();
        pNote = new NoteUnit();

        toolbar.inflateMenu(R.menu.menu_addnote); // inflate the toolbar's menu(contains the menu item)

        calendar = Calendar.getInstance();

        bundle = getArguments();
        if (bundle != null ) {
            pNote = (NoteUnit) bundle.getSerializable("note");
        }


        if(pNote.getFlag()==1){
            toolbar.setTitle("Edit Note");
            mNote.setFlag(1);
            showDetail(pNote);
        } else {
            toolbar.setTitle("New Note");
        }


    }

    public String getTime(){
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        editDate = year +"-"+month+"-"+day;
        return editDate;
    }

    public void setInfo(){
        editDate = getTime();
        mNote.setUpdateTime(editDate);
        mNote.setContent(word.getText().toString());
        mNote.setTitle(title.getText().toString());
        scontext = word.getText().toString();
        stitle = title.getText().toString();
    }

    public void TopassData(NoteUnit noteUnit){
        passData passdata = (passData)getActivity().getSupportFragmentManager().findFragmentByTag("be.kuleuven.gt.app3.ForNote.Note");
        if (passdata != null) {
            if(!noteUnit.getTitle().isEmpty()||!noteUnit.getContent().isEmpty()) {
                passdata.DataPasser(noteUnit);
            }
        }
    }

    public void showDetail(NoteUnit noteUnit){
        title.setText(noteUnit.getTitle());
        word.setText(noteUnit.getContent());
    }









}












