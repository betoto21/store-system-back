package dev.danielfelix.storesystem.app.categories.domain.infra.mapper;


import dev.danielfelix.storesystem.app.categories.domain.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper {

    private Logger LOGGER = LoggerFactory.getLogger(CategoryMapper.class);
    private final String ID_CATEGORY = "id_category";
    private final String NAME_CATEGORY = "name";

    public Category apply(ResultSet rs) throws SQLException {
        LOGGER.debug("Äpplying Category");
        try{
            return Category.builder()
                    .idCategory(rs.getInt(ID_CATEGORY))
                    .name(rs.getString(NAME_CATEGORY))
                    .build();
        } catch (SQLException e){
            throw new SQLException("Error on retrieving Category", e);
        }
    }
}
