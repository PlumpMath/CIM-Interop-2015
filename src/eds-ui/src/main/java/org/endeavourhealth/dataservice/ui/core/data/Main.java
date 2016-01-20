package org.endeavourhealth.dataservice.ui.core.data;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.net.InetAddress;
import java.util.*;

public class Main {

    public static void main (String[] args) {
        try {

            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "sheaves-dev")
                    .put("node.name", "sheaves-node-1")
                    .put("network.host", "127.0.0.1")
                    .put("transport.tcp.port", "9300")
                    .put("client.transport.sniff", true)
                    .build();

            Client client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));


            indexDocument(client, "twitter", "tweet", "4", jsonBuilder()
                    .startObject()
                    .field("user", "sheavills")
                    .field("postDate", new Date())
                    .field("message", "elastic")
                    .endObject());

            searchDocument(client, "twitter", "tweet", "user", "sheavills", "message", "elastic");

            getDocument(client, "twitter", "tweet", "3");

            updateDocument(client, "twitter", "tweet", "3", "message", "this is awesome");

            deleteDocument(client, "twitter", "tweet", "3");

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void indexDocument(Client client, String index, String type, String id,
                                      XContentBuilder sourceBuilder) {
        try {
            IndexResponse response = client.prepareIndex(index, type, id)
                    .setSource(sourceBuilder)
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void searchDocument(Client client, String index, String type,
                                      String field, String value, String field2, String value2){

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery(field, value))
                .must(QueryBuilders.termQuery(field2, value2));

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(queryBuilder)
                .get();

        SearchHit[] results = response.getHits().getHits();

        System.out.println("Current results: " + results.length);
        for (SearchHit hit : results) {
            Map<String,Object> result = hit.getSource();
            System.out.println(result);
        }
    }

    public static void deleteDocument(Client client, String index, String type, String id){

        DeleteResponse response = client.prepareDelete(index, type, id).get();
        System.out.println("Information on the deleted document:");
        System.out.println("Index: " + response.getIndex());
        System.out.println("Type: " + response.getType());
        System.out.println("Id: " + response.getId());
        System.out.println("Version: " + response.getVersion());
    }

    public static void updateDocument(Client client, String index, String type,
                                      String id, String field, String newValue){

        try {
            client.prepareUpdate(index, type, id)
                    .setDoc(jsonBuilder()
                            .startObject()
                            .field(field, newValue)
                            .endObject())
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDocument(Client client, String index, String type, String id){

        GetResponse getResponse = client.prepareGet(index, type, id)
                .get();
        Map<String, Object> source = getResponse.getSource();

        System.out.println("------------------------------");
        System.out.println("Index: " + getResponse.getIndex());
        System.out.println("Type: " + getResponse.getType());
        System.out.println("Id: " + getResponse.getId());
        System.out.println("Version: " + getResponse.getVersion());
        System.out.println(source);
        System.out.println("------------------------------");

    }





}
