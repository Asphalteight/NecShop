package com.example.ws_uchebka.Products;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.DbHandler;
import com.example.ws_uchebka.R;

public class InsertProduct extends AppCompatActivity {

    TextView productCategoryEdt;
    private EditText productNameEdt, productDescriptionEdt, productPriceEdt, productCountEdt;
    private DbHandler dbHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_insert);

        productNameEdt = findViewById(R.id.productInsertName);
        productCategoryEdt = findViewById(R.id.productInsertCategory);
        productDescriptionEdt = findViewById(R.id.productInsertDescription);
        productPriceEdt = findViewById(R.id.productInsertPrice);
        productCountEdt = findViewById(R.id.productInsertCount);

        Button addProductBtn = findViewById(R.id.btnAddProduct);
        Button btnPickCategory = findViewById(R.id.btnPickCategoryInsertProduct);

        dbHandler = new DbHandler(InsertProduct.this);

        addProductBtn.setOnClickListener(v -> {
            String productName, productCategory, productDescription, productPrice, productCount;
            boolean valid = true;

            productName = productNameEdt.getText().toString();
            productCategory = productCategoryEdt.getText().toString();
            productDescription = productDescriptionEdt.getText().toString();
            productPrice = productPriceEdt.getText().toString();
            productCount = productCountEdt.getText().toString();

            if (TextUtils.isEmpty(productName)) {
                productNameEdt.setError("Не указано наименование");
                valid = false;
            } else {
                if (TextUtils.isEmpty(productPrice)) {
                    productPriceEdt.setError("Не указана цена");
                    valid = false;
                } else {
                    if (TextUtils.isEmpty(productCount)) {
                        productCountEdt.setError("Не указано количество");
                        valid = false;
                    }

                }
            }
            int Price, Count;
            if (valid) {
                try {
                    Price = Integer.parseInt(productPrice);
                    Count = Integer.parseInt(productCount);
                    dbHandler.addProduct(productName, productCategory, productDescription, Price, Count);
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Проверьте корректность введенных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPickCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, PickCategory.class);
            startActivityForResult(intent, 200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String category = data.getStringExtra("category");
        productCategoryEdt.setText(category);
    }
}
