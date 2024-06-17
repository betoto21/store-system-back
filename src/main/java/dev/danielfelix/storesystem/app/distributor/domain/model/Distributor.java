package dev.danielfelix.storesystem.app.distributor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Distributor {
    private int idSupplier;
    private String name;
    private int totalPages;
}
