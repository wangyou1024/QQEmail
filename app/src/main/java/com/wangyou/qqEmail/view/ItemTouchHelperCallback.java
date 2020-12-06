package com.wangyou.qqEmail.view;

import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.wangyou.qqEmail.interf.onMoveAndSwipedListener;

/**
 * Created by zhang on 2016.08.21.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final int TYPE_NORMAL = 1;

    private onMoveAndSwipedListener moveAndSwipedListener;

    public ItemTouchHelperCallback(onMoveAndSwipedListener listener) {
        this.moveAndSwipedListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(this.getClass().getSimpleName(),"getMovementFlags start");
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // for recyclerView with gridLayoutManager, support drag all directions, not support swipe
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            Log.i(this.getClass().getSimpleName(),"getMovementFlags end");
            return makeMovementFlags(dragFlags, swipeFlags);

        } else {
            // for recyclerView with linearLayoutManager, support drag up and down, and swipe lift and right
            if (viewHolder.getItemViewType() == TYPE_NORMAL) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                Log.i(this.getClass().getSimpleName(),"getMovementFlags end");
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                Log.i(this.getClass().getSimpleName(),"getMovementFlags end");
                return 0;
            }
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i(this.getClass().getSimpleName(),"onMove start");
        // If the 2 items are not the same type, no dragging
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            Log.i(this.getClass().getSimpleName(),"onMove end");
            return false;
        }
        moveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        Log.i(this.getClass().getSimpleName(),"onMove end");
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
