package com.test.propertyCommuity.controller;

import com.test.propertyCommuity.service.LikesUserService;
import com.test.propertyCommuity.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReplyApiController {

    private ReplyService replyService;
    private LikesUserService likesReplyService;
    @Autowired
    public ReplyApiController(ReplyService replyService, LikesUserService likesReplyService) {
        this.replyService = replyService;
        this.likesReplyService = likesReplyService;
    }

//    @GetMapping("/reply")
//    public ApiResponseUtil<List<ReplyDto>> list() {
//        ApiResponseUtil<List<ReplyDto>> response = null;
//
//        try {
//            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), replyService.findAll());
//        }catch (Exception e) {
//            response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
//        } finally {
//            if(response == null)
//                response = new ApiResponseUtil<List<ReplyDto>>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
//        }
//
//        return response;
//    }

//    @PostMapping("/reply")
//    public ApiResponseUtil<ReplyDto> postBoard(@RequestBody ReplyDto dto) {
//        ApiResponseUtil<ReplyDto> response = null;
//
//        try {
//            GoodDto good = GoodDto.builder().goodCnt(0).build();
//            good = goodService.initGood(good);
//
//            MemberDto member = MemberDto.builder().id(dto.getUserId()).build();
//
//            dto.setMember(member.toEntity());
//            dto.setGood(good.toEntity());
//            dto.setCreatedAt(new Date());
//            BoardDto newBoard = boardService.saveBoard(dto);
//
//            dto.setId(newBoard.getId());
//
//            response = new ApiResponseUtil<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), dto);
//        }catch (Exception e) {
//            e.printStackTrace();
//            response = new ApiResponseUtil<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
//        } finally {
//            if(response == null)
//                response = new ApiResponseUtil<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
//        }
//        return response;
//    }
//    @PutMapping("/reply")
//    @DeleteMapping("/reply/{id}")
}
