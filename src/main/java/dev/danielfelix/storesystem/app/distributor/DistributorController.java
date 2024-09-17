package dev.danielfelix.storesystem.app.distributor;

import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import dev.danielfelix.storesystem.app.distributor.usecase.GetDistributorByIdUseCase;
import dev.danielfelix.storesystem.app.distributor.usecase.GetDistributorUseCase;
import dev.danielfelix.storesystem.app.distributor.usecase.PostDistributorUseCase;
import dev.danielfelix.storesystem.app.distributor.usecase.PutDistributorUseCase;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.pagination.models.RequestPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1/distributor")
public class DistributorController {

    private static final Logger LOGGER = LogManager.getLogger(DistributorController.class);


    @GetMapping("/")
    public ResponseEntity<List<Distributor>> getDistributor(@RequestHeader("page") String page, @RequestHeader("sort") String sort){
        try {
            LOGGER.info("procesing request getDistributors");
            RequestPage requestPage = RequestPage.builder()
                    .page(Integer.parseInt(page))
                    .sort(sort)
                    .build();
            List<Distributor> distributors = GetDistributorUseCase.dispatch(requestPage);
            LOGGER.info("Response: {}", distributors);
            if (distributors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(distributors);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getDistributor: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> insertSupplier(@RequestBody Distributor distributor){
        try {
            LOGGER.info("procesing request insertDistributor");
            if (isNullOrEmpty(distributor.getName())) {
                LOGGER.info("the name is null or empty");
                return ResponseEntity.badRequest().build();
            }
            PostDistributorUseCase.dispatch(distributor);
            return ResponseEntity.ok().build();
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request insertDistributor: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Distributor> updateSupplier(@RequestBody Distributor distributor){
        try {
            LOGGER.info("procesing request updateDistributor");
            if (isNullOrEmpty(distributor.getName()) || isNullOrZero(Integer.valueOf(distributor.getIdDistributor()))) {
                LOGGER.info("the name or idSupllier is null or empty");
                return ResponseEntity.badRequest().build();
            }
            Distributor distributorResponse = PutDistributorUseCase.dispatch(distributor);
            return ResponseEntity.ok(distributorResponse);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request updateDistributor: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distributor> getDistributorById(@PathVariable Integer id){
        try {
            LOGGER.info("procesing request getDistributorById");
            if (isNullOrZero(id)) {
                LOGGER.info("the id is null or empty");
                return ResponseEntity.badRequest().build();
            }
            Distributor distributor = GetDistributorByIdUseCase.dispatch(id.intValue());
            if (distributor == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(distributor);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getDistributorById: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isNullOrZero(Integer number) {
        return number == null || number == 0;
    }

}
