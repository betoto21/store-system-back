package dev.danielfelix.storesystem.app.categories.usecase;

import dev.danielfelix.storesystem.app.categories.domain.infra.repository.CategoryRepository;
import dev.danielfelix.storesystem.app.categories.domain.models.Category;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.persistence.db.ConnectionsHolder;
import dev.danielfelix.storesystem.libraries.persistence.db.PostgreSQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class GetCategoriesUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCategoriesUseCase.class);

    public static List<Category> dispatch(){
        LOGGER.info("Invoking getAllCategories");
        try{
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return CategoryRepository.getAllCategories(con);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving categories",e);
        }
    }
}
