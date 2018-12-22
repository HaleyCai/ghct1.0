package xmu.ghct.crm.exception;

import java.io.Serializable;

public class RoundNotFindException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    public RoundNotFindException() {
        super();
    }

    public RoundNotFindException(String message) {
        super(message);
    }

    public RoundNotFindException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RoundNotFindException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RoundNotFindException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public RoundNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public RoundNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoundNotFindException(Throwable cause) {
        super(cause);
    }

    protected RoundNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
