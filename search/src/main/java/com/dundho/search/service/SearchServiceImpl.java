package com.dundho.search.service;

import com.dundho.search.entity.Products;
import com.dundho.search.entity.Mongo;
import com.dundho.search.model.ProductsDTO;
import com.dundho.search.repository.MongoRepo;
import com.dundho.search.repository.SearchRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private TransportClient client;
    @Autowired
    private MongoRepo mongoRepository;

    @PostConstruct
    public void after() {
        try {
            Settings settings = Settings.settingsBuilder().put("cluster.name", "search").build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void before() {
        client.close();
    }

    @Override
    @Transactional
    public ProductsDTO saveProduct(ProductsDTO ProductsDTO) {
        Products product = new Products();
        BeanUtils.copyProperties(ProductsDTO, product);
        product.setId(this.genId());
        if( searchRepository.exists(product.getId())) {
            throw new RuntimeException();
        }
        Products result = searchRepository.save(product);
        ProductsDTO resultDTO = new ProductsDTO();
        BeanUtils.copyProperties(result,resultDTO);
        return resultDTO;
    }

    @Override
    public ProductsDTO getProduct(String id) {
        Products result = searchRepository.findOne(id);
        ProductsDTO resultDTO = new ProductsDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    @Transactional
    public ProductsDTO deleteProduct(String id) {
        Products product = searchRepository.findOne(id);
        searchRepository.delete(product);
        ProductsDTO result = new ProductsDTO();
        BeanUtils.copyProperties(product,result);
        return result;
    }

    @Override
    @Transactional
    public ProductsDTO putProduct(ProductsDTO ProductsDTO) {
        Products product = new Products();
        BeanUtils.copyProperties(ProductsDTO, product);
        Products result = searchRepository.save(product);
        ProductsDTO resultDTO= new ProductsDTO();
        BeanUtils.copyProperties(result,resultDTO);
        return resultDTO;
    }

    @Override
    public List<ProductsDTO> getAllProduct() {
        Iterable<Products> productList = searchRepository.findAll();
        List<ProductsDTO> resultList = new ArrayList<>();

        for ( Products e: productList) {
            ProductsDTO ProductsDTO =new ProductsDTO();
            BeanUtils.copyProperties(e,ProductsDTO);
            resultList.add(ProductsDTO);
        }
        return resultList;
    }

    @Override
    public JSONArray query(String recievedQuery) throws ParseException {
        String searchQuery="";
        String[] words = recievedQuery.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
            searchQuery+="*"+words[i]+"* ";
        }
        SearchResponse response = client.prepareSearch().setQuery(QueryBuilders.queryStringQuery(searchQuery)).execute().actionGet();
        JSONParser parser = new JSONParser();
        JSONArray result = (JSONArray)((JSONObject)((JSONObject)parser.parse(response.toString())).get("hits")).get("hits");
        JSONArray resultAray = new JSONArray();
        for (Object object: result) {
            resultAray.add(((JSONObject)object).get("_source"));
        }
        return resultAray;
    }

    @Override
    public String genId()
    {
        int n = 10;
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));
        StringBuffer r = new StringBuffer();
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");
        for (int k = 0; k < AlphaNumericString.length(); k++) {
            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }
        return r.toString();
    }

    @Override
    public CompletableFuture<String> addAll(List<ProductsDTO> productsDTOS) throws InterruptedException {
        for (ProductsDTO pro:productsDTOS) {
            this.saveProduct(pro);
        }
        return CompletableFuture.completedFuture("Success");
    }

    @Override
    public Object addAllmongo(List<ProductsDTO> productsDTO) throws InterruptedException {
        Mongo m;
        List<Mongo> mongos = new ArrayList<>();
        long count = mongoRepository.count();
        for(ProductsDTO p : productsDTO) {
            m = new Mongo();
            if(p.getBrand() != null) {
                m.setVal(p.getBrand());
                m.setKey("brand");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getCategory() != null) {
                m = new Mongo();
                m.setVal(p.getCategory());
                m.setKey("category");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getColor() != null) {
                m = new Mongo();
                m.setVal(p.getColor());
                m.setKey("color");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getModelNo() != null) {
                m = new Mongo();
                m.setVal(p.getModelNo());
                m.setKey("modelNo");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getSubCategory() != null) {
                m = new Mongo();
                m.setVal(p.getSubCategory());
                m.setKey("subCategory");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getTitle() != null) {
                m = new Mongo();
                m.setVal(p.getTitle());
                m.setKey("title");
                m.setId(count++);
                mongos.add(m);
            }
            if(p.getProductType() != null) {
                m = new Mongo();
                m.setVal(p.getProductType());
                m.setKey("productType");
                m.setId(count++);
                mongos.add(m);
            }
        }
        mongoRepository.save(mongos);
        return mongos;
    }

    @Override
    public Object getAllmongo(String s) {
        List<String> items = Arrays.asList(s.split("\\s* \\s*"));
        List<String> res = new ArrayList<>();
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        for (String i : items) {
            System.out.println(i);
            res = mongoRepository.findByVal(i);
            for (String t : res) {
                query.should(QueryBuilders.termQuery(t, i));
            }
        }

        SearchResponse response = client.prepareSearch().setQuery(query).setFrom(0).setSize(1000).execute().actionGet();
        JSONParser parser = new JSONParser();
        JSONArray result = null;
        try {
            result = (JSONArray)((JSONObject)((JSONObject)parser.parse(response.toString())).get("hits")).get("hits");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray resultArray = new JSONArray();
        for (Object object: result) {
            resultArray.add(((JSONObject)object).get("_source"));
        }
        return resultArray;
    }
}
