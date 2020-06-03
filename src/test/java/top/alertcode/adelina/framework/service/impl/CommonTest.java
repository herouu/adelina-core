package top.alertcode.adelina.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.base.service.CommonsService;
import top.alertcode.adelina.framework.utils.JsonUtils;

/**
 *
 *
 * @author fuq
 * @date 2020-06-03 15:52
 */
public class CommonTest extends BaseTest {

    @Autowired
    CommonsService commonsService;

    @Test
    public void tes123132() {
        TestUser testUser = commonsService.selectById(TestUser.class, 1L);
        System.out.println(JsonUtils.toJson(testUser));
    }
}
