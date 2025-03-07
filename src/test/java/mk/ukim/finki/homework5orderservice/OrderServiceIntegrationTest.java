package mk.ukim.finki.homework5orderservice;

import mk.ukim.finki.homework5orderservice.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetOrderById() {
        String url = "http://localhost:" + port + "/orders/1";
        ResponseEntity<Order> response = restTemplate.getForEntity(url, Order.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getProduct()).isEqualTo("Laptop");
    }
}
