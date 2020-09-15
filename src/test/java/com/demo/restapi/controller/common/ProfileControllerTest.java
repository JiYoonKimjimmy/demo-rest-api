package com.demo.restapi.controller.common;

import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jim, Kim
 * @since 2020-09-15
 */
public class ProfileControllerTest {

    @Test
    public void real_profile() {
        String expectedProfile = "real";
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.addActiveProfile(expectedProfile);
        mockEnvironment.addActiveProfile("db");

        ProfileController controller = new ProfileController(mockEnvironment);

        String profile = controller.profile();

        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void default_profile() {
        String expectedProfile = "default";
        MockEnvironment mockEnvironment = new MockEnvironment();

        ProfileController controller = new ProfileController(mockEnvironment);

        String profile = controller.profile();

        assertThat(profile).isEqualTo(expectedProfile);
    }

}
