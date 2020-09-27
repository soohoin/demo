package com.church.simgokchyun.common.common;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

import com.church.simgokchyun.common.paging.Pagination;
import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.Comcode;

@Service
public class CommonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    CommonMapper mapper;

    /**
     * 공통코드 생성
     * @param cd_grp_enm
     * @return List<Comcode>
     * @throws Exception
     */
    public List<Comcode> select_com_code(String cd_grp_enm)throws Exception {
        return mapper.select_com_code(cd_grp_enm);
    }


    /**
     * 공통코드에 전체 코드를 추가해서 return 한다.
     * 
     * @param cd_grp_enm/options
     * option
     *   순서       option명       코드종류
     *    1         기본코드     
     *                           0 : 사용안함 (default)
     *                           1 : 전체
     *                           2 : 선택
     * 
     *    2       기본코드위치    0 : 맨위     (default)
     *                           1 : 맨 아래
     *    3
     *    4
     *    5
     * @return List<Comcode>
     * @throws Exception
     */
    public List<Comcode> makeCombo(String cd_grp_enm, String[] options)throws Exception {
        List<Comcode> resList = new ArrayList<Comcode>();
        String knm = "";
        switch(options[0]) {
            case "0":
                return select_com_code(cd_grp_enm);
            case "1":
                knm = "전체";
                break;
            case "2":    
                knm = "선택";
                break;
                
        }
        Comcode comcode = new Comcode();
        comcode.setCd_grp_enm(cd_grp_enm);
        comcode.setCode("");
        comcode.setCode_knm(knm);
        if("0".equals(options[1]) ) {
            resList.add(comcode);
            resList.addAll(select_com_code(cd_grp_enm));
        } else if("1".equals(options[1])) {
            resList = select_com_code(cd_grp_enm);
            resList.add(comcode);
        } 
        return resList;
    }


    /**
     * 오늘 날짜 생성 
     * @param format
     * @return String
     * @throws Exception
     */
    public String getDate(String format)throws Exception {
        if(format == null) return null;
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    
    /**
     * 오늘을 기준으로 cnt 년 전 까지의 년도를 List<Map<String,Object>> 로 return 한다.
     * @param cnt
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getYears(int cnt)throws Exception {
        int curYear = Integer.parseInt(this.getDate("yyyy"));
        List<Map<String,Object>> resYears = new ArrayList<Map<String,Object>>();
        Map<String,Object> resMap;
        for(int i=0; i < cnt ; i++) {
            resMap = new HashMap<String,Object>();
            String addYear = (curYear - i) + "";
            resMap.put("code", addYear);
            resMap.put("code_knm", addYear+"년");
            resYears.add(resMap);

            if(i == cnt-1) {
                resMap = new HashMap<String,Object>();
                resMap.put("code", "");
                resMap.put("code_knm", "전체");
                resYears.add(resMap);
            }
        }
        return resYears;
    }


    
    /**
     * 게시판 페이징을 위한 메소드 
     * 
     * @param 1.totalListCnt/2.Pagination/3.model/4.board
     * 
     *        1. totalListCnt       - 총 게시글 전체 개수 (조회 할 조건으로 게시판 글의 총 개수를 구해서 넘겨준다.)
     *        2. Request Pagination - client 에서 요청받은 pagination 객체
     *        3. model              - controller 에서 return 할 model 객체 
     *        4. Request board      - client 에서 요청받은 board 객체 
     * 
     * @return void
     * @throws Exception
     *  
     */
    public void getPaginationInfo(int totalListCnt, Pagination reqPagination, Model model, Board board )throws Exception {
        List<Map<String,Object>> pageNumbers = new ArrayList<Map<String,Object>>();
        Map<String,Object> row;
        int currPage = 1;


        // 1. 페이지 번호 초기화 or 요청 페이지 setting
        if(reqPagination.getPage() != 1) {
            currPage = reqPagination.getPage();
        }


        // 2. 페이징을 위해 pagination 을 초기화 한다.  
        Pagination pagination = new Pagination(totalListCnt, currPage);


        // 3. View 에서 사용 할 정보를 넣어준다.
        model.addAttribute("totalListCnt", pagination.getTotalListCnt());
        model.addAttribute("block", pagination.getBlock());
        model.addAttribute("prevBlock", pagination.getPrevBlock());
        model.addAttribute("page", pagination.getPage());
        model.addAttribute("nextBlock", pagination.getNextBlock());
        model.addAttribute("lastBlock", pagination.getLastBlock());
        model.addAttribute("lastBlockShowNo", pagination.getLastBlockShowNo());
        model.addAttribute("totalBlockCnt", pagination.getTotalBlockCnt());


        // 4. 실제 생성 될 페이지 버튼의 리스트를 만들어서 넣어준다.
        for(int i = pagination.getStartPage() ; i <= pagination.getEndPage() ; i++) {
            row = new HashMap<String,Object>();
            row.put("no", i);
            pageNumbers.add(row);
        }
        model.addAttribute("pageNumbers", pageNumbers);


        // 5. 조회에 사용 할  1. 시작index  2. 페이지 개수를 board 객체에 넣어준다.
        board.setStart_index(pagination.getStartIndex());
        board.setPage_size(pagination.getPageSize());
    }


    /**
     * 공통 게시글 총 글의 개수 조회
     */
    public int getTotalCnt(Board board)throws Exception {
        return mapper.getTotalCnt(board);
    }

    /**
     * 공통 게시글 조회
     */
    public List<Board> select_boardList(Board board) throws Exception{ 
        return mapper.select_boardList(board);
    }

    /**
     * 공통 게시글 상세 조회
     */
    public Board select_boardDetail(Board board) throws Exception{
        return mapper.select_boardDetail(board);
    }

    /**
     * 공통 게시글 추가
     */
    public int insertBoard(Board board) throws Exception{
        return mapper.insertBoard(board);
    }

}
