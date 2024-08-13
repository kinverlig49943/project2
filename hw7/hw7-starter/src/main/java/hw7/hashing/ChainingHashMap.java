
package hw7.hashing;


import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ChainingHashMap<K, V> implements Map<K, V> {
  private static final int INITIAL_SIZE = 5;
  private static final int[] PRIMES = new int[]{11, 23, 47, 97, 197, 397, 797,
    1597, 3203, 6421, 12853, 25717, 51437,
    102877, 205759, 411527, 823117, 1646237,
    3292489, 6584983, 13169977};
  private final double loadFactor;
  private Entry<K, V>[] keys;
  private int curPrime;
  private int size;


  /**
   * Creates an empty Chaining Hash map.
   */
  public ChainingHashMap() {
    size = 0;
    curPrime = 0;
    loadFactor = 0.75;


    keys = (Entry<K, V>[]) new Entry[INITIAL_SIZE];
  }


  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k != null && !has(k)) {
      insertAt(keys, k, v);
      size++;
      grow();
    } else {
      throw new IllegalArgumentException();
    }
  }


  private void grow() {
    //if load factor has surpassed length
    if ((double) size / keys.length > loadFactor) {
      //grow the hash table
      int growTo = keys.length * 2 + 1;


      if (curPrime <= PRIMES.length) {
        growTo = PRIMES[curPrime++];
      }


      Entry<K, V>[] newMap = (Entry<K, V>[]) new Entry[growTo];


      for (Entry<K, V> entry : keys) {
        if (entry != null) {
          //insert each element in the chain to the new array
          for (Entry<K, V> curEntry = entry; curEntry != null; curEntry = curEntry.next) {
            insertAt(newMap, curEntry.key, curEntry.value);
          }
        }
      }


      //reassign array
      keys = newMap;
    }
  }


  private void insertAt(Entry<K, V>[] arr, K k, V v) {
    int index = Integer.remainderUnsigned(k.hashCode(), arr.length);
    Entry<K, V> entry = new Entry<K, V>(k, v);

    //prepend to list
    entry.next = arr[index];
    arr[index] = entry;
  }


  private Entry<K, V> getEntry(K k) {
    Entry<K, V> head = keys[Integer.remainderUnsigned(k.hashCode(), keys.length)];
    while (head != null) {
      if (!head.key.equals(k)) {
        head = head.next;
      } else {
        break;
      }
    }

    return head;
  }


  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k != null && has(k)) {
      Entry<K, V> entry = keys[Integer.remainderUnsigned(k.hashCode(), keys.length)];
      V value = entry.value;

      if (entry.key.equals(k)) {
        keys[Integer.remainderUnsigned(k.hashCode(), keys.length)] = entry.next;
      } else {
        while (entry.next != null && !entry.next.key.equals(k)) {
          entry = entry.next;
        }
        if (entry.next != null) {
          entry.next = entry.next.next;
        }
      }

      size--;
      return value;
    }

    //if not in list or null, it is invalid
    throw new IllegalArgumentException();
  }


  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k != null && has(k)) {
      Entry<K, V> head = getEntry(k);
      head.value = v;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k != null && has(k)) {
      Entry<K, V> head = getEntry(k);
      return head.value;
    }

    throw new IllegalArgumentException();
  }


  @Override
  public boolean has(K k) {
    if (k != null) {
      Entry<K, V> entry = getEntry(k);
      return entry != null;
    }

    return false;
  }


  @Override
  public int size() {
    return size;
  }


  @Override
  public Iterator<K> iterator() {
    return new ChainingHashMapIterator();
  }


  private static class Entry<K, V> {
    K key;
    V value;
    Entry<K, V> next;


    private Entry(K key, V val) {
      this.key = key;
      value = val;
      next = null;
    }
  }


  private class ChainingHashMapIterator implements Iterator<K> {
    Entry<K, V> curEntry;
    int curIndex;

    private ChainingHashMapIterator() {
      curIndex = 0;
      //find next entry and valid index
      nextEntry();
    }


    @Override
    public boolean hasNext() {
      return curIndex < keys.length;
    }

    @Override
    public K next() throws NoSuchElementException {
      if (hasNext()) {
        K k = curEntry.key;

        //loops through entire linked list
        curEntry = curEntry.next;
        //find index of next head when it is the end of linked list
        if (curEntry == null) {
          curIndex++;
          //continue finding next valid index & entry
          nextEntry();
        }

        return k;
      }

      throw new NoSuchElementException();
    }


    private void nextEntry() {
      for (int i = curIndex; i < keys.length && keys[i] == null; i++) {
        curIndex++;
      }

      if (curIndex != keys.length) {
        curEntry = keys[curIndex];
      } else {
        curEntry = null;
      }
    }

  }

}
