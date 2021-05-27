package statistics;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExecutionTime {
    private static ExecutionTime INSTANCE;
    private DefaultMutableTreeNode root;


    private ExecutionTime() {
    }

    public static ExecutionTime getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ExecutionTime();
        return INSTANCE;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void reset() {
        if (root != null)
            root.removeAllChildren();
    }

    public void setRoot(DefaultMutableTreeNode root) {
        reset();
        this.root = root;
    }
}
