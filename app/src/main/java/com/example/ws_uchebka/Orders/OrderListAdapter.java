package com.example.ws_uchebka.Orders;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.Products.Products;
import com.example.ws_uchebka.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final ArrayList<Orders> ordersArrayList;
    private final Context context;
    private int position;
    public OrderListAdapter(ArrayList<Orders> ordersList, Context context) {
        this.ordersArrayList = ordersList;
        this.context = context;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Orders model = ordersArrayList.get(position);

        DbHandler db = new DbHandler(context);
        Products product = db.getProductById(String.valueOf(model.getIdProduct()));
        holder.productId.setText(String.valueOf(model.getId()));
        holder.productName.setText(product.getName());
        holder.clientName.setText(model.getName());
        holder.isAccepted.setChecked(Boolean.parseBoolean(model.getAccept()));

        holder.itemView.setOnLongClickListener(v -> {
            setPosition(holder.getAdapterPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView productId;
        private final TextView productName;
        private final TextView clientName;
        private final CheckBox isAccepted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.itemOrderId);
            productName = itemView.findViewById(R.id.itemOrderProductName);
            clientName = itemView.findViewById(R.id.itemOrderClientName);
            isAccepted = itemView.findViewById(R.id.itemOrderAccepted);
        }
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private final OnItemClickListener mListener;
        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onLongItemClick(View view, int position);
        }
    }
}