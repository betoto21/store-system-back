package dev.danielfelix.storesystem.app.categories.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Category {

    private int idCategory;
    private String name;
    // pagination showing total items
    private int totalPages;
}
