package kakaopay.inquMgnt.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final String errCode;

    public ApiException(String message, String errCode){
        super(message, null, false, false);
        this.errCode = errCode;
    }
}
