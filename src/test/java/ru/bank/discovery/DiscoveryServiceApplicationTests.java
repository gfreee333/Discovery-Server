package ru.bank.discovery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiscoveryServiceApplicationTests {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	// Test 1: Запуск нашего Spring Boot приложения
	@Test
	void contextLoads() {
	}
	// Тест 2: Проверяем, работает ли наш Eureka Server
	@Test
	void eurekaDashboardShouldBeAvailable() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + port + "/", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Eureka", "Instances");
	}
	// Тест 3: Проверка работоспособности Spring Actuator Health
	@Test
	void actuatorHealthShouldReturnUp() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + port + "/actuator/health", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("\"status\":\"UP\"");
	}
	// Тест 4: Проверка работоспособности Spring Actuator Info
	@Test
	void actuatorInfoShouldReturnApplicationInfo() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + port + "/actuator/info", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
	}
	// Тест 5: Проверка работоспособности Spring Actuator Metrics
	@Test
	void actuatorMetricsShouldReturnMetricsList() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + port + "/actuator/metrics", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("jvm", "system", "process");
	}
}
