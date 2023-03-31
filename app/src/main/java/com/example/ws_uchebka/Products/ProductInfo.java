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

public class ProductInfo extends AppCompatActivity {

    EditText name, desc, price, count;
    TextView categ;
    Button save, delete, pick;
    String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);

        name = findViewById(R.id.productInfoName);
        desc = findViewById(R.id.productInfoDescription);
        categ = findViewById(R.id.productInfoCategory);
        price = findViewById(R.id.productInfoPrice);
        count = findViewById(R.id.productInfoCount);

        save = findViewById(R.id.btnProductInfoSave);
        delete = findViewById(R.id.btnProductInfoDelete);
        pick = findViewById(R.id.btnPickCategoryProductInfo);

        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        DbHandler db = new DbHandler(ProductInfo.this);
        Products product = db.getProductById(productId);

        name.setText(product.getName());
        desc.setText(product.getDescription());
        categ.setText(product.getCategory());
        price.setText(String.valueOf(product.getPrice()));
        count.setText(String.valueOf(product.getCount()));

        pick.setOnClickListener(v -> {
            Intent intentCategory = new Intent(this, PickCategory.class);
            startActivityForResult(intentCategory, 200);
        });

        save.setOnClickListener(v -> {
            String productName;
            String productCategory;
            String productDescription;
            String productPrice;
            String productCount;
            boolean valid = true;

            productName = name.getText().toString();
            productCategory = categ.getText().toString();
            productDescription = desc.getText().toString();
            productPrice = price.getText().toString();
            productCount = count.getText().toString();


            if (TextUtils.isEmpty(productName)) {
                name.setError("Не указано наименование");
                valid = false;
            } else {
                if (TextUtils.isEmpty(productPrice)) {
                    price.setError("Не указана цена");
                    valid = false;
                } else {
                    if (TextUtils.isEmpty(productCount)) {
                        count.setError("Не указано количество");
                        valid = false;
                    }

                }
            }
            int pPrice;
            int pCount;
            if (valid) {
                try {
                    pPrice = Integer.parseInt(productPrice);
                    pCount = Integer.parseInt(productCount);
                    db.updateProduct(productId, productName, productCategory, productDescription, pPrice, pCount);
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Проверьте корректность введенных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(v -> {
            db.deleteProduct(productId);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String category = data.getStringExtra("category");
        categ.setText(category);
    }
}
