# LRU Cache Implementation

## Approach

The LRU (Least Recently Used) cache is implemented using a combination of a **HashMap** and a **doubly-linked list**. This hybrid data structure achieves O(1) time complexity for both `get` and `put` operations, which is optimal for cache implementations.

The HashMap provides constant-time lookup by mapping keys to their corresponding nodes in the linked list. The doubly-linked list maintains the access order of elements, with the most recently used item at the head and the least recently used item at the tail. A doubly-linked list is chosen over a singly-linked list because it allows O(1) removal of any node when we have a reference to it—this is critical when updating or evicting items. To simplify boundary conditions, the implementation uses dummy head and tail nodes, eliminating the need for null checks at the list boundaries.

When `get(key)` is called, the node is retrieved from the HashMap in O(1) time, then moved to the head of the list to mark it as most recently used. When `put(key, value)` is called, if the key exists, its value is updated and the node is moved to the head. If the key is new and the cache is at capacity, the tail node (least recently used) is removed before inserting the new node at the head. This design ensures that the cache always evicts the item that hasn't been accessed for the longest time, fulfilling the LRU eviction policy efficiently.

## Files

- `src/main/java/cache/LRUCache.java` - Generic LRU cache implementation
- `src/test/java/cache/LRUCacheTest.java` - Comprehensive test suite (14 test cases)

## Running Tests

```bash
mvn test
```

All 14 tests pass, covering:
- Basic put/get operations
- LRU eviction behavior
- Access order updates
- Key updates and their effect on LRU order
- Remove and clear operations
- Size tracking
- Edge cases (capacity 1, null values, invalid capacity)
