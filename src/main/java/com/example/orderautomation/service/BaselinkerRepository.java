package com.example.orderautomation.service;

import com.example.orderautomation.model.products.BaselinkerOrderCatalog;
import com.example.orderautomation.model.products.BaselinkerOrderProduct;
import com.example.orderautomation.model.products.BaselinkerOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.*;

@Component
public class BaselinkerRepository {

    private final ObjectMapper objectMapper;
    private final Gson gson;

    public BaselinkerRepository(ObjectMapper objectMapper, Gson gson) {
        this.objectMapper = objectMapper;
        this.gson = gson;
    }

    public List<BaselinkerOrder> getOrdersWithGivenStatus(String statusId, String token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "method=getOrders&parameters={\"status_id\": " + statusId + "}");
        Request request = new Request.Builder()
                .url("https://api.baselinker.com/connector.php")
                .method("POST", body)
                .addHeader("X-BLToken", token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), BaselinkerOrderCatalog.class).getOrders();
    }

    public List<BaselinkerOrder> getOrdersWithGivenId(String orderId, String token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "method=getOrders&parameters={\"order_id\": " + orderId + "}");
        Request request = new Request.Builder()
                .url("https://api.baselinker.com/connector.php")
                .method("POST", body)
                .addHeader("X-BLToken", token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), BaselinkerOrderCatalog.class).getOrders();
    }

    public List<BaselinkerOrderProduct> getProducts(String statusId, String token) throws IOException {
        List<BaselinkerOrder> orders = getOrdersWithGivenStatus(statusId, token);
        List<BaselinkerOrderProduct> products = orders.get(0).getProducts();


        for (int i=1; i<orders.size(); i++){
            products.addAll(orders.get(i).getProducts());
        }
        return products;
    }

    public List<BaselinkerOrderProduct> getProductsWithAttribute(String statusId, String attribute, String token) throws IOException {
        return getProducts(statusId, token)
                .stream()
                .filter(p -> p.getAttribute().equals(attribute))
                .collect(Collectors.toList());
    }

    public String getStatusIdFromOrder(String orderId, String token) throws IOException {
        return String.valueOf(getOrdersWithGivenId(orderId, token).get(0).getOrder_status_id());
    }

}
