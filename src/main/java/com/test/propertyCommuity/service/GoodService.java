package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.GoodDto;
import com.test.propertyCommuity.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodService {

    private GoodRepository goodRepository;

    @Autowired
    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    //TODO
    public GoodDto findById() { return null;}

    public GoodDto initGood(GoodDto dto) {
        return goodRepository.save(dto.toEntity()).toDto();
    }
    public GoodDto save(GoodDto dto) {
        dto.setGoodCnt(dto.getGoodCnt()+1);
        return goodRepository.save(dto.toEntity()).toDto();
    }

    //TODO
    public void delete() {}

}
