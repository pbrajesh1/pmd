/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * @author Clément Fournier
 */
public final class DummyTreeUtil {


    private DummyTreeUtil() {

    }


    /** Creates a dummy node with the given children. */
    public static DummyNode node(DummyNode... children) {
        DummyNode node = new DummyNode(0) {
            @Override
            public String toString() {
                return getImage();
            }
        };
        node.children = children;
        for (int i = 0; i < children.length; i++) {
            children[i].jjtSetParent(node);
            children[i].jjtSetChildIndex(i);
        }
        return node;
    }


    public static DummyNode followPath(DummyNode root, String path) {
        List<Integer> pathIndices = Arrays.stream(path.split("")).map(Integer::valueOf).collect(Collectors.toList());

        Node current = root;
        for (int i : pathIndices) {
            current = current.jjtGetChild(i);
        }

        return (DummyNode) current;
    }


    /**
     * Must wrap the actual {@link #node(DummyNode...)} usages to assign each node the
     * image of its path from the root (in indices). E.g.
     *
     * <pre>
     * node(         ""
     *   node(       "0"
     *     node(),   "00"
     *     node(     "01"
     *       node()  "010
     *     )
     *   ),
     *   node()      "1"
     * )
     * </pre>
     */
    public static DummyNode tree(Supplier<DummyNode> supplier) {
        DummyNode dummyNode = supplier.get();
        assignPathImage(dummyNode, "");
        return dummyNode;
    }


    private static void assignPathImage(Node node, String curPath) {
        node.setImage(curPath);

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            assignPathImage(node.jjtGetChild(i), curPath + i);
        }
    }

    /** List of the images of the stream. */
    public static List<String> pathsOf(NodeStream<?> stream) {
        return stream.toList(Node::getImage);
    }
}