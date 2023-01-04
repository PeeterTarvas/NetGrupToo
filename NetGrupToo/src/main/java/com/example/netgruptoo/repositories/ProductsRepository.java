package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository  extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT MAX(product_id) + 1 AS user_id FROM data.product ", nativeQuery = true)
    Long findNextId();


    @Query(value = """
            SELECT p.* FROM (
              SELECT * FROM data.catalogue_product_reference WHERE catalogue_id=:catalogueId
                  ) AS cpr , data.product AS p WHERE cpr.product_id=p.product_id
        """, nativeQuery = true)
    List<Product> getAllProductsByCatalogue(Long catalogueId);

    @Query(value = """
        SELECT * FROM data.product WHERE product_owner = :user AND name LIKE CONCAT('%',:searchTerm,'%')
    """, nativeQuery = true)
    List<Product> getAllProductsByUserAndSearchTerm(String user, String searchTerm);
}
