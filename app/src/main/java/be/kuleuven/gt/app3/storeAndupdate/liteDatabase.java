package be.kuleuven.gt.app3.storeAndupdate;

import android.content.SharedPreferences;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

import be.kuleuven.gt.app3.ForData.ForString;


//use SQLite to store the data
public class liteDatabase extends SQLiteOpenHelper{
        private final static String DB_NAME = "note.db";// database name
        private final static int DB_VERSION = 1;// version
        private int onlineID;
        public liteDatabase(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("taggg","create");
            //create group table
            db.execSQL("create table db_group(g_id integer primary key autoincrement, " +
                    "g_name varchar, g_order integer, g_color varchar, g_encrypt integer," +
                    "g_create_time datetime, g_update_time datetime )");
            //create note table
            db.execSQL("create table db_note(n_id integer primary key autoincrement, n_title varchar, " +
                    "n_content varchar, n_group_id integer, n_group_name varchar, n_type integer, " +
                    "n_bg_color varchar, n_encrypt integer, n_create_time datetime," +
                    "n_update_time datetime )");

            db.execSQL("create table db_friendsgroup(fg_id integer primary key autoincrement, " +
                    "fg_name varchar, fg_order integer,fg_create_time datetime)");

            db.execSQL("create table db_friends(f_id integer primary key autoincrement, " +
                    "f_name varchar, f_label varchar,f_account varchar,f_onlineID integer,f_create_time datetime )");

            db.execSQL("CREATE TABLE db_relationship (rfriend_id INTEGER,rgroup_id INTEGER, " +
                    "PRIMARY KEY (rfriend_id, rgroup_id), " +
                    "FOREIGN KEY (rfriend_id) REFERENCES db_friends(f_id), " +
                    "FOREIGN KEY (rgroup_id) REFERENCES db_friendsgroup(fg_id))");

            //create a default group
            db.execSQL("insert into db_group(g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                    "values(?,?,?,?,?,?)", new String[]{"default", "1", "#FFFFFF", "0", ForString.date2string(new Date()), ForString.date2string(new Date())});

            db.execSQL("insert into db_group(g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                    "values(?,?,?,?,?,?)", new String[]{"testdefalut", "2", "#FFFFFF", "0", ForString.date2string(new Date()), ForString.date2string(new Date())});

            db.execSQL("insert into db_friendsgroup(fg_name, fg_order, fg_create_time) " +
                    "values(?,?,?)", new String[]{"AllFriends", "1", ForString.date2string(new Date())});

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

}
