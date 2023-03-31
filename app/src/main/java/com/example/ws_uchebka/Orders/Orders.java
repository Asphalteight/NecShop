package com.example.ws_uchebka.Orders;

public class Orders {

    private final int Id;
    private final int IdProduct;
    private final int Count;
    private final String Name;
    private final String Phone;
    private final String Date;
    private final String IsAccepted;

    public Orders(int id, int orderId, int count, String name, String phone, String orderDate, String isAccepted) {
        this.Id = id;
        this.IdProduct = orderId;
        this.Count = count;
        this.Name = name;
        this.Phone = phone;
        this.Date = orderDate;
        this.IsAccepted = isAccepted;
    }

    public int getId() {
        return Id;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public int getCount() { return Count; }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDate() { return Date; }

    public String getAccept() { return IsAccepted; }
}
