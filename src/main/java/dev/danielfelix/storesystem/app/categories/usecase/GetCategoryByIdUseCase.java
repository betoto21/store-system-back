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

public class GetCategoryByIdUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCategoryByIdUseCase.class);
    private GetCategoryByIdUseCase(){}

    public static Category dispatch(int id){
        LOGGER.info("Invoking getCategoryById");
        try{
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return CategoryRepository.getCategoryById(con, id);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving category",e);
        }
    }
}
