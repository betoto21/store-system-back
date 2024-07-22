package dev.danielfelix.storesystem.app.distributor.domain.infra.mapper;

import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistributorMapper {

    private static final Logger LOGGER = LogManager.getLogger(DistributorMapper.class);
    private static String idDistributor = "id_distributor";
    private static String nameDistributor = "name";

    public Distributor apply(ResultSet rs, int totalPages) throws SQLException {
        try {
            return Distributor.builder()
                    .idDistributor(rs.getInt(idDistributor))
                    .name(rs.getString(nameDistributor))
                    .totalPages(totalPages)
                    .build();
        } catch (SQLException e) {
            throw new SQLException("Error on retrieving Distributor: ", e);
        }
    }
}
