package be.kuleuven.gt.app3.ForGroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.kuleuven.gt.app3.ForNote.NoteUnit;
import be.kuleuven.gt.app3.ForNote.ReAdapter;
import be.kuleuven.gt.app3.R;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import be.kuleuven.gt.app3.storeAndupdate.storeNote;
import io.reactivex.annotations.NonNull;

public class GroupAdapter extends BaseAdapter {

    private ArrayList<GroupUnit> groups;
    private storeNote storeNote;
    private LayoutInflater layoutInflater;
    private Context context;
    private boolean[] showControl;// record item whether expand

    public GroupAdapter(Context context, ArrayList<GroupUnit> groups) {
        super();
        this.groups = groups;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        showControl = new boolean[groups.size()];
        storeNote = new storeNote(this.context);
    }

    //according the return num to determine how much bar will be generated
    public int getCount(){
        return groups.size();
    }

    public GroupUnit getItem(int position){
        return groups.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public void setGroups(ArrayList<GroupUnit> groups){this.groups = groups;}

    public View getView(final int position,View convertView,ViewGroup parent){
        Log.i("taggg","num+1");
        ViewHolder holder = null;
//        if(convertView == null){
        convertView = LayoutInflater.from(context).inflate(R.layout.grouptextview,parent,false);
        holder = new ViewHolder(convertView);
        for(FriendUnit friendUnit : groups.get(position).getFriends()){
            View friendItemView = LayoutInflater.from(context).inflate(R.layout.groupitembar,
                    holder.friendsContainer, false);
            TextView friendName = friendItemView.findViewById(R.id.ItemName);
            friendName.setText(friendUnit.getName());
            friendItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("taggg","click");
                }
            });

            // Add the friend item view to the friendsContainer
            holder.friendsContainer.addView(friendItemView);
        }
        convertView.setTag(holder);
//        }
//        else{
//            holder = (ViewHolder) convertView.getTag();
//        }
        holder.groupName.setText(groups.get(position).getGroupName());
        holder.showArea.setTag(position);

        if(showControl[position]){
            holder.hideArea.setVisibility(View.VISIBLE);
            holder.iconView.setImageResource(R.drawable.ic_arrow_down);

        }else{
            holder.iconView.setImageResource(R.drawable.ic_arrow_right);
            holder.hideArea.setVisibility(View.GONE);
        }

        holder.showArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (Integer) v.getTag();
                if(showControl[tag]){
                    showControl[tag] = false;
                }else{
                    showControl[tag] =true;
                }
                notifyDataSetChanged();
            }
        });


        convertView.setOnClickListener(null);


        return convertView;
    }




    private class ViewHolder{
        private LinearLayout showArea;
        private RelativeLayout hideArea;
        private LinearLayout friendsContainer;
        private TextView groupName;
        private ImageView iconView;

        public ViewHolder(View v){
            this.showArea = (LinearLayout) v.findViewById(R.id.layout_showArea);
            this.groupName = (TextView) v.findViewById(R.id.group_name);
            this.hideArea = (RelativeLayout) v.findViewById(R.id.layout_hideArea);
            this.iconView = (ImageView) v.findViewById(R.id.iconArror);
            this.friendsContainer = (LinearLayout) v.findViewById(R.id.friends_container);
        }


    }




}