package com.service;

import com.Repository.PersonRepository;
import com.domain.Person;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Jack
 * @Desc
 * @Date 2023/5/20
 **/
@Service
public class PersonService {
    @Resource
    private PersonRepository personRepository;

    public void save(Person p) {
        Person save = personRepository.save(p);
        System.out.println(save);
    }

    public void get(int id) {
        Optional<Person> byId = personRepository.findById(String.valueOf(id));
        byId.ifPresent(System.out::println);
    }

    public void getALl() {
        Iterable<Person> all = personRepository.findAll();
        Iterator<Person> iterator = all.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
   public void BatchSave(List<Person> list){
      personRepository.saveAll(list);
   }
   public void search(SearchSourceBuilder builder){
//       Iterable<Person> search = personRepository.search(builder);
//       Iterator<Person> iterator = search.iterator();
//       while (iterator.hasNext()){
//           System.out.println(iterator.next());
//       }

   }
}
