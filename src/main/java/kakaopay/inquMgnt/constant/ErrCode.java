package kakaopay.inquMgnt.constant;

public enum ErrCode {
    /**
     * 에러코드 정의
     */
    // HttpStatus 500
    E0000("000", "예상치 못한 오류가 발생하였습니다."),

    // HttpStatus 400
    E0101("101", "중복된 ID가 존재합니다."),
    E0102("102", "로그인 정보를 잘못 입력하였습니다."),
    E0103("103", "데이터가 존재하지 않습니다."),
    E0104("104", "해당ID로 등록된 문의가 없습니다.");


    public final String code;
    public final String errMsg;

    ErrCode(String code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }

    public String getCode(){
        return code;
    }

    public String getErrMsg(){
        return  errMsg;
    }
}
