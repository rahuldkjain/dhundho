package com.dundho.search.service;

import com.dundho.search.model.ProductsDTO;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SearchService {
    ProductsDTO saveProduct (ProductsDTO ProductsDTO);

    ProductsDTO getProduct (String id);

    ProductsDTO deleteProduct (String id);

    ProductsDTO putProduct (ProductsDTO ProductsDTO);

    List<ProductsDTO> getAllProduct ();

    JSONArray query(String query) throws ParseException;

    String genId();

    CompletableFuture<String> addAll(List<ProductsDTO> productsDTOS) throws InterruptedException;

    Object addAllmongo(List<ProductsDTO> productsDTO) throws InterruptedException;

    Object getAllmongo(String s);
}
