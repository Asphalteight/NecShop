package com.example.ws_uchebka.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.Products.Products;
import com.example.ws_uchebka.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InsertOrder extends AppCompatActivity {

    private TextView productMaxCount;
    private EditText orderCount;
    private DbHandler dbHandler;
    String productId = "";
    Products product;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_insert);

        TextView productName = findViewById(R.id.textv1);
        TextView productDescription = findViewById(R.id.textv2);
        TextView productCategory = findViewById(R.id.textv3);
        TextView productPrice = findViewById(R.id.textv5);
        productMaxCount = findViewById(R.id.productAvailableCount);
        orderCount = findViewById(R.id.orderInsertCount);
        Button addOrderBtn = findViewById(R.id.btnOrderAdd);
        Button countAdd = findViewById(R.id.btnAddCount);

        dbHandler = new DbHandler(InsertOrder.this);
        Intent intent = getIntent();
        productId = intent.getStringExtra("id");

        DbHandler db = new DbHandler(InsertOrder.this);
        product = db.getProductById(productId);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productCategory.setText(product.getCategory());
        productPrice.setText(String.valueOf(product.getPrice()));
        productMaxCount.setText(String.valueOf(product.getCount()));

        countAdd.setOnClickListener(v -> {
            orderCount.setText(String.valueOf(Integer.parseInt(orderCount.getText().toString()) + 1));
        });

        addOrderBtn.setOnClickListener(v -> {

            String count = orderCount.getText().toString();
            int maxCount = Integer.parseInt(productMaxCount.getText().toString());
            boolean valid = true;

            if (TextUtils.isEmpty(count) || Integer.parseInt(count) == 0) {
                orderCount.setError("Не указано количество");
                valid = false;
            } else {
                if (Integer.parseInt(count) > maxCount) {
                    orderCount.setError("Укажите меньшее количество товара");
                    valid = false;
                }
            }

            if (valid) {
                try {
                    String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());

                    SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
                    String name = prefs.getString("userName", "name");
                    String phone = prefs.getString("userPhone", "phone");

                    dbHandler.addOrder(productId, count, name, phone, timeStamp, "false");
                    db.updateProductCount(productId, String.valueOf(maxCount - Integer.parseInt(count)));

                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Проверьте корректность введенных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
