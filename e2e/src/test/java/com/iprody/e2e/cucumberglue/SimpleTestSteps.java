package com.iprody.e2e.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class SimpleTestSteps {

    @LocalServerPort
    private String port;
    private ResponseEntity<String> response;


    @When("the client calls endpoint {string}")
    public void whenClientCalls(String url) {
        try {
            response = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.GET, null,
                    String.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            httpClientErrorException.printStackTrace();
        }
    }

    @Then("response status code is {int}")
    public void thenStatusCodee(int expected) {
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStatusCode());
        assertThat("status code is" + expected,
                response.getStatusCodeValue() == expected);
    }

    @Then("response status code is not present")
    public void thenStatusCodeeIsNotPresent() {
        Assertions.assertNull(response);
    }

    @Then("returned string should be {string}")
    public void thenStringIs(String expected) {
        Assertions.assertEquals(expected, response.getBody());
    }

}