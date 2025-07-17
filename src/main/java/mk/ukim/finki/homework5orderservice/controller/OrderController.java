package mk.ukim.finki.homework5orderservice.controller;

import mk.ukim.finki.homework5orderservice.model.Order;
import mk.ukim.finki.homework5orderservice.model.dto.OrderDTO;
import mk.ukim.finki.homework5orderservice.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    private final List<Order> orders = List.of(
            new Order(1L, "Laptop", 1L),
            new Order(2L, "Phone", 2L),
            new Order(3L, "PC", 1L),
            new Order(4L, "TV", 2L),
            new Order(5L, "Smartwatch", 2L)
    );

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    @GetMapping("/{id}/with-user")
    public OrderDTO findOrderWithUser(@PathVariable Long id) {
        Order order = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (order == null) {
            return null;
        }

//        String userServiceUrl = "http://localhost:8083/users/" + order.getUserId();
        String userServiceUrl = "http://user-service:8083/users/" + order.getUserId();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setProduct(order.getProduct());
        UserDTO user = restTemplate.getForObject(userServiceUrl, UserDTO.class);
        orderDTO.setUser(user);

        return orderDTO;
    }
}
