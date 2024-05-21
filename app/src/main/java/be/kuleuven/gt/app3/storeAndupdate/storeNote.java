package be.kuleuven.gt.app3.storeAndupdate;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import be.kuleuven.gt.app3.ForData.ForString;
import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.ForGroup.GroupUnit;
import be.kuleuven.gt.app3.ForNote.NoteUnit;

public class storeNote {
    private liteDatabase liteDatabase;

    public storeNote(Context context) {
        liteDatabase = new liteDatabase(context);
    }

    //search all notes
    @SuppressLint("Range")
    public ArrayList<NoteUnit> queryNotesAll(int groupId) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();

        ArrayList<NoteUnit> noteList = new ArrayList<>();
        NoteUnit note ;
        String sql ;
        Cursor cursor = null;
        try {
            if (groupId > 0){
                Log.i("taggg",">0");
                sql = "select * from db_note where n_group_id =" + groupId +
                        " order by n_update_time desc";
            } else {
                sql = "select * from db_note where n_group_id = 1 order by n_create_time desc" ;
            }
            cursor = db.rawQuery(sql, null);
            //cursor = db.query("note", null, null, null, null, null, "n_id desc");
            while (cursor.moveToNext()) {
                //get the information of notes
                note = new NoteUnit();
                note.setId(cursor.getInt(cursor.getColumnIndex("n_id")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("n_title")));
                note.setContent(cursor.getString(cursor.getColumnIndex("n_content")));
                note.setGroupId(cursor.getInt(cursor.getColumnIndex("n_group_id")));
                note.setGroupName(cursor.getString(cursor.getColumnIndex("n_group_name")));
                note.setType(cursor.getInt(cursor.getColumnIndex("n_type")));
                note.setBgColor(cursor.getString(cursor.getColumnIndex("n_bg_color")));
                note.setIsEncrypt(cursor.getInt(cursor.getColumnIndex("n_encrypt")));
                note.setCreateTime(cursor.getString(cursor.getColumnIndex("n_create_time")));
                note.setUpdateTime(cursor.getString(cursor.getColumnIndex("n_update_time")));
                noteList.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return noteList;
    }

    //insert notes
    public long insertNote(NoteUnit note) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        String sql = "insert into db_note(n_title,n_content,n_group_id,n_group_name," +
                "n_type,n_bg_color,n_encrypt,n_create_time,n_update_time) " +
                "values(?,?,?,?,?,?,?,?,?)";

        long ret = 0;
        //sql = "insert into ex_user(eu_login_name,eu_create_time,eu_update_time) values(?,?,?)";
        SQLiteStatement stat = db.compileStatement(sql);
        db.beginTransaction();
        try {
            stat.bindString(1, note.getTitle());
            stat.bindString(2, note.getContent());
            stat.bindLong(3, note.getGroupId());
            stat.bindString(4, note.getGroupName());
            stat.bindLong(5, note.getType());
            stat.bindString(6, note.getBgColor());
            stat.bindLong(7, note.getIsEncrypt());
            stat.bindString(8, ForString.date2string(new Date()));
            stat.bindString(9, ForString.date2string(new Date()));
            ret = stat.executeInsert();
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ret;
    }

    //update the note
    public void updateNote(NoteUnit note) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("n_title", note.getTitle());
        values.put("n_content", note.getContent());
        values.put("n_group_id", note.getGroupId());
        values.put("n_group_name", note.getGroupName());
        values.put("n_type", note.getType());
        values.put("n_bg_color", note.getBgColor());
        values.put("n_encrypt", note.getIsEncrypt());
        values.put("n_update_time", ForString.date2string(new Date()));
        db.update("db_note", values, "n_id=?", new String[]{note.getId()+""});
        db.close();
    }

    //delete notes
    public int deleteNote(int noteId) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        int ret = 0;
        try {
            ret = db.delete("db_note", "n_id=?", new String[]{noteId + ""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    //delete lots of note
    public int deleteNote(ArrayList<NoteUnit> mNotes) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        int ret = 0;
        try {
            if (mNotes != null && mNotes.size() > 0) {
                db.beginTransaction();//begin
                try {
                    for (NoteUnit note : mNotes) {
                        ret += db.delete("db_note", "n_id=?", new String[]{note.getId() + ""});
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

    @SuppressLint("Range")
    public ArrayList<NoteUnit> searchNote(String query){
        ArrayList<NoteUnit> outcome= new ArrayList<>();
        SQLiteDatabase db = liteDatabase.getWritableDatabase();

        NoteUnit note ;
        String sql ;
        Cursor cursor = null;
        try {
                Log.i("taggg",">0");
                sql = "select * from db_note"+
                        " order by n_update_time desc";

            cursor = db.rawQuery(sql, null);
            //cursor = db.query("note", null, null, null, null, null, "n_id desc");
            while (cursor.moveToNext()) {
                //get the information of notes
                if(cursor.getString(cursor.getColumnIndex("n_title")).contains(query)
                ||cursor.getString(cursor.getColumnIndex("n_content")).contains(query))
                {
                    note = new NoteUnit();
                    note.setId(cursor.getInt(cursor.getColumnIndex("n_id")));
                    note.setTitle(cursor.getString(cursor.getColumnIndex("n_title")));
                    note.setContent(cursor.getString(cursor.getColumnIndex("n_content")));
                    note.setGroupId(cursor.getInt(cursor.getColumnIndex("n_group_id")));
                    note.setGroupName(cursor.getString(cursor.getColumnIndex("n_group_name")));
                    note.setType(cursor.getInt(cursor.getColumnIndex("n_type")));
                    note.setBgColor(cursor.getString(cursor.getColumnIndex("n_bg_color")));
                    note.setIsEncrypt(cursor.getInt(cursor.getColumnIndex("n_encrypt")));
                    note.setCreateTime(cursor.getString(cursor.getColumnIndex("n_create_time")));
                    note.setUpdateTime(cursor.getString(cursor.getColumnIndex("n_update_time")));
                    outcome.add(note);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return outcome;
    }

    public long addNewFriends(FriendUnit friend) {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        String friendSql = "INSERT INTO db_friends(f_name, f_label, f_create_time) VALUES (?, ?, ?)";
        String relationshipSql = "INSERT INTO db_relationship(rfriend_id, rgroup_id) VALUES (?, ?)";
        long ret = 0;
        db.beginTransaction();

        try {
            SQLiteStatement friendStat = db.compileStatement(friendSql);
            friendStat.bindString(1, friend.getName());
            friendStat.bindString(2, friend.getLabel());
            friendStat.bindString(3, ForString.date2string(new Date()));

            // Insert new friend
            ret = friendStat.executeInsert();

            // If the friend was successfully inserted, add to default group (assuming default group id is 1)
            if (ret != -1) {
                SQLiteStatement relationshipStat = db.compileStatement(relationshipSql);
                relationshipStat.bindLong(1, ret); // Use the newly inserted friend's ID
                relationshipStat.bindLong(2, 1); // Assuming default group ID is 1
                relationshipStat.executeInsert();
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ret;
    }

    public void editFriend(FriendUnit friend){
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("f_name", friend.getName());
        values.put("f_label", friend.getLabel());
        db.update("db_friends", values, "f_id=?", new String[]{friend.getID()+""});
        db.close();
    }

    public void editGroup(GroupUnit group){
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fg_name", group.getGroupName());
        values.put("fg_order", group.getOrder());
        db.update("db_friendsgroup", values, "fg_id=?", new String[]{group.getID()+""});
        db.close();
    }


    public long addGroup(String name){
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        String sql = "insert into db_friendsgroup(fg_name,fg_order,fg_create_time) " +
                "values(?,?,?)";

        long ret = 0;
        //sql = "insert into ex_user(eu_login_name,eu_create_time,eu_update_time) values(?,?,?)";
        SQLiteStatement stat = db.compileStatement(sql);
        db.beginTransaction();
        try {
            stat.bindString(1, name);
            stat.bindString(2, "2");
            stat.bindString(3, ForString.date2string(new Date()));
            ret = stat.executeInsert();
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ret;
    }

    @SuppressLint("Range")
    public ArrayList<GroupUnit> getAllGroupInfo() {
        SQLiteDatabase db = liteDatabase.getWritableDatabase();
        ArrayList<GroupUnit> Groups = new ArrayList<>();
        Cursor groupCursor = null;
        Cursor friendCursor = null;

        try {
            // 获取所有组的信息
            String groupSql = "SELECT * FROM db_friendsgroup ORDER BY fg_create_time ASC";
            groupCursor = db.rawQuery(groupSql, null);

            // 遍历每个组
            while (groupCursor.moveToNext()) {
                GroupUnit groupUnit = new GroupUnit();
                int groupId = groupCursor.getInt(groupCursor.getColumnIndex("fg_id"));
                groupUnit.setId(groupId);
                groupUnit.setGroupName(groupCursor.getString(groupCursor.getColumnIndex("fg_name")));
                groupUnit.setOrder(groupCursor.getInt(groupCursor.getColumnIndex("fg_order")));
                groupUnit.setDate(groupCursor.getString(groupCursor.getColumnIndex("fg_create_time")));

                // 获取该组的所有好友信息
                String friendSql = "SELECT db_friends.f_id, db_friends.f_name, db_friends.f_label, db_friends.f_create_time " +
                        "FROM db_friends " +
                        "JOIN db_relationship ON db_friends.f_id = db_relationship.rfriend_id " +
                        "WHERE db_relationship.rgroup_id = ?";
                friendCursor = db.rawQuery(friendSql, new String[]{String.valueOf(groupId)});
                ArrayList<FriendUnit> friends = new ArrayList<>();

                while (friendCursor.moveToNext()) {
                    FriendUnit friendUnit = new FriendUnit();
                    friendUnit.setID(friendCursor.getInt(friendCursor.getColumnIndex("f_id")));
                    friendUnit.setName(friendCursor.getString(friendCursor.getColumnIndex("f_name")));
                    friendUnit.setLable(friendCursor.getString(friendCursor.getColumnIndex("f_label")));
                    friendUnit.setTime(friendCursor.getString(friendCursor.getColumnIndex("f_create_time")));
                    friends.add(friendUnit);
                }

                groupUnit.setFriends(friends);
                Groups.add(groupUnit);

                if (friendCursor != null) {
                    friendCursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (groupCursor != null) {
                groupCursor.close();
            }
            if (friendCursor != null) {
                friendCursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return Groups;
    }


}
