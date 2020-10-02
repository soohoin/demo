package com.church.simgokchyun.common.common;

import java.util.List;
import java.util.Map;

import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.Comcode;

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
}
