
package com.tamil.thamizhpesi.search.trie;


import com.tamil.thamizhpesi.search.base.TrieNode;
import com.tamil.thamizhpesi.search.node.TreeMapNode;

public class SortedMapTrie<V> extends MapTrie<V> {

    @Override
    protected TrieNode<V> onCreateRootNode() {
        return new TreeMapNode<>(ROOT_KEY);
    }
}
