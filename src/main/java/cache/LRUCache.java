package cache;

/**
 * A generic LRU (Least Recently Used) cache implementation.
 * Uses a combination of HashMap and doubly-linked list for O(1) get and put operations.
 * 
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class LRUCache<K, V> {
    
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
