package com.church.simgokchyun.common.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.church.simgokchyun.common.mail.MailHandler;
import com.church.simgokchyun.common.paging.Pagination;
import com.church.simgokchyun.common.vo.Board;
import com.church.simgokchyun.common.vo.Comcode;
import com.church.simgokchyun.common.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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


    /**  업로드 파일저장 
     *    
     * @param 1.file/2.file_div_cd
     *      1. file          -  업로드 할 파일객체
     *      2. file_div_cd   -  파일의 종류 구분코드 ( 01:이미지,  02:동영상 )
     * 
     *      프로세스 
     *      1. 공통에서 parameter  1-이미지 오리지널파일명, 2-파일바이트 , 3-구분코드[이미지/동영상] 를 받는다.
     *      2. 오리지널파일명을 uuid+일자 를 사용해서 유니크한 파일명을 생성한다.
     *      3. FILE_INFO 테이블에 키를 구하고 파일 정보를 INSERT 한다.
     *      4. return 파일아이디
     * @throws Exception
     * @return String - 파일ID
     */
    public String fileSave(MultipartFile file, String file_div_cd)throws Exception {
        
        Map<String, Object> paramMap = new HashMap<String,Object>();
        String origin_file_nm = "";
        String real_file_nm = "";
        String uploadPath = "";

        // if("01".equals(file_div_cd) ) {
        //     save_folder = "\\imgs";
        // } else {
        //     save_folder = "\\video";
        // }

        // uploadPath = defaultUploadPath + File.separator + save_folder;
        uploadPath = defaultUploadPath;

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

        logger.info("fileUploadPath : " +  uploadPath);

        // 2. 파일정보 DB insert
        mapper.fileSave(paramMap);

        // 3. 업로드 경로에 파일생성 
        file.transferTo(new File(uploadPath + real_file_nm));
        
        // 4. 파일 id를 return 한다.
        return mapper.getFileId(paramMap);
    }


    /**
     * 중복 방지를 위한 파일명 변경 
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public String transFileName(String fileName)throws Exception {
        if(fileName == null  || "".equals(fileName))
            return null;
        
        String extension = fileName.substring(fileName.lastIndexOf("."),fileName.length()); //확장자
        return this.getDate("yyyyMMdd")+"_" + UUID.randomUUID().toString() + extension;
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

    /**
     * emali 중복여주 체크  
     *      
     *      Y : 중복   - 사용불가능
     *      N : 미중복 - 사용가능 
     */
    public String emailDubYn(User user) throws Exception {
        return mapper.emailDubYn(user);
    }


    /**
     * 회원가입 - (회원정보 insert)
     * @param user
     * @return
     * @throws Exception
     */
    public int joinUser(User user) throws Exception{
        return mapper.joinUser(user);
    }


    /**
     * 회원정보 조회
     * @param user
     * @return
     * @throws Exception
     */
    public User getUser(User user) throws Exception{
        return mapper.getUser(user);
    }


    /**
     * 이메일 인증 api에 사용 할 key 생성
     * @param user
     * @return String
     * @throws Exception
     */
    public String getAuthKey(User user) throws Exception {
        if(StringUtils.isEmpty(user.getUser_nic_nm()) || StringUtils.isEmpty(user.getEmail_addr())) return null;
        
        String authPlainText = user.getUser_nic_nm() + user.getEmail_addr();
        return pwdEncoder.encode(authPlainText);
    }


    /**
     * 가입 한 회원에게 인증주소를 포함한 이메일을 발송한다.
     * @param user
     * @throws Exception
     */
    public void sendAuthApiUrl(User user)throws Exception {
        // logger.info("FROM_ADDRESS : " + FROM_ADDRESS);
        // logger.info("getEmail_addr : " + user.getEmail_addr());

        String subject = "심곡천교회 회원가입 인증 이메일";
        StringBuffer content = new StringBuffer();
        content.append("<div class='mail__body' style='display: flex; align-items: center;flex-direction: column;'>")
               .append("<h1>심곡천 교회에 오신걸 환영합니다.</h1>")
               .append("<div class='content'>")
               .append("<p style='color: red;'>아래 버튼을 누르시면 회원가입 인증을 완료 할 수 있습니다.</p>")
               .append("</div>")
               .append("<a style='opacity: 0.7;padding:5px 5px;border:1px solid darkgray;background-color:darkgray;color: black;font-size: 15px;text-decoration:none;' href='http://localhost:8080/callMailAuthApi?auth_key="+user.getAuth_key()+"&user_id="+user.getUser_id()+"'>이메일 인증</a>")
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
     * 사용자 ID 조회
     * @param user
     * @return
     * @throws Exception
     */
    public String getUserId(User user)throws Exception {
        return mapper.getUserId(user);
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


}
