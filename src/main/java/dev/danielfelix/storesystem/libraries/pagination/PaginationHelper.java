package dev.danielfelix.storesystem.libraries.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PaginationHelper {

    public static String getPaginationQuery(int page, String sort){
        return " ORDER BY " + sort + " LIMIT 10 OFFSET " + (page - 1) * 10 ;
    }

    public static int totalPage(int totalItems) {
        return (int) Math.ceil((double) totalItems / 10);
    }
}
