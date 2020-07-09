package cn.acyou.framework.utils;

import cn.acyou.framework.model.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/8]
 **/
public class TreeUtil {

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     *
     * @param nodes 树形节点列表
     * @param <T>   节点类型
     * @return 树形结构列表
     */
    public static <T extends TreeNode<?>> List<T> generateTrees(List<T> nodes) {
        List<T> rootNodes = new ArrayList<>();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (node.root()) {
                rootNodes.add(node);
                //从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        rootNodes.forEach(r -> setChildren(r, nodes));
        return rootNodes;
    }

    /**
     * 从所有节点列表中查找并设置parent的所有子节点
     *
     * @param parent 父节点
     * @param nodes  所有树节点列表
     */
    private static <T extends TreeNode> void setChildren(T parent, List<T> nodes) {
        List<T> children = new ArrayList<>();
        Object parentId = parent.id();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (Objects.equals(node.parentId(), parentId)) {
                children.add(node);
                //从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        // 如果子节点不为空，则继续递归设置孩子的孩子
        if (CollectionUtils.isNotEmpty(children)) {
            parent.children(children);
            children.forEach(m -> setChildren(m, nodes));
        }

    }

}
