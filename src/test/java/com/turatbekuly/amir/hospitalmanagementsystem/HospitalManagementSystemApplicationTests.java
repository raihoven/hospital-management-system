package com.turatbekuly.amir.hospitalmanagementsystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HospitalManagementSystemApplicationTests {

    @Test
    void applicationClassIsPresent() {
        assertThat(TuratbekulyAmirHospitalApplication.class.getPackageName())
                .isEqualTo("com.turatbekuly.amir.hospitalmanagementsystem");
    }

}
