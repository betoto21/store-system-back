package dev.danielfelix.storesystem.app.product.usecase;

import dev.danielfelix.storesystem.app.product.domain.infra.repository.ProductRepository;
import dev.danielfelix.storesystem.app.product.domain.models.Product;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.persistence.db.ConnectionsHolder;
import dev.danielfelix.storesystem.libraries.persistence.db.PostgreSQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class GetProductByIdUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductByIdUseCase.class);
    private GetProductByIdUseCase() {}

    public static Product dispatch(int id){
        LOGGER.info("invoking dispatch");
        try {
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return ProductRepository.getProductById(con, id);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving Products",e);
        }
    }
}
