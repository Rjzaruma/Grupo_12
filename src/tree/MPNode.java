/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Docente
 */
public class MPNode<T> {
    
    private T content;
    private List<MPTree<T>> hijos;
    
    public MPNode() {
        this(null, new ArrayList<MPTree<T>>());
    }

    public MPNode(T content) {
        this(content, new ArrayList<MPTree<T>>());
    }

    public MPNode(T content, List<MPTree<T>> hijos) {
        this.content = content;
        this.hijos = hijos;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public MPTree<T> getHijo(int i) {
        return hijos.get(i);
    }
    
    public MPTree<T> getLastHijo() {
        return hijos.get(hijos.size()-1);
    }
    
    public void addHijo(MPTree<T> nuevoHijo) {
        this.hijos.add(nuevoHijo);
    }
    
    public int hijosSize(){
        return hijos.size();
    }
    
    public boolean isLeaf(){
        return hijos.isEmpty();
    }
}
