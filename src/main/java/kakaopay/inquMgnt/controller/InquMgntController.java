package kakaopay.inquMgnt.controller;

import kakaopay.inquMgnt.service.InquMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/kakaopay")
public class InquMgntController {
    @Autowired
    private InquMgntService inquMgntService;

    /* 고객ID존재여부 파악
     @param userId 고객ID
     */
    @ResponseBody
    @GetMapping(path = "/user")
    public ResponseEntity GetUserId(@RequestHeader String user_id){
        try {
            String userYn = inquMgntService.selectUser(user_id);
            return new ResponseEntity<>(userYn, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 문의내역 저장
     @param userId 고객ID
     @param inquTitl 문의제목
     @param inquCn 문의내용
     */
    @ResponseBody
    @PostMapping(path = "/inqu")
    public ResponseEntity SetInqu(@RequestBody HashMap<String, Object> param){
        try {
            System.out.println("param : " + param);

            String _userId = (String)param.get("user_id");
            String _inquTitl = (String)param.get("inqu_titl");
            String _inquCn = (String)param.get("inqu_cn");

            String inquSet = inquMgntService.insertInqu(_userId, _inquTitl, _inquCn);
            return new ResponseEntity<>(inquSet, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 사용자로 문의내역 조회
     @param userId 고객ID
     */
    @ResponseBody
    @GetMapping(path = "/inqu")
    public ResponseEntity GetInqu(@RequestHeader String user_id){
        try {
                String inquList = inquMgntService.selectInqu(user_id);
                return new ResponseEntity<>(inquList, HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
            } catch (InternalError | Exception e){
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /* 문의id로 문의내역 조회
     @param inquId 문의ID
     */
    @ResponseBody
    @GetMapping(path = "/inquOne")
    public ResponseEntity GetInquOne(@RequestHeader Long inqu_id){
        try {
            String inquList = inquMgntService.selectInquOne(inqu_id);
            return new ResponseEntity<>(inquList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 상담원ID중복체크
     @param clser_id 상담원ID
     */
    @ResponseBody
    @GetMapping(path = "/clser")
    public ResponseEntity GetClserId(@RequestHeader String clser_id){
        try {
            String clserYn = inquMgntService.selectClser(clser_id);
            return new ResponseEntity<>(clserYn, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 상담원 정보 저장
    @param clser_id 상담원ID
    @param clser_nm 상담원명
    @param pwd 패스워드
     */
    @ResponseBody
    @PostMapping(path = "/clser")
    public ResponseEntity SetClser(@RequestHeader String clser_id,
                                   @RequestHeader String clser_nm,
                                   @RequestHeader String pwd){
        try{
//            System.out.println("param : " + param);

//            String _clserId = (String)param.get("clser_id");
//            String _clserNm = (String)param.get("clser_nm");
//            String _pwd = (String)param.get("pwd");

            String rslt = inquMgntService.insertClser(clser_id, clser_nm, pwd);
            return new ResponseEntity<>(rslt, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 로그인
     @param clser_id 상담원ID
     @param pwd 패스워드
     */
    @ResponseBody
    @GetMapping(path = "/clserLogin")
    public ResponseEntity GetClserLogin(@RequestHeader String clser_id,
                                        @RequestHeader String pwd){
        try {
            String userYn = inquMgntService.selectClserLogin(clser_id, pwd);
            return new ResponseEntity<>(userYn, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 미답변 조회
     */
    @ResponseBody
    @GetMapping(path = "/inquYn")
    public ResponseEntity GetInquYn(){
        try {
            String inquYn = inquMgntService.selectInquYet();
            return new ResponseEntity<>(inquYn, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 문의 답변자 지정
    @param clser_id 상담원id
     */
    @ResponseBody
    @PutMapping(path = "/inquClser")
    public ResponseEntity PutInqu(@RequestHeader Long inqu_id,
                                  @RequestHeader String clser_id){
        try {
            String inquClser = inquMgntService.updateInqu(inqu_id, clser_id);
            return new ResponseEntity<>(inquClser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 문의 답변자가 답변해야할 내용 조회
   @param clser_id 상담원id
    */
    @ResponseBody
    @GetMapping(path = "/inquClser")
    public ResponseEntity GetInquClser(@RequestHeader String clser_id){
        try {
            String inquClser = inquMgntService.selectInquClser(clser_id);
            return new ResponseEntity<>(inquClser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /* 문의 답변 저장
    @param inquId 상담원id
    @param rplCn  답변내용
     */
    @ResponseBody
    @PostMapping(path = "/rpl")
    public ResponseEntity SetRpl(@RequestBody HashMap<String, Object> param){
        try{
            System.out.println("param : " + param);

            Long _inquId = Long.valueOf(param.get("inqu_id").toString());
            String _rplCn = (String)param.get("rpl_cn");

            String rslt = inquMgntService.insertRpl(_inquId, _rplCn);
            return new ResponseEntity<>(rslt, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (InternalError | Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
