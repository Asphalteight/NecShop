package com.example.ws_uchebka.Products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.R;
import com.example.ws_uchebka.Orders.OrderActivity;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView productsRV;
    DbHandler productDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        productDb = new DbHandler(ProductActivity.this);
        updateList();
        Button openInsert = findViewById(R.id.btnOpenAddProduct);
        openInsert.setOnClickListener(v -> {
            Intent intent = new Intent(this, InsertProduct.class);
            startActivity(intent);
        });

        productsRV.addOnItemTouchListener(
                new ProductListAdapter.RecyclerItemClickListener(this, productsRV ,new ProductListAdapter.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView id = view.findViewById(R.id.itemProductId);
                        Intent intent = new Intent(getApplicationContext(), ProductInfo.class);
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
        ArrayList<Products> productsArrayList = productDb.readProducts();
        ProductListAdapter productsListAdapter = new ProductListAdapter(productsArrayList);
        productsRV = findViewById(R.id.productsMainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductActivity.this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(linearLayoutManager);

        productsRV.setAdapter(productsListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        menu.add(0, 1, 1, "Товары");
        menu.add(0, 2, 2,"Заказы");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 2) {
            Intent intent = new Intent(this, OrderActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }
}