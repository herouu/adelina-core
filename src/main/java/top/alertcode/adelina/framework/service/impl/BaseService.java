package top.alertcode.adelina.framework.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import top.alertcode.adelina.framework.commons.constant.PageCons;
import top.alertcode.adelina.framework.mapper.BaseMapper;
import top.alertcode.adelina.framework.service.IBaseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 基础Service实现 继承于Mybatis-plus
 * </p>
 *
 * @author Caratacus
 */
@Transactional(readOnly = true)
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl implements IBaseService<T> {
    @Resource
    protected HttpServletRequest request;

    protected <T> Page<T> getPage() {
        int index = 1;
        // 页数
        Integer cursor = ObjectUtils.defaultIfNull(TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE)), index);
        // 分页大小
        Integer limit = ObjectUtils.defaultIfNull(TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS)), PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit);
        return page;
    }
}
