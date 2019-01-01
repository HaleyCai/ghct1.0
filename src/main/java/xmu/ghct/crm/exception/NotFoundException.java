package xmu.ghct.crm.exception;

/**
 * @author TYJ
 * 未找到目标资源异常
 */
public class NotFoundException extends Exception{


        private String errorMsg;

        public NotFoundException(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

}
