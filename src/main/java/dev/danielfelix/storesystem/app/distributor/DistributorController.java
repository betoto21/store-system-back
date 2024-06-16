package dev.danielfelix.storesystem.app.distributor;

import dev.danielfelix.storesystem.app.distributor.domain.model.Distributor;
import dev.danielfelix.storesystem.app.distributor.usecase.GetDistributorUseCase;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1/distributor")
public class DistributorController {

    private static final Logger LOGGER = LogManager.getLogger(DistributorController.class);


    @GetMapping("/")
    public ResponseEntity<List<Distributor>> getSuppliers(){
        try {
            LOGGER.info("procesing request getSuppliers");
            List<Distributor> distributors = GetDistributorUseCase.dispatch();
            LOGGER.info("Response: {}", distributors);
            if (distributors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(distributors);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getSuppliers {}", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
