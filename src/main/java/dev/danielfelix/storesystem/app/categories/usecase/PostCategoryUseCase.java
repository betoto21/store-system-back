package dev.danielfelix.storesystem.app.categories.usecase;


import dev.danielfelix.storesystem.app.categories.domain.infra.repository.CategoryRepository;
import dev.danielfelix.storesystem.app.categories.domain.models.Category;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.persistence.db.ConnectionsHolder;
import dev.danielfelix.storesystem.libraries.persistence.db.PostgreSQLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class PostCategoryUseCase {

    private static final Logger LOGGER = LogManager.getLogger(PostCategoryUseCase.class);
    private PostCategoryUseCase(){}

    public static Category dispatch(String category){
        LOGGER.info("invoking dispatch");
        try {
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return CategoryRepository.insertCategory(con, category);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving Category",e);
        }
    }
}
