package com.dillonboardman.agevalidator.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgeValidatorEndpointTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testValidateMachineAges_WithNoOutliers() {
    var url = "http://localhost:" + port + "/v1/validate";
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    var body = Arrays.asList(
        "01, 1 year",
        "02, 16 months",
        "03, 90 days",
        "04, 90 days",
        "05, 90 days",
        "06, 90 days",
        "07, 90 days",
        "08, 90 days",
        "09, 90 days",
        "10, 90 days",
        "11, 90 days"
    );
    var request = new HttpEntity<>(body, headers);
    var response = restTemplate.exchange(url, HttpMethod.POST, request, List.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  public void testValidateMachineAges_WithOutliers() {
    var url = "http://localhost:" + port + "/v1/validate";
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    var body = Arrays.asList(
        "01, 1 year",
        "02, 16 months",
        "03, 90 days",
        "04, 90 days",
        "05, 90 days",
        "06, 90 days",
        "07, 90 days",
        "08, 90 days",
        "09, 90 days",
        "10, 90 days",
        "11, 90 years"
    );
    var request = new HttpEntity<>(body, headers);
    var response = restTemplate.exchange(url, HttpMethod.POST, request, List.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("11", response.getBody().get(0));
  }

  @Test
  public void testValidateMachineAges_WithInvalidAgeFormat() {
    var url = "http://localhost:" + port + "/v1/validate";
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    var body = Arrays.asList(
        "01, 1 year",
        "02, 16 months",
        "03, 90 days",
        "04, 90 days",
        "05, 90 days",
        "06, 90 days",
        "07, 90 days",
        "08, 90 days",
        "09, 90 days",
        "10, 90 days",
        "11, 90 yearss"
    );
    var request = new HttpEntity<>(body, headers);
    var response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
