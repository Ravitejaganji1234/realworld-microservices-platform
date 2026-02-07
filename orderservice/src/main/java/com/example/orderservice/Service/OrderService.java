package com.example.orderservice.Service;

import com.example.orderservice.Entity.Order;
import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.UserClient.UserClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final UserClient userClient;

    public OrderService(OrderRepository repo, UserClient userClient) {
        this.repo = repo;
        this.userClient = userClient;
    }

    public Order create(Order order) {
        if (!userClient.userExists(order.getUserId())) {
            throw new RuntimeException("User not found");
        }
        return repo.save(order);
    }

    public List<Order> byUser(Long userId) {
        return repo.findByUserId(userId);
    }
}

