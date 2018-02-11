package com.tamil.thamizhpesi.search.node;


import java.util.Map;
import java.util.TreeMap;

import com.tamil.thamizhpesi.search.base.TrieNode;

public class TreeMapNode<V> extends AbstractMapNode<V> {

    public TreeMapNode(String character) {
        super(character);
    }

    @Override
    protected Map<String, TrieNode<V>> onCreateMap() {
        return new TreeMap<>();
    }

    @Override
    protected AbstractMapNode<V> onCreateNewNode(String character) {
        return new TreeMapNode<>(character);
    }
}
