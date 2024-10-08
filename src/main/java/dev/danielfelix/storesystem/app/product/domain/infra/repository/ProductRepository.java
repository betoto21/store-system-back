package dev.danielfelix.storesystem.app.product.domain.infra.repository;

import dev.danielfelix.storesystem.app.product.domain.infra.mapper.ProductMapper;
import dev.danielfelix.storesystem.app.product.domain.models.Product;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.pagination.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);
    private static final String QUERY_GET_ALL_PRODUCTS = "SELECT id_product, \"name\", barcode, price, id_category, id_distributor, stock FROM products p  ORDER BY ? LIMIT 10 OFFSET ?";
    private static final String QUERY_GET_PRODUCT_BY_ID = "SELECT id_product, \"name\", barcode, price, id_category, id_distributor, stock FROM products p WHERE barcode = ?";
    private static final String QUERY_INSERT_PRODUCT = "INSERT INTO products (\"name\", barcode, price, id_category, id_distributor, stock) VALUES (?,?,?,?,?,?) RETURNING id_product";
    private static final String QUERY_UPDATE_PRODUCT = "UPDATE products SET \"name\" = ?, price = ?, id_category = ?, id_distributor = ?, stock = ? WHERE id_product = ? AND barcode = ?";
    private static final String QUERY_GET_COUNT_PRODUCT = "SELECT count(*) FROM products p";
    private static final List<Product> products = new ArrayList<>();
    private static final ProductMapper mapper = new ProductMapper();
    private static final String DEBUG_QUERY = "Running Query {}";
    private ProductRepository(){}

    public static List<Product> getAllProducts(Connection con, int page, String sort){
        LOGGER.info("Invoking getAllProducts");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_ALL_PRODUCTS)){
            LOGGER.debug(DEBUG_QUERY, ps);
            products.clear();
            int countProducts = getCountProducts(con);
            int pages = PaginationHelper.totalPage(countProducts);
            ps.setString(1, sort);
            ps.setInt(2, (page - 1) * 10);

            try(ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    products.add(mapper.apply(rs, pages));
                }
            }
            return products;
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving products: ",e);
        }
    }

    public static Product getProductByBarCode(Connection con, String id){
        LOGGER.info("Invoking getProductById");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_PRODUCT_BY_ID)){
            LOGGER.debug(DEBUG_QUERY, ps);
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return mapper.apply(rs, 1);
                }
                return null;
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving product by id: ",e);
        }
    }

    public static Product insertProduct(Connection con, Product product){
        LOGGER.info("Invoking insertProduct");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_INSERT_PRODUCT)){
            LOGGER.debug(DEBUG_QUERY, ps);
            ps.setString(1, product.getName());
            ps.setString(2, product.getBarcode());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getIdCategory());
            ps.setInt(5, product.getIdDistributor());
            ps.setInt(6, product.getStock());
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    product.setIdProduct(rs.getInt(1));
                    return product;
                }
                return null;
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on inserting product: ",e);
        }

    }

    public static Product updateProduct(Connection con, Product product){
        LOGGER.info("Invoking updateProduct");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_UPDATE_PRODUCT)){
            LOGGER.debug(DEBUG_QUERY, ps);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getIdCategory());
            ps.setInt(4, product.getIdDistributor());
            ps.setInt(5, product.getStock());
            ps.setInt(6, product.getIdProduct());
            ps.setString(7, product.getBarcode());
            int result = ps.executeUpdate();
            if (result == 0) {
                return null;
            }
            return product;
        } catch (SQLException e){
            throw new DatabaseException("Error on updating product: ",e);
        }

    }

    private static int getCountProducts(Connection con){
        LOGGER.info("Invoking countProducts");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_COUNT_PRODUCT)){
            LOGGER.debug(DEBUG_QUERY, ps);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on counting products: ",e);
        }
    }
}
