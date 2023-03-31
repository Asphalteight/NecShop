package com.example.ws_uchebka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.User.UserMain;

public class Authorize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize);

        EditText name = findViewById(R.id.userName);
        EditText phone = findViewById(R.id.userPhone);


        Button btnAuth = findViewById(R.id.btnAuth);
        btnAuth.setOnClickListener(v -> {
            boolean valid = true;

            if (TextUtils.isEmpty(name.getText())){
                name.setError("Укажите имя");
                valid = false;
            }
            else
            if (TextUtils.isEmpty(phone.getText())){
                phone.setError("Укажите номер телефона");
                valid = false;
            }

            if (valid) {
                SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userName", name.getText().toString());
                editor.putString("userPhone", phone.getText().toString());
                editor.apply();

                Intent intent = new Intent(this, UserMain.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
