package com.turatbekuly.amir.hospitalmanagementsystem.service;

import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirAuthResponse;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirLoginRequest;
import com.turatbekuly.amir.hospitalmanagementsystem.dto.TuratbekulyAmirRegisterRequest;

public interface TuratbekulyAmirAuthService {

    TuratbekulyAmirAuthResponse register(TuratbekulyAmirRegisterRequest request);

    TuratbekulyAmirAuthResponse login(TuratbekulyAmirLoginRequest request);
}
