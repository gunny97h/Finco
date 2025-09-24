package com.study.finco.user.main.service;

import com.study.finco.user.main.mapper.ExampleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ExampleMapper exampleMapper;

    public String getEmail(){
        return exampleMapper.findById(1234L).getEmail();
    }

}
