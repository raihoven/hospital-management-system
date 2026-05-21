package com.amiradilzhanaisha.hospitalmanagementsystem.service;

import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaAuthResponse;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaLoginRequest;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaRegisterRequest;

public interface AmirAdilzhanAishaAuthService {

    AmirAdilzhanAishaAuthResponse register(AmirAdilzhanAishaRegisterRequest request);

    AmirAdilzhanAishaAuthResponse login(AmirAdilzhanAishaLoginRequest request);
}
