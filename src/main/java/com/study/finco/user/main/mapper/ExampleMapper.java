package com.study.finco.user.main.mapper;

import com.study.finco.user.main.model.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExampleMapper {
    User findById(Long id);
}
