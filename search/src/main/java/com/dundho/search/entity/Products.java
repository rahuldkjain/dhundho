package com.dundho.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Map;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
@Document(indexName = "search_index", type="search_product")
public class Products {

    @Id
    private String id;
    private String title;
    private String brand;
    private String category;
    private String subCategory;
    private String modelNo;
    private String rank;
    private String productType;
    private String description;
    private String shippingDetails;
    private String stock;
    private String color;
    private Integer noOfReviews;
    private Integer noOfRatings;
    private Double avgRating;
    private String finalUrl;
    private String warranty;
    private String condition;
    private String features;
    private String attributes;
    private Map<String, String> sizes;
    private Map<String, String> images;
    private Double price;
}

