package com.church.simgokchyun.common.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.church.simgokchyun.common.mail.MailHandler;
import com.church.simgokchyun.common.paging.Pagination;
import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.BoardLike;
import com.church.simgokchyun.common.vo.BoardReReply;
import com.church.simgokchyun.common.vo.BoardReply;
import com.church.simgokchyun.common.vo.CallPageInfo;
import com.church.simgokchyun.common.vo.Comcode;
import com.church.simgokchyun.common.vo.User;
import com.church.simgokchyun.config.auth.PrincipalDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.servlet.multipart.location}")
    String defaultUploadPath;

    @Autowired
    CommonMapper mapper;

    /**
     * 공통코드 생성
     * 
     * @param cd_grp_enm
     * @return List<Comcode>
     * @throws Exception
     */
    public List<Comcode> select_com_code(String cd_grp_enm) throws Exception {
        return mapper.select_com_code(cd_grp_enm);
    }

    /**
     * 공통코드에 전체 코드를 추가해서 return 한다.
     * 
     * @param cd_grp_enm/options option 순서 option명 코드종류 1 기본코드 0 : 사용안함 (default) 1
     *                           : 전체 2 : 선택
     * 
     *                           2 기본코드위치 0 : 맨위 (default) 1 : 맨 아래 3 4 5
     * @return List<Comcode>
     * @throws Exception
     */
    public List<Comcode> makeCombo(String cd_grp_enm, String[] options) throws Exception {
        List<Comcode> resList = new ArrayList<Comcode>();
        String knm = "";
        switch (options[0]) {
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
        if ("0".equals(options[1])) {
            resList.add(comcode);
            resList.addAll(select_com_code(cd_grp_enm));
        } else if ("1".equals(options[1])) {
            resList = select_com_code(cd_grp_enm);
            resList.add(comcode);
        }
        return resList;
    }

    /**
     * 오늘 날짜 생성
     * 
     * @param format
     * @return String
     * @throws Exception
     */
    public String getDate(String format) throws Exception {
        if (format == null)
            return null;
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    /**
     * 오늘을 기준으로 cnt 년 전 까지의 년도를 List<Map<String,Object>> 로 return 한다.
     * 
     * @param cnt
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getYears(int cnt) throws Exception {
        int curYear = Integer.parseInt(this.getDate("yyyy"));
        List<Map<String, Object>> resYears = new ArrayList<Map<String, Object>>();
        Map<String, Object> resMap;
        for (int i = 0; i < cnt; i++) {
            resMap = new HashMap<String, Object>();
            String addYear = (curYear - i) + "";
            resMap.put("code", addYear);
            resMap.put("code_knm", addYear + "년");
            resYears.add(resMap);

            if (i == cnt - 1) {
                resMap = new HashMap<String, Object>();
                resMap.put("code", "");
                resMap.put("code_knm", "전체");
                resYears.add(resMap);
            }
        }
        return resYears;
    }


    /**
     * 업로드 파일저장
     * 
     * @param 1.file/2.file_div_cd 1. file - 업로드 할 파일객체 2. file_div_cd - 파일의 종류 구분코드
     *                             ( 01:이미지, 02:동영상 )
     * 
     *                             프로세스 1. 공통에서 parameter 1-이미지 오리지널파일명, 2-파일바이트 ,
     *                             3-구분코드[이미지/동영상] 를 받는다. 2. 오리지널파일명을 uuid+일자 를 사용해서
     *                             유니크한 파일명을 생성한다. 3. FILE_INFO 테이블에 키를 구하고 파일 정보를
     *                             INSERT 한다. 4. return 파일아이디
     * @throws Exception
     * @return String - 파일ID
     */
    public Map<String,Object> fileSave(MultipartFile file, String file_div_cd) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        String origin_file_nm = "";
        String real_file_nm = "";
        String uploadPath = "";

        // if("01".equals(file_div_cd) ) {
        // save_folder = "\\imgs";
        // } else {
        // save_folder = "\\video";
        // }

        // uploadPath = defaultUploadPath + File.separator + save_folder;
        uploadPath = defaultUploadPath + "imgs/"; // imgs 경로 

        // 1. 파일명 변경
        origin_file_nm = file.getOriginalFilename();
        real_file_nm = this.transFileName(origin_file_nm);

        paramMap.put("origin_file_nm", origin_file_nm);
        paramMap.put("real_file_nm", real_file_nm);
        paramMap.put("file_div_cd", file_div_cd);
        paramMap.put("file_size", file.getSize());
        paramMap.put("use_yn", "Y");
        paramMap.put("user_id", "100001");
        paramMap.put("file_path", uploadPath);

        logger.info("fileUploadPath : " + uploadPath);

        // 2. 파일정보 DB insert
        mapper.fileSave(paramMap);

        // 3. 업로드 경로에 파일생성
        file.transferTo(new File(uploadPath + real_file_nm));

        // 4. 파일 id를 return 한다.
        paramMap.put("file_id", mapper.getFileId(paramMap));
        return paramMap;
    }

    /**
     * 중복 방지를 위한 파일명 변경
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public String transFileName(String fileName) throws Exception {
        if (fileName == null || "".equals(fileName))
            return null;

        String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length()); // 확장자
        return this.getDate("yyyyMMdd") + "_" + UUID.randomUUID().toString() + extension;
    }

    /**
     * 게시판 페이징을 위한 메소드
     * 
     * @param 1.totalListCnt/2.Pagination/3.model/4.board
     * 
     *                                                    1. totalListCnt - 총 게시글 전체
     *                                                    개수 (조회 할 조건으로 게시판 글의 총 개수를
     *                                                    구해서 넘겨준다.) 2. Request
     *                                                    Pagination - client 에서
     *                                                    요청받은 pagination 객체 3.
     *                                                    model - controller 에서
     *                                                    return 할 model 객체 4.
     *                                                    Request board - client 에서
     *                                                    요청받은 board 객체
     * @return void
     * @throws Exception
     * 
     */
    public void getPaginationInfo(int totalListCnt, Pagination reqPagination, Model model, Board board)
            throws Exception {
        List<Map<String, Object>> pageNumbers = new ArrayList<Map<String, Object>>();
        Map<String, Object> row;
        int currPage = 1;

        // 1. 페이지 번호 초기화 or 요청 페이지 setting
        if (reqPagination.getPage() != 1) {
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
        for (int i = pagination.getStartPage(); i <= pagination.getEndPage(); i++) {
            row = new HashMap<String, Object>();
            row.put("no", i);
            pageNumbers.add(row);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        // 5. 조회에 사용 할 1. 시작index 2. 페이지 개수를 board 객체에 넣어준다.
        board.setStart_index(pagination.getStartIndex());
        board.setPage_size(pagination.getPageSize());
    }

    /**
     * 공통 게시글 총 글의 개수 조회
     */
    public int getTotalCnt(Board board) throws Exception {
        return mapper.getTotalCnt(board);
    }

    /**
     * 공통 게시글 조회
     */
    public List<Board> select_boardList(Board board) throws Exception {
        return mapper.select_boardList(board);
    }

    /**
     * 공통 게시글 상세 조회
     */
    public Board select_boardDetail(Board board, PrincipalDetails userDetails, HttpServletRequest request) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        BoardLike boardLike =  new BoardLike();
        

        // 1. 로그인 회원이면 ID를 넣고 로그인 정보가 없으면 IP주소를 넣는다.
        if(userDetails != null) {
            // logger.info("userDetails : " + userDetails.getUser());    
            boardLike.setClick_user_id(userDetails.getUser().getUser_id());
            board.setClick_user_id(userDetails.getUser().getUser_id());
        } else {
            String userIp = this.getIp(request);
            logger.info("userIp : " + userIp);
            logger.info("userIp.length : " + userIp.length());
            boardLike.setClick_user_id(userIp);
            board.setClick_user_id(userIp);
        }

        // 2. callback 호출이 아니면 조회 수 관련 logic을 수행한다.
        if("N".equals(board.getCallback_yn())){
            // 2-1. 게시글 조회 시 조회 수 를 올려준다. 
            
            boardLike.setBoard_div_cd(board.getBoard_div_cd());
            boardLike.setBoard_no(board.getBoard_no());
            boardLike.setClick_div_cd("01");  // 01: 조회수 ,  02: 좋아요

            map = mapper.getBoardLikeExistYn(boardLike);

            //  2-2. 오늘 조회수를 올린적이 없다면 해당 게시글의 조회수를 올려준다.
            if("N".equals(map.get("LIKE_EXISTYN"))) {

                // 게시글이 기존에 존재하면 update
                if("Y".equals(map.get("BOARD_EXISTYN"))) {
                    mapper.updateBoardlike(boardLike);
                    
                    // 게시글이 기존에 존재하지 않으면 insert
                } else {
                    mapper.insertBoardlike(boardLike);
                }
                
                // 최종 게시글 증가
                mapper.increaseBoard(boardLike);
            }
        }
        
        return mapper.select_boardDetail(board);
    }

    /**
     * 공통 게시글  likeInfo 조회
     */
    public Board selectLikeInfo(Board board, PrincipalDetails userDetails, HttpServletRequest request) throws Exception {
        
        // 1. 로그인 회원이면 ID를 넣고 로그인 정보가 없으면 IP주소를 넣는다.
        if(userDetails != null) {
            // logger.info("userDetails : " + userDetails.getUser());    
            board.setClick_user_id(userDetails.getUser().getUser_id());
        } else {
            String userIp = this.getIp(request);
            logger.info("userIp : " + userIp);
            logger.info("userIp.length : " + userIp.length());
            board.setClick_user_id(userIp);
        }
        return mapper.select_boardDetail(board);
    }

    public List<BoardReply> select_boardReply(Board board)throws Exception {
        return mapper.select_boardReply(board);
    }

    // 공통 댓글 , 답글  DELETE
    public int deleteBoardReply_reReply(BoardReply boardReply) throws Exception {
        int rsltVal = 0;

        // 1. 댓글을  삭제한다.
        if("REPLY".equals(boardReply.getKind_of_reply())) {
            rsltVal = mapper.deleteBoardReply(boardReply);

        // 2. 답글을 삭제한다.
        } else if("REREPLY".equals(boardReply.getKind_of_reply())) {
            rsltVal = mapper.deleteBoardReReply(boardReply);
        }

        return rsltVal;
    }

    // 공통 댓글 , 답글   INSERT, UPDATE
    public int insertBoardReply_reReply(BoardReply boardReply) throws Exception {
        int rsltVal = 0;
        BoardReReply boardReReply = new BoardReReply();

        // 1. 댓글을 저장한다.
        if("REPLY".equals(boardReply.getKind_of_reply())) {

            // 1-1. 댓글 UPDATE
            if("Y".equals(boardReply.getUpdate_yn()) ) {
                rsltVal = updateBoardReply(boardReply);
            // 2-1. 댓글 INSERT
            } else {
                rsltVal = insertBoardReply(boardReply);
            }
        

        // 2. 답글을 저장한다.
        } else if("REREPLY".equals(boardReply.getKind_of_reply())) {
            boardReReply.setBoard_div_cd(boardReply.getBoard_div_cd());
            boardReReply.setBoard_no(boardReply.getBoard_no());
            boardReReply.setReply_no(boardReply.getReply_no());
            boardReReply.setRe_reply_no(boardReply.getRe_reply_no());
            boardReReply.setUser_id(boardReply.getUser_id());
            boardReReply.setRe_reply_cntn(boardReply.getReply_cntn());

            // 2-1. 답글 UPDATE
            if("Y".equals(boardReply.getUpdate_yn()) ) {
                rsltVal = updateBoardReReply(boardReReply);
            // 2-2. 답글 INSERT
            } else {
                boardReReply.setRe_reply_no("");
                rsltVal = insertBoardReReply(boardReReply);
            }
        }

        return rsltVal;
    }
    
    
    /**
     * 공통 댓글 삭제
     */
    public int deleteBoardReply(BoardReply boardReply) throws Exception {
        return mapper.deleteBoardReply(boardReply);
    }


    /**
     * 공통 답글 삭제
     */
    public int deleteBoardReReply(BoardReply boardReply) throws Exception {
        return mapper.deleteBoardReReply(boardReply);
    }


    /**
     * 공통 게시글 추가
     */
    public int insertBoard(Board board) throws Exception {
        return mapper.insertBoard(board);
    }

    /**
     * 공통 게시글 삭제
     */
    public int updateBoardDeleteY(Board board) throws Exception {
        return mapper.updateBoardDeleteY(board);
    }

    /**
     * 댓글 추가
     * @param boardReply
     * @return
     * @throws Exception
     */
    public int insertBoardReply(BoardReply boardReply) throws Exception{
        return mapper.insertBoardReply(boardReply);
    }


    /**
     * 댓글 수정
     * @param boardReply
     * @return
     * @throws Exception
     */
    public int updateBoardReply(BoardReply boardReply) throws Exception{
        return mapper.updateBoardReply(boardReply);
    }


    /**
     * 답글 추가
     * @param boardReReply
     * @return
     * @throws Exception
     */
    public int insertBoardReReply(BoardReReply boardReReply) throws Exception{
        return mapper.insertBoardReReply(boardReReply);
    }

    /**
     * 답글 수정
     * @param boardReReply
     * @return
     * @throws Exception
     */
    public int updateBoardReReply(BoardReReply boardReReply) throws Exception{
        return mapper.updateBoardReReply(boardReReply);
    }


    /**
     * emali 중복여주 체크
     * 
     * Y : 중복 - 사용불가능 N : 미중복 - 사용가능
     */
    public String emailDubYn(User user) throws Exception {
        return mapper.emailDubYn(user);
    }

    /**
     * 회원가입 - (회원정보 insert)
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public int joinUser(User user) throws Exception {
        return mapper.joinUser(user);
    }

    /**
     * 회원정보 조회
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public User getUser(User user) throws Exception {
        return mapper.getUser(user);
    }

    /**
     * 이메일 인증 api에 사용 할 key 생성
     * 
     * @param user
     * @return String
     * @throws Exception
     */
    public String getAuthKey(User user) throws Exception {
        if (StringUtils.isEmpty(user.getUser_nic_nm()) || StringUtils.isEmpty(user.getEmail_addr()))
            return null;

        String authPlainText = user.getUser_nic_nm() + user.getEmail_addr();
        return pwdEncoder.encode(authPlainText);
    }

    /**
     * 가입 한 회원에게 인증주소를 포함한 이메일을 발송한다.
     * 
     * @param user
     * @throws Exception
     */
    public void sendAuthApiUrl(User user) throws Exception {
        // logger.info("FROM_ADDRESS : " + FROM_ADDRESS);
        // logger.info("getEmail_addr : " + user.getEmail_addr());

        String subject = "심곡천교회 회원가입 인증 이메일";
        StringBuffer content = new StringBuffer();
        content.append("<div class='mail__body' style='display: flex; align-items: center;flex-direction: column;'>")
                .append("<h1>심곡천 교회에 오신걸 환영합니다.</h1>").append("<div class='content'>")
                .append("<p style='color: red;'>아래 버튼을 누르시면 회원가입 인증을 완료 할 수 있습니다.</p>").append("</div>")
                .append("<a style='opacity: 0.7;padding:5px 5px;border:1px solid darkgray;background-color:darkgray;color: black;font-size: 15px;text-decoration:none;' href='http://www.simgokcheon.org/callMailAuthApi?auth_key="
                        + user.getAuth_key() + "&user_id=" + user.getUser_id() + "'>이메일 인증</a>")
                .append("</div>");

        MailHandler mailHandler = new MailHandler(mailSender);
        mailHandler.setTo(user.getEmail_addr());
        mailHandler.setFrom(FROM_ADDRESS);
        mailHandler.setSubject(subject);
        mailHandler.setText(content.toString(), true);
        // mailHandler.setAttach("새로운파일명", "파일경로+파일명");
        // mailHandler.setInline("새로운이미지명", "파일경로+이미지명");
        mailHandler.send();

        // 간단한 메세지 만 보낼 때 사용.
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(user.getEmail_addr());
        // message.setFrom(FROM_ADDRESS);
        // message.setSubject(subject);
        // message.setText(content.toString());
        // mailSender.send(message);
    }

    /**
     * 회원 이메일로 임시 비밀번호를 전송한다.
     * 
     * @param user
     * @throws Exception
     */
    public void sendTmpPw(User user, String tmpPw) throws Exception {
        // logger.info("FROM_ADDRESS : " + FROM_ADDRESS);
        // logger.info("getEmail_addr : " + user.getEmail_addr());

        String subject = "심곡천교회 임시 페스워드 발급";
        StringBuffer content = new StringBuffer();
        content.append("<div class='mail__body' style='display: flex; align-items: center;flex-direction: column;'>")
                .append("<h1> 임시 비밀번호 : "+ tmpPw +" </h1>").append("<div class='content'>")
                .append("<p style='color: red;'>임시 비밀번호는 회원관리 화면에서 변경 가능합니다.</p>").append("</div>")
                .append("</div>");

        MailHandler mailHandler = new MailHandler(mailSender);
        mailHandler.setTo(user.getEmail_addr());
        mailHandler.setFrom(FROM_ADDRESS);
        mailHandler.setSubject(subject);
        mailHandler.setText(content.toString(), true);
        // mailHandler.setAttach("새로운파일명", "파일경로+파일명");
        // mailHandler.setInline("새로운이미지명", "파일경로+이미지명");
        mailHandler.send();
    }


    /**
     * 사용자 ID 조회
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public String getUserId(User user) throws Exception {
        return mapper.getUserId(user);
    }

    /**
     * 사용자 password 변경
     * @param user
     * @return
     */
    public int changeUserPw(User user)throws Exception {
        return mapper.changeUserPw(user);
    }

    /**
     * 사용자 인증여부 활성화
     * @param user
     * @return
     * @throws Exception
     */
    public int successJoin(User user)throws Exception{
        return mapper.successJoin(user);
    }

    /**
     * 랜덤 패스워드 생성
     * @param len
     * @return
     */
    public String getRamdomPassword(int len) { 
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 
                                      'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        int idx = 0; StringBuffer sb = new StringBuffer(); 
        for (int i = 0; i < len; i++) { 
            idx = (int) (charSet.length * Math.random()); 
            // 36 * 생성된 난수를 Int로 추출 (소숫점제거) 
            logger.info("idx :::: "+idx);
            sb.append(charSet[idx]); 
        } 
        return sb.toString(); 
    }

    /**
     * 좋아요 클릭여부
     * @param vo
     * @return
     */
    public String getLikeYn(BoardLike vo) throws Exception{
        return mapper.getLikeYn(vo);
    }

    /**
     * 게시글의 좋아요 정보를 삭제(DEL_YN = 'Y')
     * @param vo
     * @return
     */
    public int onBoardLike(BoardLike vo) throws Exception{
        return mapper.onBoardLike(vo);
    }
    
    /**
     * 게시글의 좋아요 정보를 생성(INSERT / UPDATE)
     * @param vo
     * @return
     */
    public int offBoardLike(BoardLike vo) throws Exception{
        return mapper.offBoardLike(vo);
    }

    /**
     * 대메뉴 목록
     * @return List<Comcode>
     */
    public List<Comcode> getMenu_lv01(String code) throws Exception{
        return mapper.getMenu_lv01(code);
    }

    /**
     * 중메뉴 목록
     * @param code
     * @return List<Comcode>
     */
    public List<Comcode> getMenu_lv02(String code01, String code02) throws Exception{
        return mapper.getMenu_lv02(code01, code02);
    }



    /**
     * return 할 페이지 명을 만든다. [리턴 시 binding 할 부분의 id를 찾아서 부분 페이지를 파싱한다.
     * @param pageInfo
     * @return
     */
    public String getReturnUrl(CallPageInfo pageInfo) {
        String returnUrl = "";
        String page_name;
        page_name = pageInfo.getPage_name();

        if(page_name.contains("002")) {
            returnUrl = "page/page_002/page_002_01_02 :: #";
        } else if(page_name.contains("005_01")) {
            returnUrl = "page/page_005/page_005_01_02 :: #";
        } else if(page_name.contains("005_02")) {
            returnUrl = "page/page_005/page_005_02_02 :: #";
        } else if(page_name.contains("005_03")) {
            returnUrl = "page/page_005/page_005_03_02 :: #";
        } else if(page_name.contains("005_04")) {
            returnUrl = "page/page_005/page_005_04_02 :: #";
        } else if(page_name.contains("006_01")) {
            returnUrl = "page/page_006/page_006_01_02 :: #";
        } else if(page_name.contains("006_02")) {
            returnUrl = "page/page_006/page_006_02_02 :: #";
        } else if(page_name.contains("007_01")) {
            returnUrl = "page/page_007/page_007_01_02 :: #";
        } else if(page_name.contains("007_02")) {
            returnUrl = "page/page_007/page_007_02_02 :: #";
        }
        returnUrl += pageInfo.getBind_id();
        return returnUrl;
    }


    private String getIp(HttpServletRequest request) {
 
        String ip = request.getHeader("X-Forwarded-For");
 
        logger.info(">>>> X-FORWARDED-FOR : " + ip);
 
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
            logger.info(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.info(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        
        logger.info(">>>> Result : IP Address : "+ip);
 
        return ip;
 
    }




}
