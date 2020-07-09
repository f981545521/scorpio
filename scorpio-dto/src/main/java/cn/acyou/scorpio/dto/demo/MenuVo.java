package cn.acyou.scorpio.dto.demo;

import cn.acyou.framework.model.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/8]
 **/
@Data
public class MenuVo implements TreeNode<Long> {
    private static final long serialVersionUID = 5549531525495087966L;
    private Long id;
    private Long parentId;
    private String name;
    private List<MenuVo> childNodes;

    public MenuVo(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public Long parentId() {
        return parentId;
    }

    @Override
    public boolean root() {
        return parentId == 0;
    }

    @Override
    public void children(List<? extends TreeNode<Long>> children) {
        this.childNodes = (List<MenuVo>) children;
    }

    @Override
    public List<? extends TreeNode<Long>> children() {
        return childNodes;
    }
}
