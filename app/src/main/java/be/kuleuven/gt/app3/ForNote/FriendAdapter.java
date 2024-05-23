package be.kuleuven.gt.app3.ForNote;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import be.kuleuven.gt.app3.ForGroup.FriendUnit;
import be.kuleuven.gt.app3.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.myViewHolder> {
    private ArrayList<FriendUnit> Allfriends;
    private ArrayList<FriendUnit> selectFriend;
    private OnRecyclerViewItemClickListenerFA mOnItemClickListener;
    private LayoutInflater layoutInflater;
    private Context mContext;


    public FriendAdapter(Context context, ArrayList<FriendUnit> Allfriends) {
            this.Allfriends = Allfriends;
            selectFriend = new ArrayList<>();
            this.layoutInflater = LayoutInflater.from(context);
            mContext = context;
    }



        public interface OnRecyclerViewItemClickListenerFA {
            void onItemClick(View view , FriendUnit friend);
        }

        public void setOnItemClickListener(FriendAdapter.OnRecyclerViewItemClickListenerFA listener) {
            this.mOnItemClickListener = listener;
            Log.i("taggg","setOnItemClickListener");
        }

        public void onClick(View v) {
            Log.i("taggg","onClick");
            if (mOnItemClickListener != null) {
                //utilize getTag method to get data. When click the view, call onItemClick
                mOnItemClickListener.onItemClick(v,(FriendUnit) v.getTag());
            }
        }


        public void setFriends(ArrayList<FriendUnit> friends){
            this.Allfriends = friends;
        }

        public ArrayList<FriendUnit> getFriends(){return Allfriends;}

//        public int getItemViewType(int position){
//            if(position < Allfriends.size()){
//                Log.i("Taggg","data");
//                return 0;
//            }
//            else{
//                Log.i("Taggg","picture");
//                return 1;
//            }
//
//        }

//        public void setDeleteMode(boolean isDeleteMode){
//            this.isDeleteMode = isDeleteMode;
//            notifyDataSetChanged();
//        }

        public ArrayList<FriendUnit> getSelect(){
            return selectFriend;
        }

        public void setSelect(ArrayList<FriendUnit> friends){
            this.selectFriend = friends;
        }


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;
            mContext = parent.getContext();
            v = layoutInflater.inflate(R.layout.groupitembar,parent,false);

            myViewHolder viewHolder = new myViewHolder(v);
            v.setOnClickListener(this::onClick);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {

            final FriendUnit friend = Allfriends.get(position);
            friend.setPosition(position);// set the note's position in the arraylist
            //data store in itemView's Tag，click it to get the data
            holder.itemView.setTag(friend);
            Log.i("taggg","size"+Allfriends.size());
            Log.i("taggg",Allfriends.get(position).getName());
            holder.name.setText(Allfriends.get(position).getName());
            //add icon but have not icon now (holder.icon)
            holder.checkBox.setVisibility(View.VISIBLE);

            holder.view.setOnClickListener(null);
            //holder.checkBox.setChecked(selectFriend.contains(Allfriends.get(position)));

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            selectFriend.add(Allfriends.get(holder.getAdapterPosition()));
                        }
                        else{
                            selectFriend.remove(Allfriends.get(holder.getAdapterPosition()));
                        }
                    }
                });


        }

        @Override
        public int getItemCount() {
            return Allfriends == null ? 0 : Allfriends.size();
        }

//        public void addCard(NoteUnit note){
//            //add data to the array
//            Notes.add(note);
//            notifyItemInserted(Notes.size());
//        }









        public class myViewHolder extends RecyclerView.ViewHolder{
            ImageView icon;
            TextView name;
            CheckBox checkBox;
            View view;

            public myViewHolder(View itemView){
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.ItemName);
                icon = (ImageView) itemView.findViewById(R.id.iconFriend);
                checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_share);
                view = itemView;
            }


        }


    }
