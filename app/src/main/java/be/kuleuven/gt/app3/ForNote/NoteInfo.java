package be.kuleuven.gt.app3.ForNote;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import be.kuleuven.gt.app3.ForData.ForString;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import be.kuleuven.gt.app3.R;
import io.reactivex.disposables.Disposable;

import java.util.Calendar;

public class NoteInfo extends Fragment {
    private Toolbar toolbar;
    private EditText title;
    private EditText word;
    private NoteUnit mNote;
    private ProgressDialog loadingDialog;
    private Disposable mDisposable;
    private Calendar calendar;
    private int day;
    private int month;
    private int year;
    private String editDate;
    private Bundle bundle;
    private View view;
    private BottomNavigationView bottomNavigationView;


    public NoteInfo(){

    }

    public static NoteInfo newInstance() {
        NoteInfo fragment = new NoteInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
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

        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
        toolbar.setTitle("Information");
        title.setEnabled(false);
        word.setEnabled(false);
        loadingDialog = new ProgressDialog(getContext());


        if (bundle != null) {
            mNote = (NoteUnit) bundle.getSerializable("note");
            Log.i("Taggg",mNote.getId()+"a");
            title.setText(mNote.getTitle());
            word.setText(mNote.getContent());


        }

        //add note by return button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Taggg","click");
                bottomNavigationView.setVisibility(View.VISIBLE);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if (item.getItemId() == R.id.editIcon2) {
                    bundle.putSerializable("note",mNote);
                    AddNote addNote = new AddNote();
                    addNote.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, addNote,null).addToBackStack(null)
                            .commit();
                }
                return false;
            }
        });
        return view;
    }


    public void initData(){
        toolbar = view.findViewById(R.id.toolbar_add);
        title = view.findViewById(R.id.editTitle);
        word = view.findViewById(R.id.editContext);
        mNote = new NoteUnit();
        toolbar.inflateMenu(R.menu.menu_noteinfo);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        bundle = getArguments();
    }


}



