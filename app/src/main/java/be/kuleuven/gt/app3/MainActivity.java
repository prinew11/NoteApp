package be.kuleuven.gt.app3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.kuleuven.gt.app3.ForCalendar.Canlader;
import be.kuleuven.gt.app3.ForGroup.Group;
import be.kuleuven.gt.app3.ForNote.Note;
import be.kuleuven.gt.app3.ForNote.NoteUnit;
import be.kuleuven.gt.app3.ForProfile.Profile;
import be.kuleuven.gt.app3.storeAndupdate.storeNote;

public class MainActivity extends AppCompatActivity {

    private Fragment[] frags = new Fragment[4];
    private BottomNavigationView Bottomnav;
    private int fragFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Bottomnav = findViewById(R.id.bottomNavigationView);
        initFragment();
        selectFragment();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void initFragment(){
        frags[0] = new Note();
        frags[1] = new Group();
        frags[2] = new Canlader();
        frags[3] = new Profile();

        initLoadFragment(R.id.frame,0,frags);
    }

    private void initLoadFragment(int containerId, int showFrag ,Fragment... fragments){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i =0;i<frags.length;i++){
            transaction.add(containerId,fragments[i],fragments[i].getClass().getName());
            Log.i("Taggg",fragments[i].getClass().getName());

            if(i != showFrag){
                transaction.hide(fragments[i]);
            }
        }
        transaction.commitAllowingStateLoss();

    }

    private void selectFragment(){
        Bottomnav.setItemIconTintList(null);
        // 隐藏底部导航栏

        Bottomnav.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.Item1){
               showAndHideFragment(frags[0],frags[fragFlag]);
               fragFlag = 0;
            }
            else if (menuItem.getItemId() == R.id.Item2) {
                showAndHideFragment(frags[1],frags[fragFlag]);
                fragFlag = 1;
            }
            else if (menuItem.getItemId() == R.id.Item3) {
                showAndHideFragment(frags[2],frags[fragFlag]);
                fragFlag = 2;
            }
            else if (menuItem.getItemId() == R.id.Item4) {
                showAndHideFragment(frags[3],frags[fragFlag]);
                fragFlag = 3;
            }

            return true;
        });

    }

    private void showAndHideFragment(Fragment show,Fragment hide){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(show != hide){
            transaction.show(show).hide(hide).commitAllowingStateLoss();
        }

    }

    public void hideNav(){
        Bottomnav.setVisibility(View.GONE);
    }

    public void showNav(){
        Bottomnav.setVisibility(View.VISIBLE);
    }

}



