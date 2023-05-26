package com.Repository;

import com.domain.Person;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Jack
 * @Desc
 * @Date 2023/5/20
 **/
public interface PersonRepository extends ElasticsearchRepository<Person, String> {
    Person findByName(String name);
}
