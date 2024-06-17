package dev.danielfelix.storesystem.app.distributor.domain.infra.repository;

import dev.danielfelix.storesystem.app.distributor.domain.infra.mapper.DistributorMapper;
import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import dev.danielfelix.storesystem.libraries.exceptions.DatabaseException;
import dev.danielfelix.storesystem.libraries.pagination.PaginationHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class DistributorRepository {

    private static final Logger LOGGER = LogManager.getLogger(DistributorRepository.class);
    public static final String QUERY_GET = "select id_distributor, \"name\"  from products_distributors pd";
    private static final String GET_COUNT_DISTRIBUTORS = "select count(*) from products_distributors pd";
    private static List<Distributor> distributors = new ArrayList<>();
    private static DistributorMapper mapper = new DistributorMapper();
    private static int countCategories = 0;
    private DistributorRepository(){}

    public static List<Distributor> getSuppliers(Connection con, int page, String sort){
        LOGGER.info("Invoking getAllCategories");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET.concat(PaginationHelper.getPaginationQuery(page, sort)))){
            LOGGER.debug("Running Query {}", ps);
            distributors.clear();
            getCountCategories(con);
            int pages = PaginationHelper.totalPage(countCategories);
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    distributors.add(mapper.apply(rs, pages));
                }
            }
            return distributors;
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving suppliers: ",e);
        }
    }

    private static void getCountCategories(Connection con){
        LOGGER.info("Invoking countCategories");
        try(final PreparedStatement ps = con.prepareStatement(GET_COUNT_DISTRIBUTORS)){
            LOGGER.debug("Running Query {}", ps);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    countCategories = rs.getInt(1);
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on counting categories: ",e);
        }
    }
}
