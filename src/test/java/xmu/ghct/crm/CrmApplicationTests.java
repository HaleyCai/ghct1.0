package xmu.ghct.crm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CrmApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrmApplicationTests {

    @Test
    public void contextLoads() {
    }

}
