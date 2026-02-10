package com.example.orderservice.Controller;

import com.example.orderservice.Entity.Order;
import com.example.orderservice.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUser(@PathVariable Long userId) {
        return service.byUser(userId);
    }

    @GetMapping("/slow")
    public String slow(@RequestParam long delay) throws InterruptedException {
        Thread.sleep(delay);
        return "OK";
    }
}

