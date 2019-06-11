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
    public void configEncryptor() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("7nZPf)pMwh");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("123456");
        String host = textEncryptor.encrypt("localhost");
        String url = textEncryptor.encrypt("jdbc:mysql://localhost:3306/adelina?characterEncoding=utf8" +
                "&serverTimezone=Asia/Shanghai&useSSL=false");
        System.out.println("hosts:" + host);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("url:" + url);

    }
}
