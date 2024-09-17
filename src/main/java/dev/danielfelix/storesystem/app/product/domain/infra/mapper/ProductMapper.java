package dev.danielfelix.storesystem.app.product.domain.infra.mapper;

import dev.danielfelix.storesystem.app.product.domain.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {

    private static String idProduct = "id_product";
    private static String name = "name";
    private static String barcode = "barcode";
    private static String price = "price";
    private static String idCategory = "id_category";
    private static String idDistributor = "id_distributor";
    private static String stack = "stock";

    public Product apply(ResultSet rs, int pages) throws SQLException {

        try {
            return Product.builder()
                    .idProduct(rs.getInt(idProduct))
                    .name(rs.getString(name))
                    .barcode(rs.getString(barcode))
                    .price(rs.getDouble(price))
                    .idCategory(rs.getInt(idCategory))
                    .idDistributor(rs.getInt(idDistributor))
                    .stock(rs.getInt(stack))
                    .totalPage(pages)
                    .build();
        } catch (SQLException e) {
            throw new SQLException("Error on retrieving Product: ", e);
        }
    }
}
