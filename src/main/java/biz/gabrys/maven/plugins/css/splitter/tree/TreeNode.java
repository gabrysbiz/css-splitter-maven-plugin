package biz.gabrys.maven.plugins.css.splitter.tree;

import java.util.List;

public interface TreeNode<T> {

    List<TreeNode<T>> getChildren();

    boolean hasValue();

    T getValue();
}
