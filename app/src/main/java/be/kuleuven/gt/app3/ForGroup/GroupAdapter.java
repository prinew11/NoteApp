package be.kuleuven.gt.app3.ForGroup;

import android.content.Context;
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

import io.reactivex.annotations.NonNull;

public class GroupAdapter extends BaseAdapter {

    private ArrayList<GroupUnit> groups;
    private LayoutInflater layoutInflater;
    private Context context;
    private boolean[] showControl;// record item whether expand

    public GroupAdapter(Context context, ArrayList<GroupUnit> groups) {
        super();
        this.groups = groups;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        showControl = new boolean[groups.size()];
    }

    public int getCount(){
        return groups.size();
    }

    public GroupUnit getItem(int position){
        return groups.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position,View convertView,ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grouptextview,parent,false);
            holder = new ViewHolder();
            holder.showArea = (LinearLayout) convertView.findViewById(R.id.layout_showArea);
            holder.groupName = (TextView) convertView.findViewById(R.id.group_name);
            holder.friendsName = (TextView) convertView.findViewById(R.id.friendsname);
            holder.hideArea = (RelativeLayout) convertView.findViewById(R.id.layout_hideArea);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.showArea.setTag(position);
        if(showControl[position]){
            holder.hideArea.setVisibility(View.VISIBLE);
        }else{
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



        return convertView;
    }














    private class ViewHolder{
        private LinearLayout showArea;
        private RelativeLayout hideArea;
        private TextView groupName;
        private TextView friendsName;

    }




}
