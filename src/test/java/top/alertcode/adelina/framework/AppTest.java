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
        String username = textEncryptor.encrypt("adelina");
        String password = textEncryptor.encrypt("adelina");
        String host = textEncryptor.encrypt("adelina.alertcode.top");
        String redisPwd = textEncryptor.encrypt("adelina_!QAZ");
        String url = textEncryptor.encrypt("jdbc:mysql://adelina.alertcode" +
                ".top:3306/adelina_dev?characterEncoding=utf8" +
                "&serverTimezone=Asia/Shanghai&useSSL=false");
        System.out.println("hosts:" + host);
        System.out.println("redisPwd:" + redisPwd);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("url:" + url);

    }
}
