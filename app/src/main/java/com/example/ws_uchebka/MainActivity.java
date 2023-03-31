package com.example.ws_uchebka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.Products.ProductActivity;
import com.example.ws_uchebka.User.UserMain;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText code = findViewById(R.id.codeEdt);
        Button asAdmin = findViewById(R.id.btnAdmin);
        Button asGuest = findViewById(R.id.btnGuest);

        asAdmin.setOnClickListener(v -> {
            if (!code.getText().toString().equals("4444")) {
                code.setError("Код неверный!");
                return;
            }
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
            code.setText("");
        });

        asGuest.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
            String name = prefs.getString("userName", "name");
            String phone = prefs.getString("userPhone", "phone");

            Intent intent;
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)){
                intent = new Intent(this, UserMain.class);
            }
            else
                intent = new Intent(this, Authorize.class);
            startActivity(intent);
        });
    }
}
