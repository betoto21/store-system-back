package dev.danielfelix.storesystem.app.categories;



import dev.danielfelix.storesystem.app.categories.domain.models.Category;
import dev.danielfelix.storesystem.app.categories.usecase.GetCategoriesUseCase;
import dev.danielfelix.storesystem.app.categories.usecase.GetCategoryByIdUseCase;
import dev.danielfelix.storesystem.libraries.exceptions.TechnicalErrorException;
import dev.danielfelix.storesystem.libraries.pagination.models.RequestPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private static final String DEBUG_RESPONSE = "Response: {}";

    @GetMapping("/")
    public ResponseEntity<List<Category>> getSuppliers(@RequestHeader("page") String page, @RequestHeader("sort") String sort) {
        try {
            LOGGER.info("procesing request getSuppliers");
            RequestPage requestPage = RequestPage.builder()
                    .page(Integer.parseInt(page))
                    .sort(sort)
                    .build();
            List<Category> categories = GetCategoriesUseCase.dispatch(requestPage);
            LOGGER.info(DEBUG_RESPONSE, categories);
            if (categories.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(categories);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getCategories: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        try {
            LOGGER.info("procesing request getCategoryById");
            Category category = GetCategoryByIdUseCase.dispatch(id);
            LOGGER.info(DEBUG_RESPONSE, category);
            return ResponseEntity.ok(category);
        } catch (TechnicalErrorException e){
            LOGGER.error("Error on request getCategoryById: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
