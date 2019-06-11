package top.alertcode.adelina.framework.commons.enums;

import org.testng.annotations.Test;

public class OperateEnumTest {

    @Test
    public void testGetOperateEnum() {
        OperateEnum and = OperateEnum.getOperateEnum("and");
        OperateEnum in = OperateEnum.getOperateEnum("ss");
    }
}