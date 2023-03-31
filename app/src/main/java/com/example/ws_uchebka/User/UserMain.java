package com.example.ws_uchebka.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.Products.InsertProduct;
import com.example.ws_uchebka.Products.ProductListAdapter;
import com.example.ws_uchebka.Products.Products;
import com.example.ws_uchebka.R;

import java.util.ArrayList;

public class UserMain extends AppCompatActivity {

    private RecyclerView productsRV;
    DbHandler productDb;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        TextView userName = findViewById(R.id.userProfileName);
        prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        String name = prefs.getString("userName", "name");
        userName.setText(name);

        productDb = new DbHandler(UserMain.this);
        updateList();

        productsRV.addOnItemTouchListener(
                new ProductListAdapter.RecyclerItemClickListener(this, productsRV ,new ProductListAdapter.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView id = view.findViewById(R.id.itemProductId);
                        Intent intent = new Intent(getApplicationContext(), InsertOrder.class);
                        intent.putExtra("id", id.getText().toString());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        Button exit = findViewById(R.id.btnExit);
        exit.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("userName", "");
            editor.putString("userPhone", "");
            editor.apply();
            finish();
        });
    }

    @Override
    protected void onResume() {
        updateList();
        super.onResume();
    }

    private void updateList(){
        ArrayList<Products> productsArrayList = productDb.readProducts();
        ProductListAdapter productsListAdapter = new ProductListAdapter(productsArrayList);
        productsRV = findViewById(R.id.userProductsMainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserMain.this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(linearLayoutManager);

        productsRV.setAdapter(productsListAdapter);
    }
}
