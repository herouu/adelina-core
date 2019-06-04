package top.alertcode.adelina.framework;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
@SpringBootApplication
@EnableSwagger2Doc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
