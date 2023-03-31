package com.example.ws_uchebka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ws_uchebka.Products.Products;
import com.example.ws_uchebka.Orders.Orders;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "ShopDatabase";
    private static final int DB_VERSION = 1;

    private static final String PRODUCTS_TABLE = "Products";
    private static final String PRODUCT_ID_COL = "id";
    private static final String PRODUCT_NAME_COL = "name";
    private static final String PRODUCT_CATEGORY_COL = "category";
    private static final String PRODUCT_DESCRIPTION_COL = "description";
    private static final String PRODUCT_PRICE_COL = "price";
    private static final String PRODUCT_COUNT_COL = "count";

    private static final String ORDERS_TABLE = "Orders";
    private static final String ORDER_ID_COL = "id";
    private static final String ORDER_PRODUCT_ID_COL = "idProduct";
    private static final String ORDER_COUNT_COL = "count";
    private static final String ORDER_NAME_COL = "name";
    private static final String ORDER_PHONE_COL = "phone";
    private static final String ORDER_DATE_COL = "date";
    private static final String ORDER_IS_ACCEPTED_COL = "isAccepted";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProducts = "CREATE TABLE " + PRODUCTS_TABLE + " ("
                + PRODUCT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME_COL + " TEXT,"
                + PRODUCT_CATEGORY_COL + " TEXT,"
                + PRODUCT_DESCRIPTION_COL + " TEXT,"
                + PRODUCT_PRICE_COL + " INTEGER,"
                + PRODUCT_COUNT_COL + " INTEGER)";
        String createOrders  = "CREATE TABLE " + ORDERS_TABLE + " ("
                + ORDER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_PRODUCT_ID_COL + " INTEGER,"
                + ORDER_COUNT_COL + " INTEGER,"
                + ORDER_NAME_COL + " TEXT,"
                + ORDER_PHONE_COL + " TEXT,"
                + ORDER_DATE_COL + " TEXT,"
                + ORDER_IS_ACCEPTED_COL + " TEXT,"
                + "FOREIGN KEY(" + ORDER_PRODUCT_ID_COL + ") REFERENCES " + PRODUCTS_TABLE + "(" + PRODUCT_ID_COL + "))";
        db.execSQL(createProducts);
        db.execSQL(createOrders);
    }

    public void addProduct(String Name, String Category, String Description, int Price, int Count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME_COL, Name);
        values.put(PRODUCT_CATEGORY_COL, Category);
        values.put(PRODUCT_DESCRIPTION_COL, Description);
        values.put(PRODUCT_PRICE_COL, Price);
        values.put(PRODUCT_COUNT_COL, Count);

        db.insert(PRODUCTS_TABLE, null, values);
        db.close();
    }

    public void updateProduct(String id, String Name, String Category, String Description, int Price, int Count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME_COL, Name);
        values.put(PRODUCT_CATEGORY_COL, Category);
        values.put(PRODUCT_DESCRIPTION_COL, Description);
        values.put(PRODUCT_PRICE_COL, Price);
        values.put(PRODUCT_COUNT_COL, Count);

        db.update(PRODUCTS_TABLE, values, "id=?", new String[]{id});
        db.close();
    }

    public void updateProductCount(String id, String count){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_COUNT_COL, count);

        db.update(PRODUCTS_TABLE, values, "id=?", new String[]{id});
        db.close();
    }
    public void deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(PRODUCTS_TABLE, "id=?", new String[]{id});
        db.close();
    }

    public ArrayList<Products> readProducts() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + PRODUCTS_TABLE, null);
        ArrayList<Products> productsArrayList = new ArrayList<>();

        if (cursorProducts.moveToFirst()) {
            do {
                productsArrayList.add(new Products(
                        Integer.parseInt(cursorProducts.getString(0)),
                        cursorProducts.getString(1),
                        cursorProducts.getString(2),
                        cursorProducts.getString(3),
                        Integer.parseInt(cursorProducts.getString(4)),
                        Integer.parseInt(cursorProducts.getString(5)))
                );
            } while (cursorProducts.moveToNext());
        }
        cursorProducts.close();
        return productsArrayList;
    }

    public Products getProductById(String id) {
        Products found = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + PRODUCTS_TABLE + " WHERE id=?", new String[]{id});
        if (cursorProducts.moveToFirst()) {
            found = new Products(
                    Integer.parseInt(cursorProducts.getString(0)),
                    cursorProducts.getString(1),
                    cursorProducts.getString(2),
                    cursorProducts.getString(3),
                    Integer.parseInt(cursorProducts.getString(4)),
                    Integer.parseInt(cursorProducts.getString(5)));

        }
        return found;
    }

    public void addOrder(String IdProduct, String Count, String Name, String Phone, String Date, String IsAccepted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDER_PRODUCT_ID_COL, IdProduct);
        values.put(ORDER_COUNT_COL, Count);
        values.put(ORDER_NAME_COL, Name);
        values.put(ORDER_PHONE_COL, Phone);
        values.put(ORDER_DATE_COL, Date);
        values.put(ORDER_IS_ACCEPTED_COL, IsAccepted);
        db.insert(ORDERS_TABLE, null, values);
        db.close();
    }

    public void updateOrder(String Id, String IdProduct, String Count, String Name, String Phone, String IsAccepted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDER_PRODUCT_ID_COL, IdProduct);
        values.put(ORDER_COUNT_COL, Count);
        values.put(ORDER_NAME_COL, Name);
        values.put(ORDER_PHONE_COL, Phone);
        values.put(ORDER_IS_ACCEPTED_COL, IsAccepted);

        db.update(ORDERS_TABLE, values, "id=?", new String[]{Id});
        db.close();
    }

    public void deleteOrder(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ORDERS_TABLE, "id=?", new String[]{id});
        db.close();
    }

    public ArrayList<Orders> readOrders() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + ORDERS_TABLE, null);
        ArrayList<Orders> productsArrayList = new ArrayList<>();

        if (cursorProducts.moveToFirst()) {
            do {
                productsArrayList.add(new Orders(
                        Integer.parseInt(cursorProducts.getString(0)),
                        Integer.parseInt(cursorProducts.getString(1)),
                        Integer.parseInt(cursorProducts.getString(2)),
                        cursorProducts.getString(3),
                        cursorProducts.getString(4),
                        cursorProducts.getString(5),
                        cursorProducts.getString(6)
                ));
            } while (cursorProducts.moveToNext());
        }
        cursorProducts.close();
        return productsArrayList;
    }

    public Orders getOrderById(String id) {
        Orders found = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + ORDERS_TABLE + " WHERE id=?", new String[]{id});
        if (cursorProducts.moveToFirst()) {
            found = new Orders(
                    Integer.parseInt(cursorProducts.getString(0)),
                    Integer.parseInt(cursorProducts.getString(1)),
                    Integer.parseInt(cursorProducts.getString(2)),
                    cursorProducts.getString(3),
                    cursorProducts.getString(4),
                    cursorProducts.getString(5),
                    cursorProducts.getString(6)
            );
        }
        return found;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE);
        onCreate(db);
    }
}