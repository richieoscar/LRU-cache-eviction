package cache;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Test cases for LRUCache implementation.
 */
public class LRUCacheTest {
    
    @Test
    @DisplayName("Test basic put and get operations")
    public void testBasicPutAndGet() {
        LRUCache<String, Integer> cache = new LRUCache<>(2);
        
        cache.put("a", 1);
        cache.put("b", 2);
        
        assertEquals(Integer.valueOf(1), cache.get("a"));
        assertEquals(Integer.valueOf(2), cache.get("b"));
    }
    
    @Test
    @DisplayName("Test get returns null for non-existent key")
    public void testGetNonExistentKey() {
        LRUCache<String, Integer> cache = new LRUCache<>(2);
        
        cache.put("a", 1);
        
        assertNull(cache.get("b"));
    }
    
    @Test
    @DisplayName("Test LRU eviction when capacity is reached")
    public void testLRUEviction() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three"); // Should evict key 1 (least recently used)
        
        assertNull(cache.get(1), "Key 1 should have been evicted");
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }
    
    @Test
    @DisplayName("Test accessing an item makes it most recently used")
    public void testAccessUpdatesLRUOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        
        cache.put(1, "one");
        cache.put(2, "two");
        
        // Access key 1, making it most recently used
        cache.get(1);
        
        // Add new item, should evict key 2 (now least recently used)
        cache.put(3, "three");
        
        assertEquals("one", cache.get(1));
        assertNull(cache.get(2), "Key 2 should have been evicted");
        assertEquals("three", cache.get(3));
    }
    
    @Test
    @DisplayName("Test updating existing key")
    public void testUpdateExistingKey() {
        LRUCache<String, Integer> cache = new LRUCache<>(2);
        
        cache.put("a", 1);
        cache.put("a", 10);
        
        assertEquals(Integer.valueOf(10), cache.get("a"));
        assertEquals(1, cache.size());
    }
    
    @Test
    @DisplayName("Test update makes key most recently used")
    public void testUpdateMakesKeyMostRecentlyUsed() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        
        cache.put(1, "one");
        cache.put(2, "two");
        
        // Update key 1
        cache.put(1, "updated one");
        
        // Add new item, should evict key 2
        cache.put(3, "three");
        
        assertEquals("updated one", cache.get(1));
        assertNull(cache.get(2), "Key 2 should have been evicted");
        assertEquals("three", cache.get(3));
    }
    
    @Test
    @DisplayName("Test remove operation")
    public void testRemove() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        
        assertTrue(cache.remove("b"));
        assertFalse(cache.remove("b"), "Removing non-existent key should return false");
        
        assertEquals(2, cache.size());
        assertNull(cache.get("b"));
        assertEquals(Integer.valueOf(1), cache.get("a"));
        assertEquals(Integer.valueOf(3), cache.get("c"));
    }
    
    @Test
    @DisplayName("Test size tracking")
    public void testSizeTracking() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        
        assertEquals(0, cache.size());
        
        cache.put(1, "one");
        assertEquals(1, cache.size());
        
        cache.put(2, "two");
        assertEquals(2, cache.size());
        
        cache.put(3, "three");
        assertEquals(3, cache.size());
        
        cache.put(4, "four"); // Should evict one item
        assertEquals(3, cache.size());
        
        cache.remove(2);
        assertEquals(2, cache.size());
    }
    
    @Test
    @DisplayName("Test clear operation")
    public void testClear() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        
        cache.clear();
        
        assertEquals(0, cache.size());
        assertNull(cache.get("a"));
        assertNull(cache.get("b"));
        assertNull(cache.get("c"));
    }
    
    @Test
    @DisplayName("Test capacity 1 cache")
    public void testCapacityOne() {
        LRUCache<Integer, String> cache = new LRUCache<>(1);
        
        cache.put(1, "one");
        assertEquals("one", cache.get(1));
        
        cache.put(2, "two"); // Should evict key 1
        assertNull(cache.get(1));
        assertEquals("two", cache.get(2));
    }
    
    @Test
    @DisplayName("Test constructor with invalid capacity")
    public void testInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LRUCache<>(0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new LRUCache<>(-1);
        });
    }
    
    @Test
    @DisplayName("Test with null values")
    public void testNullValues() {
        LRUCache<String, String> cache = new LRUCache<>(2);
        
        cache.put("key", null);
        assertNull(cache.get("key"));
        assertEquals(1, cache.size());
    }
    
    @Test
    @DisplayName("Test multiple evictions in sequence")
    public void testMultipleEvictions() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four"); // Evicts 1
        cache.put(5, "five"); // Evicts 2
        
        assertNull(cache.get(1));
        assertNull(cache.get(2));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
        assertEquals("five", cache.get(5));
    }
    
    @Test
    @DisplayName("Test getCapacity method")
    public void testGetCapacity() {
        LRUCache<String, Integer> cache1 = new LRUCache<>(5);
        assertEquals(5, cache1.getCapacity());
        
        LRUCache<Integer, String> cache2 = new LRUCache<>(10);
        assertEquals(10, cache2.getCapacity());
    }
}
