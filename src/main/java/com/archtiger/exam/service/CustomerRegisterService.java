package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;

public interface CustomerRegisterService {

    CustomerRegisterResponse register(CustomerRegisterRequest request) throws ExamException;

}
