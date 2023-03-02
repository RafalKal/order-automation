package com.example.orderautomation.service;

import com.example.orderautomation.model.products.BaselinkerOrderProduct;
import com.example.orderautomation.model.OrderResult;
import com.example.orderautomation.entities.repositories.UserRepository;
import com.example.orderautomation.entities.User;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Component
public class OrderService {

    private UserRepository userRepository;
    private BaselinkerRepository baselinkerRepository;
    private EtradeRepository etradeRepository;

    public OrderService(UserRepository userRepository, BaselinkerRepository baselinkerRepository, EtradeRepository etradeRepository) {
        this.userRepository = userRepository;
        this.baselinkerRepository = baselinkerRepository;
        this.etradeRepository = etradeRepository;
    }

    public OrderResult createOrder(String userToken, String orderId) throws IOException {
        // TODO pobierz USER po tokenie, jezeli nie ma- błąd
        User user = userRepository.findByToken(userToken)
                .orElseThrow( () -> new IllegalArgumentException("User not found by token.") );

        Optional<String> baselinker = user.getUserAuths()
                .stream()
                .filter(t -> t.getServiceName().equals("Baselinker"))
                .map(t -> t.getAuthorization())
                .findFirst();

        String baselinkerToken = baselinker
                .orElseThrow( () -> new IllegalStateException("User doesn't have Baselinker token") );

        // TODO Pobierz status tego zamowienia, pobierz produkty do zamowienia z tego statusu, pobierz produkty z etrade na podstawie ean, zloz zamowienie
        int orderStatus = baselinkerRepository.getStatusIdFromOrder(orderId, baselinkerToken);
        List<BaselinkerOrderProduct> baselinkerOrderProducts = baselinkerRepository.getProductsWithAttribute(Integer.toString(orderStatus), "", baselinkerToken);

        return null;
    }

    @PostConstruct
    public void test() throws IOException {

    }

}
