package top.alertcode.adelina.framework.commons.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 树形节点
 * </p>
 *
 * @author Caratacus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreeNode {
    protected Integer id;

    protected Integer parentId;

    protected List<TreeNode> childrens = new ArrayList<>();
}
