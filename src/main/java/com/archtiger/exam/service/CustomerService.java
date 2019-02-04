package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.AllCustomersResponse;

public interface CustomerService {

    AllCustomersResponse all() throws ExamException;

}
