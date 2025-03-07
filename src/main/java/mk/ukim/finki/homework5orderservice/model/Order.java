package mk.ukim.finki.homework5orderservice.model;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private String product;
    private Long userId;

    public Order(Long id, String product, Long userId) {
        this.id = id;
        this.product = product;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Long getUserId() {
        return userId;
    }
}

