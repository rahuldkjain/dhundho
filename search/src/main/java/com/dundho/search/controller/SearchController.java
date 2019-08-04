package com.dundho.search.controller;

import com.dundho.search.model.ProductsDTO;
import com.dundho.search.repository.SearchRepository;
import com.dundho.search.service.SearchServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchServiceImpl searchService;

    @Autowired
    SearchRepository searchRepository;

    public JSONObject getJSONResponse(Object data){
        JSONObject response = new JSONObject();
        response.put("code", "200");
        response.put("data", data);
        response.put("error","");
        response.put("message", "success");

        response.remove(data,"password");

        return response;
    }

    @PostMapping(consumes = {"application/json"})
    public JSONObject saveProduct(@RequestBody ProductsDTO ProductsDTO) {
        ProductsDTO result = new ProductsDTO();
        result = searchService.saveProduct(ProductsDTO);
        JSONObject response = getJSONResponse(result);
        response.replace("message", "success", "saving successful");
        return response;
    }

    @GetMapping
    public JSONObject getProduct(@RequestParam String id) {
        JSONObject response = getJSONResponse(searchService.getProduct(id));
        response.replace("message", "success", "fetching successful");
        return response;
    }

    @DeleteMapping
    public JSONObject deleteProduct(@RequestParam String id) {
        JSONObject response = getJSONResponse(searchService.deleteProduct(id));
        response.replace("message", "success", "fetching successful");
        return response;
    }

    @PutMapping(consumes = {"application/json"})
    public JSONObject putProduct(@RequestBody ProductsDTO ProductsDTO) {
        JSONObject response = getJSONResponse(searchService.putProduct(ProductsDTO));
        response.replace("message", "success", "updating successful");
        return response;
    }

    @GetMapping(value = "/getall")
    public JSONObject getAllProduct() {
        JSONObject response = getJSONResponse(searchService.getAllProduct());
        response.replace("message", "success", "fetching successful");
        return response;

    }

    @GetMapping(value = "/suggestion")
    public JSONObject getQuery(@RequestParam String query) throws ParseException {
        JSONObject response = getJSONResponse(searchService.query(query));
        response.replace("message", "success", "fetching successful");
        return response;
    }

    @PostMapping(value="/addall",consumes = {"application/json"})
    public JSONObject saveallProduct(@RequestBody List<ProductsDTO> ProductsDTO) {

        JSONObject response = null;
        try {
            response = getJSONResponse(searchService.addAll(ProductsDTO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.replace("message", "success", "saving successful");
        return response;
    }

    @GetMapping(value="/asd")
    public JSONObject asd(Pageable pageable ) {
        JSONObject response = getJSONResponse(searchRepository.search(pageable));
        return response;

    }
}
