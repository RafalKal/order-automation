package com.example.orderautomation.service;

import com.example.orderautomation.model.order.EtradeOrderProductCatalog;
import com.example.orderautomation.model.order.EtradeOrderProduct;
import com.example.orderautomation.model.products.ProductCatalog;
import com.example.orderautomation.model.products.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public class EtradeRepository {

    private final ObjectMapper objectMapper;
    private final String SERVICE_NAME = "etrade";

    public EtradeRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /*
        METHODS WITH GETTING ETRADE PRODUCTS.
    */
    public List<Product> getProductPage(int page, String token) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("https://api.etrade.pl/v1/products?page=" + page + "&perPage=1000")
                .method("GET", null)
                .addHeader("Authorization", "Basic " + token)
                .addHeader("Cookie", "PHPSESSID=s3fnld9tgekmrqqa5duk3ipk3l")
                .build();

        Response response = client.newCall(request).execute();

        return objectMapper.readValue(response.body().string(), ProductCatalog.class).getResults();
    }

    public List<EtradeOrderProduct> getProductPageAndMapToEtradeProduct(int page, String token) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url("https://api.etrade.pl/v1/products?page=" + page + "&perPage=1000")
                .method("GET", null)
                .addHeader("Authorization", "Basic " + token)
                .addHeader("Cookie", "PHPSESSID=s3fnld9tgekmrqqa5duk3ipk3l")
                .build();

        Response response = client.newCall(request).execute();

        return objectMapper.readValue(response.body().string(), EtradeOrderProductCatalog.class).getResults();
    }

    public List<Product> getProducts(String token) throws IOException {
        List<Product> products = getProductPage(0, token);
        for(int i=1; i<=6;i++){
            products.addAll(getProductPage(i, token));
        }
        return products;
    }

    public List<EtradeOrderProduct> getProductsMappedToEtradeProduct(String token) throws IOException {
        List<EtradeOrderProduct> products = getProductPageAndMapToEtradeProduct(0, token);
        for (int i = 1; i <= 6; i++) {
            products.addAll(getProductPageAndMapToEtradeProduct(i, token));
        }
        return products;
    }

    /*
        ETRADE ORDER-RELATED METHODS
    */
        public void createEtradeOrder(String token) throws IOException {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(mediaType,
                    "{\n" +
                            "    \"order\": {\n" +
                            "        \"details\": {\n" +
                            "            \"deliveryMethodId\": \"COURIER\",\n" +
                            "            \"address\": 16683,\n" +
                            "            \"additionalData\": {\n" +
                            "                \"comment\": null,\n" +
                            "                \"extNo\": null,\n" +
                            "                \"invoiceBundle\": true,\n" +
                            "                \"dontShipInvoice\": false,\n" +
                            "                \"printComment\": false,\n" +
                            "                \"phone\": \"533331632\"\n" +
                            "            }\n" +
                            "        },\n" +
                            "        \"items\": [\n" +
                            "            {\n" +
                            "                \"product\": \"Z28897\",\n" +
                            "                \"count\": 1\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"product\": \"Z31145\",\n" +
                            "                \"count\": 1\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n}");

            Request request = new Request.Builder()
                    .url("https://api.etrade.pl/v1/orders")
                    .method("POST", body)
                    .addHeader("Authorization", "Basic " + token)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "PHPSESSID=00g9hk0ad321phg50m7nviqqt1")
                    .build();

            Response response = client.newCall(request).execute();
        }
}