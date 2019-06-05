package top.alertcode.adelina.framework;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void test() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("7nZPf)pMwh");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("simba");
        String password = textEncryptor.encrypt("BeGYVXnw7&Kj@A!j");
        String url = textEncryptor.encrypt("jdbc:mysql://192.168.0.221:3306/fero_repay_bak?characterEncoding=utf8" +
                "&serverTimezone=Asia/Shanghai&useSSL=false");
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("url:" + url);

    }
}
