package xmu.ghct.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan(basePackages ="xmu.ghct.crm.mapper")
public class CrmApplication {


    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }
}
