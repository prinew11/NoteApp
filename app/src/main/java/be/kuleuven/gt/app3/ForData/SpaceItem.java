package be.kuleuven.gt.app3.ForData;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// set the space of the cardview
public class SpaceItem extends RecyclerView.ItemDecoration{
    private int space;

    public SpaceItem(int space){
        this.space = space;
    }

    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.top = space-5;
        outRect.right = space;
        outRect.bottom = space-5;
    }

}
