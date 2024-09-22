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

public class PostProductUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostProductUseCase.class);
    private PostProductUseCase() {}

    public static Product dispatch(Product product){
        LOGGER.info("invoking dispatch");
        try {
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return ProductRepository.insertProduct(con, product);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving Products",e);
        }
    }

}
