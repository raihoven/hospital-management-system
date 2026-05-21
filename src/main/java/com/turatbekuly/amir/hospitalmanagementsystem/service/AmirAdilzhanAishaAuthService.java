package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaAuthResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaLoginRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.AmirAdilzhanAishaRegisterRequest;

public interface AmirAdilzhanAishaAuthService {

    AmirAdilzhanAishaAuthResponse register(AmirAdilzhanAishaRegisterRequest request);

    AmirAdilzhanAishaAuthResponse login(AmirAdilzhanAishaLoginRequest request);
}
