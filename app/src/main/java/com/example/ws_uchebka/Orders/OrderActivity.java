package com.example.ws_uchebka.Orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ws_uchebka.Products.ProductActivity;
import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView ordersRV;
    DbHandler orderDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        orderDb = new DbHandler(OrderActivity.this);
        updateList();

        ordersRV.addOnItemTouchListener(
                new OrderListAdapter.RecyclerItemClickListener(this, ordersRV ,new OrderListAdapter.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView id = view.findViewById(R.id.itemOrderId);
                        Intent intent = new Intent(getApplicationContext(), OrderInfo.class);
                        intent.putExtra("id", id.getText().toString());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    protected void onResume() {
        updateList();
        super.onResume();
    }

    private void updateList(){
        ArrayList<Orders> ordersArrayList = orderDb.readOrders();
        if (ordersArrayList == null) return;
        OrderListAdapter ordersListAdapter = new OrderListAdapter(ordersArrayList, this);
        ordersRV = findViewById(R.id.ordersMainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this, RecyclerView.VERTICAL, false);
        ordersRV.setLayoutManager(linearLayoutManager);

        ordersRV.setAdapter(ordersListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0, 1, 1, "Товары");
        menu.add(0, 2, 2,"Заказы");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1){
            Intent intent = new Intent(this, ProductActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }
}