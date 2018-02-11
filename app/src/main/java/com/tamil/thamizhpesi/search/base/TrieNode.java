
package com.tamil.thamizhpesi.search.base;

import java.util.Collection;

public interface TrieNode<V> {

    V getValue();

    void setValue(V value);

    String getChar();

    TrieNode<V> addChild(final String Stringacter);

    TrieNode<V> getChild(final String Stringacter);

    void removeChild(final String Stringacter);

    Collection<TrieNode<V>> getChildren();

    boolean isEnd();

    boolean isKey();

    void setKey(boolean isKey);

    void print();

    boolean containsChild(String c);

    void clear();
}
