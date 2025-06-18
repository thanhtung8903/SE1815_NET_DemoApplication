package com.example.se1815_net_demoapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "products")
@Data
@Getter
@Setter
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "price")
    private double price;


    public Product(@NonNull String name, double price) {
        this.name = name;
        this.price = price;
    }
}
