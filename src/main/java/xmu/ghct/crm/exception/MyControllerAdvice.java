package xmu.ghct.crm.exception;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TYJ
 */
@RestControllerAdvice
public class MyControllerAdvice implements ResponseBodyAdvice {

    /**
     * 全局异常捕捉处理
     * @param e
     * @return Map
     */
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception e, HttpServletResponse httpServletResponse) {
        e.printStackTrace();
        httpServletResponse.setStatus(500);
        return this.getResult(500,"System error!");
    }

    /**
     * SQL异常捕捉处理
     * @param e
     * @return Map
     */
    @ExceptionHandler(value = SQLException.class)
    public Map queryErrorHandler(SQLException e,HttpServletResponse httpServletResponse) {
        e.printStackTrace();
        httpServletResponse.setStatus(500);
        return this.getResult(500,"error on querying database!");
    }

    /**
     * 参数错误异常捕捉处理
     * @param e
     * @return Map
     */
    @ExceptionHandler(value = ParamErrorException.class)
    public Map paramErrorHandler(ParamErrorException e, HttpServletResponse httpServletResponse) {
        e.printStackTrace();
        httpServletResponse.setStatus(400);
        return this.getResult(400,e.getErrorMsg());
    }

    /**
     * 未找到资源异常捕捉处理
     * @param e
     * @return Map
     */
    @ExceptionHandler(value = NotFoundException.class)
    public Map paramErrorHandler(NotFoundException e,HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(404);
        e.printStackTrace();
        return this.getResult(404,e.getErrorMsg());
    }

    /**
     * 获取错误信息
     * @param code
     * @param message
     * @return Map
     */
    private Map getResult(int code,String message){
        Map map = new HashMap(1);
        map.put("code", code);
        map.put("message", message);
        return map;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //通过 ServerHttpRequest的实现类ServletServerHttpRequest 获得HttpServletRequest
        ServletServerHttpResponse sshr=(ServletServerHttpResponse) serverHttpRequest;
        //此处获取到request 是为了取到在拦截器里面设置的一个对象 是我项目需要,可以忽略
        HttpServletResponse response=sshr.getServletResponse();
        HttpServletResponse httpServletResponse=sshr.getServletResponse();
        if(httpServletResponse.getStatus()==403){
            return null;
        }
        return null;
    }
}
