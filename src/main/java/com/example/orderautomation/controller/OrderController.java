package com.example.orderautomation.controller;

import org.springframework.web.bind.annotation.*;
import com.example.orderautomation.service.OrderService;
import com.example.orderautomation.model.OrderResult;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public OrderResult createOrder(@RequestParam String userToken, @RequestParam String orderId) throws IOException {

        return orderService.createOrder(userToken, orderId);
    }


}
