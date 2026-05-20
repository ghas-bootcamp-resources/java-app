package com.acme.trainingportal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortalControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void homePageShowsSeedCourses() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("Acme Training Portal", "Secure Coding Fundamentals");
    }

    @Test
    void documentsEndpointListsSeedDocuments() {
        ResponseEntity<String> response = restTemplate.getForEntity("/documents", String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("onboarding.txt");
    }
}
