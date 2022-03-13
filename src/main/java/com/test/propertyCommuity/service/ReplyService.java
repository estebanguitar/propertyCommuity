package com.test.propertyCommuity.service;

import com.test.propertyCommuity.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }


//    public List<ReplyDto> findAll() {
//        return replyRepository.findAll().stream().map(Reply::toDto).collect(Collectors.toList());
//    }
//
//    public ReplyDto save(ReplyDto dto) {
//        return replyRepository.save(dto.toEntity()).toDto();
//    }





}
