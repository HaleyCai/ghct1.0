package xmu.ghct.crm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getUserById()
    {
        User user=userDao.getUserByAccount("24320162202804",false);
        if(user==null)
            System.out.println("空");
        else
            System.out.println(user.toString());
    }

    @Test
    public void setPassword()
    {
        User user=new User();
        user.setAccount("24320162202333");
        user.setPassword("2333333");
        user.setEmail("ioooooooooo");
        userDao.setPasswordByAccount(user,true);
    }
}
