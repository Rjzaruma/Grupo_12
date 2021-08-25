package tree;

public class MPTree<T> {

    private MPNode<T> root;

    public MPTree() {
        this.root = new MPNode<>();
    }

    public MPTree(T content) {
        this.root = new MPNode<>(content);
    }

    public MPNode<T> getRoot() {
        return root;
    }

    public void setRoot(MPNode<T> root) {
        this.root = root;
    }
    
    public MPTree<T> getHijo(int i) {
        return root.getHijo(i);
    }
    
    public MPTree<T> getLastHijo() {
        return root.getHijo(root.hijosSize()-1);
    }    

    public void addHijo(MPTree<T> nuevoHijo) {
        root.addHijo(nuevoHijo);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean isLeaf() {
        return this.root.isLeaf();
    }

//    public MPNode<T> recursiveSearch(T content) {
//        if (this.isEmpty()) {
//            return null;
//        } else {
//            if (this.root.getContent().equals(content)) {
//                return this.root;
//            } else {
//                MPNode<T> tmp = null;
//                if (this.root.getLeft() != null) {
//                    tmp = this.root.getLeft().recursiveSearch(content);
//                }
//                if (tmp == null) {
//                    if (this.root.getRight() != null) {
//                        tmp = this.root.getRight().recursiveSearch(content);
//                    }
//                }
//                return tmp;
//            }
//        }
//    }
}
