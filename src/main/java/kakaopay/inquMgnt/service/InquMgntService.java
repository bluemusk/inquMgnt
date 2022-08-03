package kakaopay.inquMgnt.service;

import kakaopay.inquMgnt.constant.ErrCode;
import kakaopay.inquMgnt.entity.Clser;
import kakaopay.inquMgnt.entity.Inqu;
import kakaopay.inquMgnt.entity.User;
import kakaopay.inquMgnt.exception.ApiException;
import kakaopay.inquMgnt.repository.ClserRepository;
import kakaopay.inquMgnt.repository.InquRepository;
import kakaopay.inquMgnt.repository.UserRepository;
import kakaopay.inquMgnt.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquMgntService {
    @Autowired
    InquRepository inquRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClserRepository clserRepository;

    public String selectUser(String _userId){
        String rsltMsg;
        List<User> rsltUser = userRepository.findByUserId(_userId);

        if (rsltUser.isEmpty()){
            rsltMsg = "중복된 ID가 없습니다. 문의 내역 입력 가능";
            insertUser(_userId);
        }else{
            throw new ApiException(ErrCode.E0101.getErrMsg(), ErrCode.E0101.getCode());
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltMsg);

        return resJson;
    }

    public String selectClser(String _clserId){
        String rsltMsg;
        List<Clser> rsltClser = clserRepository.findByClserId(_clserId);

        if (rsltClser.isEmpty()){
            rsltMsg = "중복된 ID가 없습니다. ID 사용 가능";
            insertUser(_clserId);
        }else{
            throw new ApiException(ErrCode.E0101.getErrMsg(), ErrCode.E0101.getCode());
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltMsg);

        return resJson;
    }

    public String selectInquOne(Long _inquId){
        List<Inqu> rsltInqu = inquRepository.findByInquId(_inquId);

        if (rsltInqu.isEmpty()){
            throw new ApiException(ErrCode.E0104.getErrMsg(), ErrCode.E0104.getCode());
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltInqu);

        return resJson;
    }

    public String selectInqu(String _userId){
        List<Inqu> rsltInqu = inquRepository.findByUserId(_userId);

        if (rsltInqu.isEmpty()){
            throw new ApiException(ErrCode.E0104.getErrMsg(), ErrCode.E0104.getCode());
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltInqu);

        return resJson;
    }

    public String selectInquClser(String _clserId){
        List<Inqu> rsltInqu = inquRepository.findByClserIdAndCmplYn(_clserId, null);

        if (rsltInqu.isEmpty()){
            throw new ApiException(ErrCode.E0104.getErrMsg(), ErrCode.E0104.getCode());
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltInqu);

        return resJson;
    }

    public String selectClserLogin(String _clserId, String _pwd){
        String rsltMsg;
        Long listCnt = clserRepository.listCnt(_clserId, _pwd);
        if (listCnt < 1L){
            throw new ApiException(ErrCode.E0102.getErrMsg(), ErrCode.E0102.getCode());
        }else{
            rsltMsg = "로그인 성공";
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltMsg);

        return resJson;
    }

    public String selectInquYet(){
        List<Inqu> rsltList = new ArrayList<>();
        List<Inqu> rsltInquYet = inquRepository.findAll();

        if (rsltInquYet.isEmpty()){
            throw new ApiException(ErrCode.E0103.getErrMsg(), ErrCode.E0103.getCode());
        }else{
            for (int i=0;i<rsltInquYet.size();i++){
                if (rsltInquYet.get(i).getClserId() == null){
                    rsltList.add(rsltInquYet.get(i));
                }
            }
        }
        GsonUtils gson = new GsonUtils();
        String resJson = gson.toJson(rsltList);

        return resJson;
    }

    @Transactional
    public void insertUser(String _userId){
        try{
            User user = new User();
            user.setUserId(_userId);
            userRepository.save(user);
        }catch(ApiException e){
            throw new ApiException(ErrCode.E0000.getErrMsg(), ErrCode.E0000.getCode());
        }
    }

    @Transactional
    public String updateInqu(Long _inquId, String _clserId){
        String rsltMsg = "";
        try{
            inquRepository.updateClsId(_clserId, LocalDateTime.now(), _inquId);
            rsltMsg = "선택하신 문의 내역의 답변자로 지정되었습니다.";
        }catch(ApiException e){
            throw new ApiException(ErrCode.E0000.getErrMsg(), ErrCode.E0000.getCode());
        }
        return rsltMsg;
    }

    @Transactional
    public String insertInqu(String _userId, String _inquTitl, String _inquCn) {
        String rsltMsg = "";
        try {
            Inqu inqu = new Inqu();
            inqu.setUserId(_userId);
            inqu.setInquTitl(_inquTitl);
            inqu.setInquCn(_inquCn);
            inquRepository.save(inqu);
            rsltMsg = "문의 내역이 저장되었습니다.";
        }catch(ApiException e){
            throw new ApiException(ErrCode.E0000.getErrMsg(), ErrCode.E0000.getCode());
        }

        return rsltMsg;
    }

    @Transactional
    public String insertClser(String _clserId, String _clserNm, String _pwd) {
        String rsltMsg = "";
        Long listCnt = clserRepository.listCnt(_clserId, _pwd);
        if (listCnt < 1L){
            Clser clser = new Clser();
            clser.setClserId(_clserId);
            clser.setClserNm(_clserNm);
            clser.setPwd(_pwd);
            rsltMsg = "ID 생성 되었습니다.";
            clserRepository.save(clser);
        }else{
            throw new ApiException(ErrCode.E0101.getErrMsg(), ErrCode.E0101.getCode());
        }
        return rsltMsg;
    }

    @Transactional
    public String insertRpl(Long _inquId, String _rplCn) {
        String rsltMsg = "";
        try {
            inquRepository.updateRplCn(_rplCn, "Y", _inquId);
            rsltMsg = "답변이 저장 되었습니다.";
        }catch(ApiException e){
            throw new ApiException(ErrCode.E0000.getErrMsg(), ErrCode.E0000.getCode());
        }

        return rsltMsg;
    }

}
