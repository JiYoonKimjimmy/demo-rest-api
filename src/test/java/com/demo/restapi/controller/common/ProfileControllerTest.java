package com.demo.restapi.controller.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    public void real_profile() {
//        String expectedProfile = "real";
//        MockEnvironment mockEnvironment = new MockEnvironment();
//        mockEnvironment.addActiveProfile(expectedProfile);
//        mockEnvironment.addActiveProfile("db");
//
//        ProfileController controller = new ProfileController(mockEnvironment);
//
//        String profile = controller.profile();
//
//        assertThat(profile).isEqualTo(expectedProfile);
//    }
//
//    @Test
//    public void default_profile() {
//        String expectedProfile = "default";
//        MockEnvironment mockEnvironment = new MockEnvironment();
//
//        ProfileController controller = new ProfileController(mockEnvironment);
//
//        String profile = controller.profile();
//
//        assertThat(profile).isEqualTo(expectedProfile);
//    }

    @Test
    public void no_auth_call_profile() {
        String expected = "default";

        ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }


}
