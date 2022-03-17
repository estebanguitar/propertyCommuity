# JPA 게시판
## H2 DATABASE
url : jdbc:h2:tcp://localhost/~/test
username : sa


## 검증
1. 검증을 위한 기본 데이터 세팅을 위해 Postman을 사용하여 각 메소드 별 CRUD 확인 및 권한체크.
2. 모든 GET method 테스트 케이스 작성 Request Header 내 
Authorization의 유무에 따른 POST, PUT, DELETE 테스트 케이스 작성 및 테스트 완료
>![img_1.png](img_1.png) ![img.png](img.png)
## 구현방식
1. 모든 데이터는 ApiResponseUtil 제네릭 클래스를 정의하여 response data format을 규격화함.
```
    {
        "statusCode" : "서버 응답 코드",
        "message" : "서버 응답 메세지. 오류일 경우 오류 관련 메세지"
        "data" : "제네릭 타입으로 각 DTO 및 여러 데이터 일 경우 HashMap 반환"
    }
```
2. Entity와 DTO 분리
3. Requset Header에 포함된 Authorization은 AuthInterceptor에서 파싱하여 파라미터화. 
해당 파라미터는 각 Controller에서 userId를 DTO에 삽입.
```
    HttpServletRequest.setAttribute("accountType", accountType);
    HttpServletRequest.setAttribute("userId", userId);
```
4. AuthInterceptor에서 Request method를 확인하고 GET 요청이 아닐경우 AuthController로 redirect.
```
    @GetMapping("/notAllowed")
    public ApiResponseUtil notAllowed() {
        return new ApiResponseUtil(HttpStatus.UNAUTHORIZED.value(), 
            HttpStatus.UNAUTHORIZED.getReasonPhrase(), 
            null);
    }
```
5. POST, PUT, DELETE 요청은 valid 메소드를 정의하여 해당 글(게시글, 댓글)의 정보와 대조하도록 함. 
```
    private void valid(BoardDto dto) throws Exception{
        if(!boardService.isUsersBoard(dto.getMember().getId(), dto.getId())) 
            throw new Exception("Not Found");
    }
```
6. 좋아요 처리시 Board와 LikesUser의 트랜잭션 처리를 위해 LikesUserBoardService를 생성하고
처리 실패 시 모두 rollback 처리하도록 함.
```
    @Transactional(rollbackFor = Exception.class)
    public void save(LikesUserDto dto) throws Exception{
        BoardDto boardDto = boardRepository.findById(dto.getBoard().getId())
                .orElseThrow(() -> new Exception("Board Not Found")).toDto();
        boardDto.setLikes(boardDto.getLikes() + 1L);
        boardRepository.save(boardDto.toEntity());

        dto.setBoard(boardDto.toEntity());
        likesUserRepository.save(dto.toEntity());
    }
```
