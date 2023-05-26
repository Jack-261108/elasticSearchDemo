package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author 张欢
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "person")
public class Person {
    @Id
    private int age;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Text,index = false)
    private String sex;
}
