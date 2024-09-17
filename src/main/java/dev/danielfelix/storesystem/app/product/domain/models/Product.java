package dev.danielfelix.storesystem.app.product.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Product {

    private int idProduct;
    private String name;
    private String barcode;
    private double price;
    private int idCategory;
    private int idDistributor;
    private int stock;
    private int totalPage;
}
