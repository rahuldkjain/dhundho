package com.dundho.search.repository;

import com.dundho.search.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface SearchRepository extends ElasticsearchRepository<Products,String> {
    //List<Products> findByProductName(String query);

    List<Products> findByDescription(String query);

    //Iterable<Products> findByProductNameOrDescription(String query);

    //Iterable<Products> findByProductNameLike(String query);

    Iterable<Products> findByDescriptionLike(String query);

    //Iterable<Products> findByProductNameContaining(String query);

    Iterable<Products> findByDescriptionContaining(String query);

    @Query("{ \"range\": { \"price\": { \"gte\": 80 } } } ")
    Page<Products> search(Pageable pageable);
}
