package com.dundho.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductsDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("category")
    private String category;
    @JsonProperty("subCategory")
    private String subCategory;
    @JsonProperty("modelNo")
    private String modelNo;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shippingDetails")
    private String shippingDetails;
    @JsonProperty("stock")
    private String stock;
    @JsonProperty("color")
    private String color;
    @JsonProperty("noOfReviews")
    private Integer noOfReviews;
    @JsonProperty("noOfRatings")
    private Integer noOfRatings;
    @JsonProperty("avgRating")
    private Double avgRating;
    @JsonProperty("finalUrl")
    private String finalUrl;
    @JsonProperty("warranty")
    private String warranty;
    @JsonProperty("condition")
    private String condition;
    @JsonProperty("features")
    private String features;
    @JsonProperty("attributes")
    private String attributes;
    @JsonProperty("sizes")
    private Map<String, String> sizes;
    @JsonProperty("images")
    private Map<String,String> images;
    @JsonProperty("price")
    private Double price;
}
