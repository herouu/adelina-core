package top.alertcode.adelina.framework.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;

public class CodeGeneratorTest extends BaseTest {
    @Autowired
    private CodeGenerator codeGenerator;

    @Test
    public void testExec() {
        codeGenerator.exec("alert", "user", "sys_user", "top.alertcode.adelina.framework", "test");
    }
}