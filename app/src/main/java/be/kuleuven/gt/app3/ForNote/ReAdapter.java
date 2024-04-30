package be.kuleuven.gt.app3.ForNote;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.gt.app3.R;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.myViewHolder> {

   private ArrayList<NoteUnit> Notes;

    private LayoutInflater layoutInflater;
   private Context mContext;
   private String word;
    private OnRecyclerViewItemClickListener mOnItemClickListener ;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener ;

    public ReAdapter(Context context,ArrayList<NoteUnit> noteUnits) {
        this.mContext=context;
        this.Notes = noteUnits;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setArray(ArrayList<NoteUnit> notes){
        this.Notes = notes;
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(NoteUnit) v.getTag());
        }
    }

    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取数据,点的时候传数据
            mOnItemLongClickListener.onItemLongClick(v,(NoteUnit)v.getTag());
        }
        return true;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , NoteUnit note);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view , NoteUnit note);
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
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

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        mContext = parent.getContext();
        // 实例化展示的view
        if(viewType == 1){
            v = layoutInflater.inflate(R.layout.cardview,parent,false);
        }
        else{
            v = layoutInflater.inflate(R.layout.cardview,parent,false);
        }
        // 实例化myViewHolder
        myViewHolder viewHolder = new myViewHolder(v);
        v.setOnClickListener(this::onClick);
        v.setOnLongClickListener(this::onLongClick);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final NoteUnit noteUnit = Notes.get(position);
        noteUnit.setPosition(position);// set the note's position in the arraylist
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(noteUnit);
        //add text to the note according to array's data
        holder.title.setText(Notes.get(position).getTitle());
        holder.context.setText(Notes.get(position).getContent());
        holder.Tag.setText(Notes.get(position).getGroupName());
        holder.EditDate.setText(Notes.get(position).getUpdateTime());


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

        public myViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.cardText);
            context = (TextView) itemView.findViewById(R.id.cardContext);
            EditDate = (TextView) itemView.findViewById(R.id.cardTime);
            Tag = (TextView) itemView.findViewById(R.id.cardTag);
        }


    }


}


