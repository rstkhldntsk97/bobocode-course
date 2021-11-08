package com.bobocode.service;

import com.bobocode.annotation.Trimmed;
import org.springframework.stereotype.Service;

@Service
@Trimmed
public class BadAssService {

    public String greeting(String str) {
        return str;
    }

}
