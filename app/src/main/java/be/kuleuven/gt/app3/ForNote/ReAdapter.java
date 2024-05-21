package be.kuleuven.gt.app3.ForNote;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.kuleuven.gt.app3.MainActivity;
import be.kuleuven.gt.app3.R;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.myViewHolder> {

   private ArrayList<NoteUnit> Notes;

    private LayoutInflater layoutInflater;
   private Context mContext;
   private String word;
    private OnRecyclerViewItemClickListener mOnItemClickListener ;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener ;
    private boolean isDeleteMode;
    private boolean isSearchMode;
    private ArrayList<NoteUnit> deleteNotes;
    private ArrayList<NoteUnit> searchNotes;
    private FragmentActivity activity;

    public ReAdapter(Context context, ArrayList<NoteUnit> noteUnits, FragmentActivity activity) {
        this.mContext=context;
        this.Notes = noteUnits;
        this.layoutInflater = LayoutInflater.from(context);
        this.activity = activity;
        isDeleteMode = false;
        isSearchMode = false;
        deleteNotes = new ArrayList<>();
    }

    public void setArray(ArrayList<NoteUnit> notes){
        this.Notes = notes;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , NoteUnit note);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
        Log.i("taggg","setOnItemClickListener");
    }

    public void onClick(View v) {
        Log.i("taggg","onClick");
        if (mOnItemClickListener != null) {
            //utilize getTag method to get data. When click the view, call onItemClick
            mOnItemClickListener.onItemClick(v,(NoteUnit) v.getTag());
        }
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view , NoteUnit note);
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            //utilize getTag method to get data,pass data when click it
            mOnItemLongClickListener.onItemLongClick(v,(NoteUnit)v.getTag());
        }
        return true;
    }


    public void setNotes(ArrayList<NoteUnit> notes){
        this.Notes = notes;
    }

    public ArrayList<NoteUnit> getNotes(){return Notes;}

    public int getItemViewType(int position){
        if(position < Notes.size()){
            Log.i("Taggg","data");
            return 0;
        }
        else{
            Log.i("Taggg","picture");
            return 1;
        }

    }

    public void setDeleteMode(boolean isDeleteMode){
        this.isDeleteMode = isDeleteMode;
        notifyDataSetChanged();
    }

    public ArrayList<NoteUnit> getSelect(){
        return deleteNotes;
    }

    public void setSelect(ArrayList<NoteUnit> deleteNotes){
        this.deleteNotes = deleteNotes;
    }

    public ArrayList<NoteUnit> getSearchNotes(){
        return searchNotes;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        mContext = parent.getContext();

        if(viewType == 1){
            v = layoutInflater.inflate(R.layout.cardview,parent,false);
        }
        else{
            v = layoutInflater.inflate(R.layout.cardview,parent,false);
        }

        myViewHolder viewHolder = new myViewHolder(v);
        v.setOnClickListener(this::onClick);
        v.setOnLongClickListener(this::onLongClick);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Log.i("taggg","init1");
        final NoteUnit noteUnit = Notes.get(position);
        noteUnit.setPosition(position);// set the note's position in the arraylist
        //data store in itemView's Tag，click it to get the data
        holder.itemView.setTag(noteUnit);
        //add text to the note according to array's data
        holder.title.setText(Notes.get(position).getTitle());
        holder.context.setText(Notes.get(position).getContent());
        holder.Tag.setText(Notes.get(position).getGroupName());
        holder.EditDate.setText(Notes.get(position).getUpdateTime());
        holder.checkBox.setVisibility(isDeleteMode ? View.VISIBLE : View.GONE);


        if(isDeleteMode){
            holder.view.setOnClickListener(null);
            holder.view.setOnLongClickListener(null);

            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });

            holder.checkBox.setChecked(deleteNotes.contains(Notes.get(position)));

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        deleteNotes.add(Notes.get(holder.getAdapterPosition()));
                    }
                    else{
                        deleteNotes.remove(Notes.get(holder.getAdapterPosition()));
                    }
                }
            });


        }  else{
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.view.setOnClickListener(this::onClick);
            holder.view.setOnLongClickListener(this::onLongClick);
        }

    }

    @Override
    public int getItemCount() {
        return Notes == null ? 0 : Notes.size();
    }

    public void addCard(NoteUnit note){
        //add data to the array
        Notes.add(note);
        notifyItemInserted(Notes.size());
    }

    public void editCard(NoteUnit noteUnit){
        int position = noteUnit.getPosition();
        Notes.set(position,noteUnit);
        notifyItemChanged(position);

    }








    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView context;
        TextView EditDate;
        TextView Tag;
        CheckBox checkBox;
        View view;

        public myViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.cardText);
            context = (TextView) itemView.findViewById(R.id.cardContext);
            EditDate = (TextView) itemView.findViewById(R.id.cardTime);
            Tag = (TextView) itemView.findViewById(R.id.cardTag);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            view = itemView;
        }


    }


}


