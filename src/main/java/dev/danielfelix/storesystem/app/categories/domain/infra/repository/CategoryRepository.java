package dev.danielfelix.storesystem.app.categories.domain.infra.repository;

import dev.danielfelix.storesystem.app.categories.domain.infra.mapper.CategoryMapper;
import dev.danielfelix.storesystem.app.categories.domain.models.Category;
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

public class CategoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepository.class);
    private static final String QUERY_GET_ALL_CATEGORIES = "select id_category, name  from products_categories pc ORDER BY ? LIMIT 10 OFFSET ?";
    private static final String QUERY_GET_COUNT_CATEGORIES = "select count(*) from products_categories pc";
    private static final String QUERY_GET_CATEGORY_BY_ID = "select id_category, name from products_categories pc where id_category = ?";
    private static List<Category> categories = new ArrayList<>();
    private static CategoryMapper mapper = new CategoryMapper();
    private static int countCategories = 0;
    private static String debugQuery = "Running Query {}";
    private CategoryRepository(){}

    public static List<Category> getAllCategories(Connection con, int page, String sort){
        LOGGER.info("Invoking getAllCategories");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_ALL_CATEGORIES)){
            LOGGER.debug(debugQuery, ps);
            categories.clear();
            getCountCategories(con);
            int pages = PaginationHelper.totalPage(countCategories);
            ps.setString(1, sort);
            ps.setInt(2, (page - 1) * 10);

            try(ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    categories.add(mapper.apply(rs, pages));
                }
            }
                return categories;
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving categories: ",e);
        }
    }

    public static Category getCategoryById(Connection con, int id){
        LOGGER.info("Invoking getCategoryById");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_CATEGORY_BY_ID)){
            LOGGER.debug(debugQuery, ps);
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return mapper.apply(rs, 1);
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving category: ",e);
        }
        return null;
    }

    private static void getCountCategories(Connection con){
        LOGGER.info("Invoking countCategories");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_COUNT_CATEGORIES)){
            LOGGER.debug(debugQuery, ps);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    countCategories = rs.getInt(1);
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on counting categories: ",e);
        }
    }

}
