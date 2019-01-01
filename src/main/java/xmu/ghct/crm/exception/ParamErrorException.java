package xmu.ghct.crm.exception;

import java.io.Serializable;

/**
 * @author hzm
 * 参数错误异常
 */
public class ParamErrorException extends Exception implements Serializable {
        private String errorMsg;

        public ParamErrorException(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
}
