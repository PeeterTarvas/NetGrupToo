package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository  extends JpaRepository<Catalogue, Integer> {
}
