package hw7.hashing;


import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class OpenAddressingHashMap<K, V> implements Map<K, V> {

  private static final int INITIAL_SIZE = 5;
  private static final int[] PRIMES = new int[]
      {11, 23, 47, 97, 197, 397, 797,
      1597, 3203, 6421, 12853, 25717, 51437,
      102877, 205759, 411527, 823117, 1646237,
      3292489, 6584983, 13169977};
  private final double loadFactor;
  private Entry<K, V>[] keys;
  private int curPrime;

  private int size;
  private int totalEntries;


  // the "q" in the equation; second hash
  private int moduloSecondHash;


  /**
   * Creates an empty Open Addressing Hash map.
   */
  public OpenAddressingHashMap() {
    size = 0;
    totalEntries = 0;
    curPrime = 0;

    loadFactor = 0.75;
    moduloSecondHash = 3;
    keys = (Entry<K, V>[]) new Entry[INITIAL_SIZE];
  }

  //MUST HAVE
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }

    insertAt(keys, k, v);
    size++;
    totalEntries++;
    if (totalEntries / (double) keys.length > loadFactor) {
      grow();
    }
  }


  private void insertAt(Entry<K, V>[] arr, K k, V v) throws IllegalArgumentException {
    int index = Integer.remainderUnsigned(k.hashCode(), arr.length);
    for (int i = 0; i <= arr.length - 1; i++) {
      // double hashing
      int secondIndex = Integer.remainderUnsigned(index + i
          * (moduloSecondHash - Integer.remainderUnsigned(k.hashCode(), moduloSecondHash)),
          arr.length);

      if (arr[secondIndex] == null) {
        arr[secondIndex] = new Entry<>(k, v);
        return;
      } else if (k.equals(arr[secondIndex].key)) {
        throw new IllegalArgumentException();
      }
    }

    // if here, it's full
    grow();
    insertAt(arr, k, v);
  }


  private void grow() {
    int growTo = keys.length * 2 + 1;
    moduloSecondHash = keys.length;


    if (curPrime <= PRIMES.length) {
      moduloSecondHash = PRIMES[curPrime++];
      growTo = PRIMES[curPrime];
    }

    Entry<K, V>[] newMap = (Entry<K, V>[]) new Entry[growTo];
    int i = 0;
    while (i < keys.length) {
      Entry<K, V> entry = keys[i];
      if (entry != null && entry.key != null) {
        insertAt(newMap, entry.key, entry.value);
      }
      i++;
    }
    totalEntries = size;
    keys = newMap;

  }

  private int getIndex(K k) {
    int index = Integer.remainderUnsigned(k.hashCode(), keys.length);
    for (int i = 0; i <= keys.length - 1; i++) {
      int ind = Integer.remainderUnsigned(index + i
          * (moduloSecondHash - Integer.remainderUnsigned(k.hashCode(), moduloSecondHash)),
          keys.length);

      if (keys[ind] == null) {
        break;
      } else if (k.equals(keys[ind].key)) {
        return ind;
      }
    }

    return -1;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }

    size--;

    int toRemoveIndex = getIndex(k);

    //gets entry
    int index = getIndex(k);
    Entry<K, V> toRemoveEntry;
    if (index == -1) { // meaning it's null
      throw new IllegalArgumentException();
    } else {
      toRemoveEntry = keys[index];
      keys[toRemoveIndex].key = null;
      return toRemoveEntry.value;
    }
  }


  //MUST HAVE
  @Override
  public void put(K k, V v) throws IllegalArgumentException {

    if (k == null) {
      throw new IllegalArgumentException();
    }

    int index = getIndex(k);
    Entry<K, V> entry;
    if (index == -1) {
      throw new IllegalArgumentException();
    } else {
      entry = keys[index];
      entry.value = v;
    }
  }

  //MUST HAVE
  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }


    int index = getIndex(k);
    Entry<K, V> entry;
    if (index == -1) {
      throw new IllegalArgumentException();
    } else {
      entry = keys[index];
      return entry.value;
    }
  }

  //MUST HAVE
  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }

    int index = getIndex(k);
    return index != -1;
  }

  //MUST HAVE
  @Override
  public int size() {
    return size;
  }

  //MUST HAVE
  @Override
  public Iterator<K> iterator() {
    return new OpenAddressingHashMapIterator();
  }

  private static class Entry<K, V> {
    K key;
    V value;

    private Entry(K key, V val) {
      this.key = key;
      value = val;
    }

  }

  private class OpenAddressingHashMapIterator implements Iterator<K> {
    Entry<K, V> curEntry;

    int curIndex;

    private OpenAddressingHashMapIterator() {
      curIndex = 0;


      while (curIndex < keys.length) {
        if (keys[curIndex] != null && keys[curIndex].key != null) {
          break;
        } else {
          curIndex++;
        }
      }

      if (curIndex != keys.length) {
        curEntry = keys[curIndex];
      } else {
        curEntry = null;
      }
    }

    @Override
    public boolean hasNext() {
      return curIndex < keys.length && curEntry != null;
    }

    @Override
    public K next() throws NoSuchElementException {
      K k;
      if (hasNext()) {
        k = curEntry.key;

        // keep finding next valid index
        curIndex++;
        while (curIndex < keys.length && (keys[curIndex] == null || keys[curIndex].key == null)) {
          curIndex++;
        }

        if (curIndex != keys.length) {
          curEntry = keys[curIndex];
        } else {
          curEntry = null;
        }

        return k;

      } else {
        throw new NoSuchElementException();
      }


    }
  }
}
