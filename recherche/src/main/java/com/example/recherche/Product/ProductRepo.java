package com.example.recherche.Product;

import com.example.recherche.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepo extends JpaRepository <Produit , Long> {
}
