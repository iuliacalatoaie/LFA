import java.util.LinkedList;

public class Node {
    private String parent;
    private LinkedList<String> children = new LinkedList<>();

    public Node(final String parent, final String child) {
        this.parent = parent;
        children.add(child);
    }

    public String getParent() {
        return parent;
    }

    public void addChild(final String child) {
        this.children.add(child);
    }

    public LinkedList<String> getChildren() {
        return children;
    }
}