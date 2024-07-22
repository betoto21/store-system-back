package dev.danielfelix.storesystem.app.distributor.usecase;

import dev.danielfelix.storesystem.app.distributor.domain.infra.repository.DistributorRepository;
import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.persistence.db.ConnectionsHolder;
import dev.danielfelix.storesystem.libraries.persistence.db.PostgreSQLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class PostDistributorUseCase {

    private static final Logger LOGGER = LogManager.getLogger(PostDistributorUseCase.class);
    private PostDistributorUseCase(){}

    public static Distributor dispatch(Distributor distributor){
        LOGGER.info("invoking dispatch");
        try {
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return DistributorRepository.insertDistributor(con, distributor.getName());
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving Distributor",e);
        }
    }
}
