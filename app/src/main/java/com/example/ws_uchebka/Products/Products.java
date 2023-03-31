package com.example.ws_uchebka.Products;

public class Products {

    private final int Id;
    private final String Name;
    private final String Category;
    private final String Description;
    private final int Price;
    private final int Count;

    public Products(int Id, String Name, String Category, String Description, int Price, int Count) {
        this.Id = Id;
        this.Name = Name;
        this.Category = Category;
        this.Description = Description;
        this.Price = Price;
        this.Count = Count;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getPrice() {
        return Price;
    }

    public int getCount() {
        return Count;
    }
}
