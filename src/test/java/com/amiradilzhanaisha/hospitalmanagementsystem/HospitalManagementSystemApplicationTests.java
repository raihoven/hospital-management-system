package com.amiradilzhanaisha.hospitalmanagementsystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HospitalManagementSystemApplicationTests {

    @Test
    void applicationClassIsPresent() {
        assertThat(AmirAdilzhanAishaHospitalApplication.class.getPackageName())
                .isEqualTo("com.amiradilzhanaisha.hospitalmanagementsystem");
    }

}
