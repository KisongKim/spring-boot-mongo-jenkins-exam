package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.CustomerSearchResultResponse;

public interface CustomerSearchService {

    CustomerSearchResultResponse searchCustomerByEmail(String email) throws ExamException;

}
