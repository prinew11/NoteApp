package be.kuleuven.gt.app3.storeAndupdate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;

import be.kuleuven.gt.app3.ForData.ForString;


//use SQLite to store the data
public class liteDatabase extends SQLiteOpenHelper{
        private final static String DB_NAME = "note.db";// database name
        private final static int DB_VERSION = 1;// version

        public liteDatabase(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create group table
            db.execSQL("create table db_group(g_id integer primary key autoincrement, " +
                    "g_name varchar, g_order integer, g_color varchar, g_encrypt integer," +
                    "g_create_time datetime, g_update_time datetime )");
            //create note table
            db.execSQL("create table db_note(n_id integer primary key autoincrement, n_title varchar, " +
                    "n_content varchar, n_group_id integer, n_group_name varchar, n_type integer, " +
                    "n_bg_color varchar, n_encrypt integer, n_create_time datetime," +
                    "n_update_time datetime )");
            //create a default group
            db.execSQL("insert into db_group(g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                    "values(?,?,?,?,?,?)", new String[]{"default", "1", "#FFFFFF", "0", ForString.date2string(new Date()), ForString.date2string(new Date())});

            db.execSQL("insert into db_group(g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                    "values(?,?,?,?,?,?)", new String[]{"testdefalut", "2", "#FFFFFF", "0", ForString.date2string(new Date()), ForString.date2string(new Date())});


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

}
