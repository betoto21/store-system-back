package dev.danielfelix.storesystem.app.product;

import dev.danielfelix.storesystem.app.product.domain.models.Product;
import dev.danielfelix.storesystem.app.product.usecase.GetProductsUseCase;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.pagination.models.RequestPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private static final Logger LOGGER = LogManager.getLogger(ProductController.class);
    List<Product> products = null;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(@RequestHeader("page") String page, @RequestHeader("sort") String sort){
        try{
            LOGGER.info("procesing request getProducts");
            RequestPage requestPage = RequestPage.builder()
                    .page(Integer.parseInt(page))
                    .sort(sort)
                    .build();
            products = GetProductsUseCase.dispatch(requestPage);
            LOGGER.info("Response: {}", products);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getProducts: ", e);
            return ResponseEntity.internalServerError().build();
        }

    }
}
