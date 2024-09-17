package dev.danielfelix.storesystem.libraries.pagination;




public class PaginationHelper {
    private PaginationHelper(){}

    public static int totalPage(int totalItems) {
        return (int) Math.ceil((double) totalItems / 10);
    }
}
