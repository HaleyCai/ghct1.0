package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.UserDao;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 正常登录，判断用户名密码是否匹配
     * @param input
     * @param isTeacher
     * @return
     */
    public Map<String,Object> login(User input, boolean isTeacher){
        Map<String,Object> resultMap=new HashMap<>();
        if(input.getAccount()==null||input.getPassword()==null){
            resultMap.put("message","用户名或密码不能为空");
        }
        else{
            User resultUser=userDao.getUserByAccount(input.getAccount(),isTeacher);
            if(resultUser.getPassword().equals(input.getPassword())){
                resultMap.put("message","登录成功");
            }
            else
                resultMap.put("message","用户名或密码错误");
        }
        resultMap.put("kkk","调用service");
        return resultMap;
    }

    /**
     * 激活账号，设置对应密码，向邮箱发送信息
     * @param input
     * @param isTeacher
     */
    public void active(User input, boolean isTeacher){}

    /**
     * 根据用户ID和身份获取个人信息
     * @param id
     * @param isTeacher
     */
    public void getInformation(BigInteger id,boolean isTeacher)
    {}


}