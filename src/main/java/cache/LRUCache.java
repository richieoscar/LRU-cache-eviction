package cache;

import java.util.HashMap;
import java.util.Map;

/**
 * A generic LRU (Least Recently Used) cache implementation.
 * Uses a combination of HashMap and doubly-linked list for O(1) get and put operations.
 * 
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class LRUCache<K, V> {
    
    private final int capacity;
    private int size;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> list;
    
    /**
     * Create a new LRU cache with the specified capacity.
     * 
     * @param capacity the maximum number of items the cache can hold
     * @throws IllegalArgumentException if capacity is not positive
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }
    
    /**
     * Get the value associated with the key.
     * If the key exists, it becomes the most recently used.
     * 
     * @param key the key to look up
     * @return the value if found, null otherwise
     */
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        
        Node<K, V> node = map.get(key);
        // Move to front (most recently used)
        list.remove(node);
        list.addFirst(node);
        
        return node.value;
    }
    
    /**
     * Node for the doubly-linked list.
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    /**
     * Doubly-linked list to maintain access order.
     * Head represents most recently used, tail represents least recently used.
     */
    private static class DoublyLinkedList<K, V> {
        private final Node<K, V> head; // dummy head
        private final Node<K, V> tail; // dummy tail
        
        DoublyLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }
        
        void addFirst(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
        
        void remove(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
        }
        
        Node<K, V> removeLast() {
            if (head.next == tail) {
                return null; // empty list
            }
            Node<K, V> lru = tail.prev;
            remove(lru);
            return lru;
        }
        
        void clear() {
            head.next = tail;
            tail.prev = head;
        }
    }
}
