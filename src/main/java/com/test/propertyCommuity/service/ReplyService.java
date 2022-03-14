package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.ReplyDto;
import com.test.propertyCommuity.entity.Reply;
import com.test.propertyCommuity.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReplyService {

    private ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }


    public List<Reply> findAll(int isDelete, Long boardId) {
        return replyRepository.findByIsDeletedAndBoardId(isDelete, boardId);
    }

    public ReplyDto findById(Long id, int isDeleted) throws Exception{
        return replyRepository.findByIdAndIsDeleted(id, isDeleted).orElseThrow(() -> new NoSuchElementException("Reply Not Found")).toDto();
    }
    public boolean isUsersReply(Long userId, Long replyId) {
        return replyRepository.findByMemberIdAndId(userId, replyId).isPresent();
    }


    public ReplyDto save(Reply entity) throws Exception{
        return replyRepository.save(entity).toDto();
    }

    public void delete(Long id) throws Exception{
        ReplyDto dto = replyRepository.findById(id).get().toDto();
        dto.setIsDeleted(1);
        dto.setDeletedAt(new Date());
        replyRepository.save(dto.toEntity());
    }

}
