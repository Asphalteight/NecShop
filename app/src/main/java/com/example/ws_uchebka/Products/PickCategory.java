package com.example.ws_uchebka.Products;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ws_uchebka.R;

public class PickCategory extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list);

        Resources r = getResources();
        String[] stationsArray = r.getStringArray(R.array.categories);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.categories_list_item, stationsArray);
        ListView CategoriesList;
        CategoriesList = findViewById(R.id.categoriesList);
        CategoriesList.setAdapter(aa);

        CategoriesList.setOnItemClickListener((parent, v, position, id) -> {
            String category = (String) ((TextView) v).getText();
            Intent intent = new Intent();
            intent.putExtra("category", category);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
