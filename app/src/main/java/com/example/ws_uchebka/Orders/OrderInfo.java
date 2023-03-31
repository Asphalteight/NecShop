package com.example.ws_uchebka.Orders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.R;

public class OrderInfo extends AppCompatActivity {

    EditText idProduct, count, name, phone;
    TextView date;
    Button save, delete;
    CheckBox isAccepted;
    String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);

        idProduct = findViewById(R.id.orderInfoIdProduct);
        count = findViewById(R.id.orderInfoCount);
        name = findViewById(R.id.orderInfoName);
        phone = findViewById(R.id.orderInfoPhone);
        date = findViewById(R.id.orderInfoDate);
        isAccepted = findViewById(R.id.orderInfoIsAccepted);

        save = findViewById(R.id.btnOrderInfoSave);
        delete = findViewById(R.id.btnOrderInfoDelete);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("id");
        DbHandler db = new DbHandler(OrderInfo.this);
        Orders order = db.getOrderById(orderId);

        idProduct.setText(String.valueOf(order.getIdProduct()));
        count.setText(String.valueOf(order.getCount()));
        name.setText(order.getName());
        phone.setText(order.getPhone());
        date.setText(order.getDate());
        isAccepted.setChecked(Boolean.parseBoolean(order.getAccept()));

        save.setOnClickListener(v -> {
            String orderIdProduct, orderCount, orderName, orderPhone, orderIsAccepted;
            boolean valid = true;

            orderIdProduct = idProduct.getText().toString();
            orderCount = count.getText().toString();
            orderName = name.getText().toString();
            orderPhone = phone.getText().toString();
            orderIsAccepted = String.valueOf(isAccepted.isChecked());

            if (TextUtils.isEmpty(orderIdProduct)) {
                idProduct.setError("Не указан ID товара");
                valid = false;
            } else {
                if (TextUtils.isEmpty(orderCount)) {
                    count.setError("Не указано количество");
                    valid = false;
                }
            }
            int idProduct, count;
            if (valid) {
                try {
                    db.updateOrder(orderId, orderIdProduct, orderCount, orderName, orderPhone, orderIsAccepted);
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Проверьте корректность введенных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(v -> {
            db.deleteOrder(orderId);
            finish();
        });
    }
}
