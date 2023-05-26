package com.utils;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 张欢
 * es的工具类
 */
public class ElasticsearchUtils {
    private final RestHighLevelClient elasticsearchClient;

    @Autowired
    public ElasticsearchUtils(RestHighLevelClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public static boolean createIndex(RestHighLevelClient client, String name) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(name);
        CreateIndexResponse Response = client.indices().create(request, RequestOptions.DEFAULT);
        return Response.isAcknowledged();
    }

    public static GetIndexResponse getIndex(RestHighLevelClient client, String name) throws IOException {
        GetIndexRequest request = new GetIndexRequest(name);
        return client.indices().get(request, RequestOptions.DEFAULT);
    }

    public static List<String> getAllIndex(RestHighLevelClient client) throws IOException {
        GetAliasesRequest build = new GetAliasesRequest();
        GetAliasesResponse alias = client.indices().getAlias(build, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetadata>> aliases = alias.getAliases();
        Set<String> strings = aliases.keySet();
        return new ArrayList<String>(strings);
    }

    public static boolean deleteIndex(RestHighLevelClient client, String name) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(name);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    public static SearchResponse searchDate(RestHighLevelClient client, String indices, SearchSourceBuilder builder) throws IOException {
        SearchRequest request = new SearchRequest(indices);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }


    public void saveDocument(String index, String id, String json) throws IOException {
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(json, XContentType.JSON);

        elasticsearchClient.index(request, RequestOptions.DEFAULT);
    }

    public String getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = elasticsearchClient.get(request, RequestOptions.DEFAULT);

        return response.getSourceAsString();
    }

    public SearchResponse searchDocuments(String index, String field, String value, int from, int size) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field, value));
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        request.source(sourceBuilder);

        return elasticsearchClient.search(request, RequestOptions.DEFAULT);
    }

    public void saveDocuments(String index, Iterable<String> ids, Iterable<String> jsonList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        int i = 0;
        for (String id : ids) {
            String json = jsonList.iterator().next();
            IndexRequest request = new IndexRequest(index)
                    .id(id)
                    .source(json, XContentType.JSON);

            bulkRequest.add(request);

            // 每500个请求执行一次批量操作
            if (++i % 500 == 0) {
                BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                if (bulkResponse.hasFailures()) {
                    // 处理批量操作失败的情况
                }
                bulkRequest = new BulkRequest();
            }
        }

        // 执行剩余的批量操作
        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                // 处理批量操作失败的情况
            }
        }
    }
}
