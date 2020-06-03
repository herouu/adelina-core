package top.alertcode.adelina.framework.service.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.alertcode.adelina.framework.base.annotation.DBTableName;

/**
 *
 *
 * @author fuq
 * @date 2020-06-03 15:54
 */
@DBTableName("user_test")
@Data
public class TestUser {
    private Long id;
    private String name;
}
