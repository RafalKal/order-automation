package com.example.orderautomation.service;

import com.example.orderautomation.entities.User;
import com.example.orderautomation.entities.UserExternalSystemAuth;
import com.example.orderautomation.entities.repositories.UserRepository;
import com.example.orderautomation.model.OrderResult;
import com.example.orderautomation.model.order.EtradeProduct;
import com.example.orderautomation.model.products.BaselinkerOrderProduct;
import com.example.orderautomation.model.products.OrderItem;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderService {

    private final UserRepository userRepository;
    private final BaselinkerRepository baselinkerRepository;
    private final EtradeRepository etradeRepository;

    public OrderService(UserRepository userRepository, BaselinkerRepository baselinkerRepository, EtradeRepository etradeRepository) {
        this.userRepository = userRepository;
        this.baselinkerRepository = baselinkerRepository;
        this.etradeRepository = etradeRepository;
    }

    public List<OrderItem> mapBaselinkerProductsToEtrade(List<BaselinkerOrderProduct> baselinkerOrderProducts, String etradeToken) throws IOException {
        List<EtradeProduct> products = etradeRepository.getProducts(etradeToken);
        List<OrderItem> orderItemsList = new ArrayList<>();


        for(int i=0; i<baselinkerOrderProducts.size(); i++){
            // ilość produktu i-tego z baselinkera
            int count = baselinkerOrderProducts.get(i).getQuantity();
            // ean produktu i-tego z baselinkera
            String ean = baselinkerOrderProducts.get(i).getEan();

            List<EtradeProduct> productsWithGivenEan = products.stream().filter(p -> p.getEan().equals(ean)).toList();
            String id = productsWithGivenEan.get(0).getId();

            orderItemsList.add(new OrderItem(id, count));
        }
        return orderItemsList;
    }

    public OrderResult createOrder(String userToken, String orderId) throws IOException {
        User user = userRepository.findByToken(userToken)
                .orElseThrow( () -> new IllegalArgumentException("User not found by token.") );

        Optional<UserExternalSystemAuth> baselinker = user.getUserAuths()
                .stream()
                .filter(t -> t.getServiceName().equals("Baselinker"))
                .findFirst();

        String baselinkerToken = baselinker
                .map(b -> b.getAuthorization())
                .orElseThrow( () -> new IllegalStateException("User doesn't have Baselinker token") );

        Optional<UserExternalSystemAuth> etrade = user.getUserAuths()
                .stream()
                .filter(t -> t.getServiceName().equals("Etrade"))
                .findFirst();

        String etradeToken = baselinker
                .map(b -> b.getAuthorization())
                .orElseThrow( () -> new IllegalStateException("User doesn't have Etrade token") );

        String orderStatus = baselinkerRepository.getStatusIdFromOrder(orderId, baselinkerToken);

        List<BaselinkerOrderProduct> baselinkerOrderProducts = baselinkerRepository.getProductsWithAttribute(orderStatus, "", baselinkerToken);
        List<OrderItem> orderItemsList = mapBaselinkerProductsToEtrade(baselinkerOrderProducts, etradeToken);

        String orderItemsListJSON = new Gson().toJson(orderItemsList);

        etradeRepository.createEtradeOrder(etradeToken, orderItemsListJSON);

        return null;
    }


    @PostConstruct
    public void test() throws IOException {
        // TEST AREA
        // PLACE WHERE THE CODE IS AUTOMATICALLY EXECUTED AFTER THE APPLICATION STARTS
    }

}
