package com.walinns.walinnsinnovation.waltics_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walinns.walinnsinnovation.waltics_test.BeanClass.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    List<NoteItem> nodeItemList = new ArrayList<>();
    Context mContext;
    Listener mListener;
    private GestureDetector gestureDetector;
    private static final int MIN_SWIPE_DISTANCE=300;
    private int oldX,newX;

    public ListAdapter(Context context, List<NoteItem> noteItems,Listener listener){
        this.mContext = context;
        this.nodeItemList = noteItems;
        this.mListener = listener;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txt_note.setText(nodeItemList.get(position).getNote_text());
        holder.note_cat.setText(nodeItemList.get(position).getNote_cat());
        if(nodeItemList.get(position).getNote_cat().equals("Home")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.home_cat));
        }else if(nodeItemList.get(position).getNote_cat().equals("Office")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.office));

        }else if(nodeItemList.get(position).getNote_cat().equals("Extras")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.extra));

        }else if(nodeItemList.get(position).getNote_cat().equals("Other")){
            holder.note_cat.setBackgroundColor(mContext.getResources().getColor(R.color.other));

        }
        holder.itemView.setOnTouchListener(new OnSwipeTouchListener(mContext){

            @Override
            public void onClick(View view , MotionEvent event) {
                super.onClick(view,event);
                // your on click here
                System.out.println("OnTouch Listner :"+ "Single click");
                mListener.onClick(view, event,position,nodeItemList , "single");

            }

            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                // your on onDoubleClick here
                System.out.println("OnTouch Listner :"+ "Double click");
            }

            @Override
            public void onLongClick(View view , MotionEvent event) {
                super.onLongClick(view,event);
                // your on onLongClick here
                System.out.println("OnTouch Listner :"+ "Long click");
                mListener.onClick(view, event,position,nodeItemList , "long");
            }

            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                // your swipe up here
                System.out.println("OnTouch Listner :"+ "swipe click");
            }

            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
                // your swipe down here.
                System.out.println("OnTouch Listner :"+ "Swipe down");
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                // your swipe left here.
                System.out.println("OnTouch Listner :"+ "swipe left");
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                // your swipe right here.
                System.out.println("OnTouch Listner :"+ "Swipe right");
            }

        });


//        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//
//
////                if (gestureDetector.onTouchEvent(motionEvent)) {
////                    // single tap
////                    System.out.println("OnTouch Listner :"+ "Single click");
////                    return true;
////                } else {
////                    // your code for move and drag
////                    //mListener.onClick(view, motionEvent,position,nodeItemList);
////                    System.out.println("OnTouch Listner :"+ "move and drasag click");
////
////                }
//                return false;
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return nodeItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_note, note_cat;

        public MyViewHolder(View view) {
            super(view);
            txt_note = (TextView)view.findViewById(R.id.note_text);
            note_cat = (TextView)view.findViewById(R.id.note_cat);

        }
    }
    public interface Listener {
        void onClick(View view, MotionEvent motionEvent , int position , List<NoteItem> noteItemList,String click_type);
    }
    public class OnSwipeTouchListener implements View.OnTouchListener {

        private GestureDetector gestureDetector;
        public View mView;

        public OnSwipeTouchListener(Context c) {
            gestureDetector = new GestureDetector(c, new GestureListener());
        }

        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            this.mView =view;
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick(mView , e);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                onDoubleClick();
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                onLongClick(mView , e);
                super.onLongPress(e);
            }

            // Determines the fling velocity and then fires the appropriate swipe event accordingly
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeDown();
                            } else {
                                onSwipeUp();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeUp() {
        }

        public void onSwipeDown() {
        }

        public void onClick(View view , MotionEvent motionEvent) {

        }

        public void onDoubleClick() {

        }

        public void onLongClick(View view , MotionEvent motionEvent) {

        }
    }

}
