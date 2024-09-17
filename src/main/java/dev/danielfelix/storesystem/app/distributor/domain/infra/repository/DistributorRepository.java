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
    private static final String QUERY_GET = "select id_distributor, \"name\"  from products_distributors pd ORDER BY ? LIMIT 10 OFFSET ?";
    private static final String QUERY_INSERT = "insert into products_distributors (\"name\") values (?)";
    private static final String GET_COUNT_DISTRIBUTORS = "select count(*) from products_distributors pd";
    private static final String QUERY_GET_BY_ID = "select id_distributor, \"name\"  from products_distributors pd where id_distributor = ?";
    private static final String QUERY_UPDATE = "update products_distributors set \"name\" = ? where id_distributor = ?";
    private static List<Distributor> distributors = new ArrayList<>();
    private static DistributorMapper mapper = new DistributorMapper();
    private static int countCategories = 0;
    private static String debugQuery = "Running Query: {}";
    private DistributorRepository(){}

    public static List<Distributor> getAllDistributor(Connection con, int page, String sort){
        LOGGER.info("Invoking getAllDistributor");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET)){
            LOGGER.debug(debugQuery, ps);
            distributors.clear();
            getCountCategories(con);
            int pages = PaginationHelper.totalPage(countCategories);
            ps.setString(1, sort);
            ps.setInt(2, (page - 1) * 10);
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    distributors.add(mapper.apply(rs, pages));
                }
            }
            return distributors;
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving Distributor: ",e);
        }
    }

    public static Distributor insertDistributor(Connection con, String distributor){
        LOGGER.info("Invoking insertDistributor");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_INSERT)){
            LOGGER.debug(debugQuery, ps);
            con.setAutoCommit(false);
            ps.setString(1, distributor);
            ps.executeUpdate();
            LOGGER.info("Distributor {} inserted, commiting changes", distributor);
            con.commit();
            con.setAutoCommit(true);
            return Distributor.builder().name(distributor).build();
        } catch (SQLException e){
            try {
                LOGGER.error("performing rollback");
                con.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException("Error on rollback: ",ex);
            }
            throw new DatabaseException("Error on inserting Distributor: ",e);
        }
    }

    public static Distributor getDistributorById(Connection con, int id){
        LOGGER.info("Invoking getDistributorById");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_GET_BY_ID)){
            LOGGER.debug(debugQuery, ps);
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return mapper.apply(rs, 1);
                }
            }
            return null;
        } catch (SQLException e){
            throw new DatabaseException("Error on retrieving Distributor by id: ",e);
        }
    }

    public static Distributor updateDistributor(Connection con, Distributor distributor){
        LOGGER.info("Invoking updateSupplier");
        try(final PreparedStatement ps = con.prepareStatement(QUERY_UPDATE)){
            LOGGER.debug(debugQuery, ps);
            con.setAutoCommit(false);
            ps.setString(1, distributor.getName());
            ps.setInt(2, distributor.getIdDistributor());
            ps.executeUpdate();
            LOGGER.info("Distributor {} updated, commiting changes", distributor.getName());
            con.commit();
            con.setAutoCommit(true);
            return distributor;
        } catch (SQLException e){
            try {
                LOGGER.error("performing rollback");
                con.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException("Error on rollback: ",ex);
            }
            throw new DatabaseException("Error on updating Distributor: ",e);
        }
    }

    private static void getCountCategories(Connection con){
        LOGGER.info("Invoking countCategories");
        try(final PreparedStatement ps = con.prepareStatement(GET_COUNT_DISTRIBUTORS)){
            LOGGER.debug(debugQuery, ps);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    countCategories = rs.getInt(1);
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Error on counting Distributor: ",e);
        }
    }
}
