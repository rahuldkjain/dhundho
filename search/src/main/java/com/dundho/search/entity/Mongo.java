package com.dundho.search.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
@Document(collection = "search")
public class Mongo {

    @Id
    private Long id;
    private String val;
    private String key;

}
