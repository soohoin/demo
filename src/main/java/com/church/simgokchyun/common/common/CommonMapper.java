package com.church.simgokchyun.common.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.BoardLike;
import com.church.simgokchyun.common.vo.Comcode;
import com.church.simgokchyun.common.vo.User;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommonMapper {
 
    List<Comcode> select_com_code(String cd_grp_enm) throws Exception;

    List<Board> select_boardList(Board board)throws Exception;

    Board select_boardDetail(Board board)throws Exception;

    int insertBoard(Board board)throws Exception;

    int getTotalCnt(Board board)throws Exception;

    int fileSave(Map<String, Object> paramMap)throws Exception;

    String getFileId(Map<String, Object> paramMap)throws Exception; 

    String emailDubYn(User user)throws Exception;

    int joinUser(User user)throws Exception;

    User findByUsername(String email_addr);

    String getUserId(User user) throws Exception;

    User getUser(User user)throws Exception;

    int successJoin(User user) throws Exception;

    Optional<User> findByUserInfo(String email)throws Exception;

    Map<String, Object> getBoardLikeExistYn(BoardLike boardLike)throws Exception;

    /**
     * 조회고객 정보 INSERT
     * @param boardLike
     * @return
     * @throws Exception
     */
    int insertBoardlike(BoardLike boardLike) throws Exception;

    /**
     * 조회고객 정보 UPDATE
     * @param boardLike
     * @return
     * @throws Exception
     */    
    int updateBoardlike(BoardLike boardLike) throws Exception;
    
    /**
     * 게시글 조회 수 증가
     * @param boardLike
     * @return
     * @throws Exception
     */
    int increaseBoard(BoardLike boardLike) throws Exception;
    
}


