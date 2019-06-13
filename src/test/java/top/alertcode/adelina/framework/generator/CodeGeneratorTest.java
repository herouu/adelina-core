package top.alertcode.adelina.framework.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;

/**
 * <p>CodeGeneratorTest class.</p>
 *
 * @author alertcode
 * @version $Id: $Id
 * @since 1.0.0
 */
public class CodeGeneratorTest extends BaseTest {
    @Autowired
    private CodeGenerator codeGenerator;

    /**
     * <p>testExec.</p>
     */
    @Test
    public void testExec() {
        codeGenerator.exec("alert", "audit", "repayment_audit", "top.alertcode.adelina.framework", "test");
    }
}
