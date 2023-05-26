package test;


import com.Application;
import com.Repository.PersonRepository;
import com.domain.Person;
import com.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest(classes = Application.class)
@Slf4j
public class test {

    @Resource
    private PersonService personService;
    public static final Faker FAKER = new Faker(Locale.CHINA);
    @Resource
    private RestHighLevelClient restHighLevelClient;
@Resource
private PersonRepository personRepository;
    @Test
    public void createIndex() throws Exception {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(new Person(FAKER.number().numberBetween(1, 100), FAKER.name().name(), FAKER.gender().shortBinaryTypes()));
        }
        personService.BatchSave(list);

    }

    @Test
    public void rrr() {
        personService.getALl();
//        GetIndexRequest books = new GetIndexRequest("books");
    }

    //
//    @Test
//    public void gggg() throws Exception {
//        search();
//    }
//
    @Test
    public void search() throws IOException {
        SearchSourceBuilder query = new SearchSourceBuilder();
        SearchSourceBuilder query1 = query.query(QueryBuilders.matchAllQuery()).from(10).size(20);
        query.aggregation(AggregationBuilders.avg("avgAge").field("age"));
        query.aggregation(AggregationBuilders.terms("Ages").field("age"));
//      personService.search(query1);
        Person jack = personRepository.findByName("Jack");
        System.out.println(jack);
    }
//
//    /**
//     * 单条精确查询
//     *
//     * @throws IOException
//     */
//    public void search0() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termQuery("name", "Jack"));
//        SearchRequest request = new SearchRequest("kkkk");
//        request.source(builder);
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//        log.info("{}", response.toString());
//    }
//
//    /**
//     * 多条件精确查询，取并集
//     *
//     * @throws IOException
//     */
//    public void search1() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termsQuery("name", "Jack", "Cker"));
//        SearchResponse response = ElasticsearchUtils.searchDate(client, "kkkk", builder);
//        log.info("{}", response.toString());
//    }
//
//    /**
//     * 范围查询
//     *
//     * @throws IOException
//     */
//    public void search2() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").from(20).to(30));
//        SearchResponse response = ElasticsearchUtils.searchDate(client, "kkkk", builder);
//        log.info("{}", response.toString());
//    }
//
//    /**
//     * 范围查询
//     *
//     * @throws IOException
//     */
//    public void search3() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").lt(20).gt(30));
//        SearchResponse response = ElasticsearchUtils.searchDate(client, "kkkk", builder);
//        log.info("{}", response.toString());
//    }
//
//    /**
//     * 模糊查询
//     *
//     * @throws IOException
//     */
//    public void search4() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.wildcardQuery("name", "Jack"));
//        SearchResponse response = ElasticsearchUtils.searchDate(client, "kkkk", builder);
//        log.info("{}", response.toString());
//    }
//
//    /**
//     * 模糊查询
//     *
//     * @throws IOException
//     */
//    public void search5() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.wildcardQuery("name", "Jack"));
//        SearchResponse response = ElasticsearchUtils.searchDate(client, "kkkk", builder);
//        log.info("{}", response.toString());
//    }

//    @Test
//    public void test6() {
//        Person person = new Person();
//        person.setAge(10);
//        person.setSex("M");
//        person.setName("Jack");
//        Person save = esService.save(person);
//    }
}