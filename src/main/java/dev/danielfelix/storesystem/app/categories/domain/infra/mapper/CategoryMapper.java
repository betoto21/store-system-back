package dev.danielfelix.storesystem.app.categories.domain.infra.mapper;


import dev.danielfelix.storesystem.app.categories.domain.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryMapper.class);
    private static final String ID_CATEGORY = "id_category";
    private static final String NAME_CATEGORY = "name";

    public Category apply(ResultSet rs, int countCategories) throws SQLException {
        LOGGER.debug("Äpplying Category");
        try{
            return Category.builder()
                    .idCategory(rs.getInt(ID_CATEGORY))
                    .name(rs.getString(NAME_CATEGORY))
                    .totalPages(countCategories)
                    .build();
        } catch (SQLException e){
            throw new SQLException("Error on retrieving Category", e);
        }
    }
}
