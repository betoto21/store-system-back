package dev.danielfelix.storesystem.app.distributor.usecase;

import dev.danielfelix.storesystem.app.distributor.domain.infra.repository.DistributorRepository;
import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.pagination.models.RequestPage;
import dev.danielfelix.storesystem.libraries.persistence.db.PostgreSQLConnection;
import dev.danielfelix.storesystem.libraries.persistence.db.ConnectionsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;


public class GetDistributorUseCase {

    private static final Logger LOGGER = LogManager.getLogger(GetDistributorUseCase.class);
    private static final String sort = "id_distributor";
    private GetDistributorUseCase(){}

    public static List<Distributor> dispatch(RequestPage pr){
        LOGGER.info("invoking dispatch");
        try {
            final Connection con = ConnectionsHolder.getConnection(
                    PostgreSQLConnection.ENVARPREFIX,
                    PostgreSQLConnection::getConnection);
            return DistributorRepository.getSuppliers(con, pr.getPage(), sort);
        } catch (DatabaseException e){
            throw new TechnicalErrorException("Error on retrieving suppliers",e);
        }
    }
}
