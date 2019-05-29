package top.alertcode.adelina.framework.utils;


import top.alertcode.adelina.framework.commons.model.TreeNode;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 * Tree工具�?
 * </p>
 *
 * @author Caratacus
 * @version $Id: $Id
 */
public abstract class TreeUtils {

    /**
     * 递归查找子节�?
     *
     * @param treeNodes 子节�?
     * @param treeNodes 子节�?
     * @return T extends TreeNode
     * @param treeNodes 子节�?
     * @param treeNodes 子节�?
     * @param treeNodes 子节�?
     * @param treeNodes 子节�?
     * @param treeNode a T object.
     * @param <T> a T object.
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        treeNodes.stream().filter(e -> Objects.equals(treeNode.getId(), e.getParentId())).forEach(e -> treeNode.getChildrens().add(findChildren(e, treeNodes)));
        return treeNode;
    }
}
