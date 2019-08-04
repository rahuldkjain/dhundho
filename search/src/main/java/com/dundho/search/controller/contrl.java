package com.dundho.search.controller;

import com.dundho.search.model.ProductsDTO;
import com.dundho.search.service.SearchService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongo")
public class contrl {

    @Autowired
    SearchService searchService;

    public JSONObject getJSONResponse(Object data){
        JSONObject response = new JSONObject();
        response.put("code", "200");
        response.put("data", data);
        response.put("error","");
        response.put("message", "success");

        response.remove(data,"password");

        return response;
    }

    @PostMapping(value="/addall",consumes = {"application/json"})
    public JSONObject mongoall(@RequestBody List<ProductsDTO> ProductsDTO) {

        JSONObject response = null;
        try {
            response = getJSONResponse(searchService.addAllmongo(ProductsDTO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.replace("message", "success", "saving successful");
        return response;
    }

    @GetMapping(value = "/getall")
    public JSONObject mongogetall(@RequestParam String s) {
        JSONObject response = getJSONResponse(searchService.getAllmongo(s));
        response.replace("message", "success", "saving successful");
        return response;
    }
}
