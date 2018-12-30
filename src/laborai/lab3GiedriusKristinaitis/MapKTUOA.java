package laborai.lab3GiedriusKristinaitis;

import laborai.studijosktu.HashType;
import laborai.studijosktu.MapADT;

import java.util.Arrays;
import java.util.Objects;

/**
 *  HashMap implementation using linear probing collision resolution strategy
 *
 * @param <K> Key type of the element
 * @param <V> Value type of the element
 *
 * @author Giedrius Kristinaitis
 */
public class MapKTUOA<K, V> implements MapADT<K, V> {

    // default initial capacity set by the default no-args constructor
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    // default hash type set by default no-args constructor
    private static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    // default load factor
    private static final double DEFAULT_LOAD_FACTOR = 0.5;

    // hash type used to hash keys
    private HashType hashType;

    // number of elements in the hash map
    private int size;

    // capacity of the hash map
    private int capacity;

    // load factor (between 0 and 1), when it is reached, the hash table is resized
    private double loadFactor;

    // elements of the hash table
    private Element<K, V>[] elements;

    /**
     * Default class constructor. Sets initial map capacity, hash type and load factor to default values
     *
     * @author Giedrius Kristinaitis
     */
    public MapKTUOA() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_HASH_TYPE, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor. Sets initial map capacity to the specified value, hash type - default value,
     * load factor - default value
     * @param capacity initial map capacity
     *
     * @author Giedrius Kristinaitis
     */
    public MapKTUOA(int capacity) {
        this(capacity, DEFAULT_HASH_TYPE, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor. Sets hash type to specified value, initial capacity - default value,
     * load factor - default value
     * @param hashType hash type to use when hashing keys
     *
     * @author Giedrius Kristinaitis
     */
    public MapKTUOA(HashType hashType) {
        this(DEFAULT_INITIAL_CAPACITY, hashType, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor. Sets initial capacity and hash type to specified values, load factor - default value
     * @param capacity initial capacity of the map
     * @param hashType hash type to use when hashing keys
     *
     * @author Giedrius Kristinaitis
     */
    public MapKTUOA(int capacity, HashType hashType) {
        this(capacity, hashType, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor. Sets capacity, hash type and load factor to specified values
     * @param capacity
     * @param hashType
     * @param loadFactor
     */
    public MapKTUOA(int capacity, HashType hashType, double loadFactor) {
        this.capacity = capacity;
        this.hashType = hashType;
        this.loadFactor = loadFactor;

        elements = new Element[capacity];
    }

    /**
     * Checks if the map contains specified key
     * @param key key to look for
     * @return true if the key exists, false otherwise
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null at contains(K key)");
        }

        return get(key) != null;
    }

    /**
     * Clears the hash map
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * Gets the element with the specified key
     * @param key key of the element
     * @return value of the element if found, null if not found
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null at get(K key)");
        }

        int index = hash(key, hashType);

        if (elements[index] == null) {
            return null;
        }

        index = getKeyIndex(key, index);

        if (index != -1) {
            return elements[index].value;
        }

        return null;
    }

    /**
     * Gets the key index in the element array
     * @param key key to look for
     * @param startingIndex from where to start looking
     * @return index of the key, -1 if not found
     *
     * @author Giedrius Kristinaitis
     */
    private int getKeyIndex(K key, int startingIndex) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null at getKeyIndex(K key)");
        }

        if (elements[startingIndex] != null && Objects.equals(elements[startingIndex].key, key)) {
            return startingIndex;
        }

        for (int index = startingIndex + 1; index < capacity; index++) {
            if (elements[index] == null) {
                return -1;
            }

            if (Objects.equals(elements[index].key, key)) {
                return index;
            }

            if (index == startingIndex) {
                break;
            }

            if (index == capacity - 1 && startingIndex != 0) {
                index = 0;
            }
        }

        return -1;
    }

    /**
     * Hashes the specified key and returns it's index in the array;
     *
     * @param key key to hash
     * @param hashType hash function to use
     * @return index of the key
     */
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();

        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % capacity;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * capacity);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (capacity - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (capacity - 1);
            default:
                return Math.abs(h) % capacity;
        }
    }

    /**
     * Checks if the hash map is empty
     * @return true if the map is empty, false otherwise
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Gets the next empty element array index starting from the specified one
     * @param index starting index
     * @return index of the next empty slot, -1 if all slots are filled
     *
     * @author Giedrius Kristinaitis
     */
    private int nextEmptySlot(int index) {
        for (int i = index == capacity - 1 ? 0 : index + 1; i < capacity; i++) {
            if (elements[i] == null) {
                return i;
            }

            if (i == index) {
                break;
            }

            if (i == capacity - 1 && index != 0) {
                i = 0;
            }
        }

        return -1;
    }

    /**
     * Puts a new element to the hash map
     * @param key key of the element
     * @param value value of the element
     * @return newly inserted value
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null at put(K key, V value)");
        }

        if (size > capacity * loadFactor) {
            resize(capacity * 2);
        }

        int index = hash(key, hashType);

        if (elements[index] == null) {
            Element<K, V> element = new Element<K, V>(key, value);
            elements[index] = element;
            size++;
            return value;
        }

        int keyIndex = getKeyIndex(key, index);

        if (keyIndex != -1) {
            elements[keyIndex].value = value;
            return value;
        }

        index = nextEmptySlot(index);

        if(index == -1){
            return null;
        }

        Element<K, V> element = new Element<K, V>(key, value);
        elements[index] = element;
        size++;
        return value;
    }

    /**
     * Removes an element from the hash map
     * @param key key of the element
     * @return removed value
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null at remove(K key)");
        }

        if (!contains(key)) {
            return null;
        }

        int hash = hash(key, hashType);
        int index = getKeyIndex(key, hash);

        V value = elements[index].value;
        elements[index] = null;

        // shift elements
        index = (index + 1) % capacity;

        while (elements[index] != null && hash(elements[index].key, hashType) == hash) {
            if (index > 0) {
                elements[index - 1] = elements[index];
            } else {
                elements[capacity] = elements[index];
            }

            elements[index] = null;

            index = (index + 1) % capacity;
        }

        size--;

        return value;
    }

    /**
     * Resizes the hash table
     * @param newCapacity new capacity of the map
     *
     * @author Giedrius Kristinaitis
     */
    private void resize(int newCapacity) {
        MapKTUOA<K, V> temp = new MapKTUOA<K, V>(newCapacity, hashType);

        for (int index = 0; index < capacity; index++) {
            if (elements[index] != null) {
                temp.put(elements[index].key, elements[index].value);
            }
        }

        elements = temp.elements;
        capacity = newCapacity;
    }

    /**
     * Gets the number of elements in the hash map
     * @return number of elements
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Converts the hahs map to a 2D string array
     * @return 2D array representation of the hash map
     *
     * @author Giedrius Kristinaitis
     */
    @Override
    public String[][] toArray() {
        String[][] array = new String[capacity][1];

        int index = 0;

        for (Element<K, V> element: elements) {
            if (element != null) {
                array[index++][0] = element.toString();
            }
        }

        return array;
    }

    /**
     * Key-value pair (element of the hash map)
     * @param <K> Key type
     * @param <V> Value type
     *
     * @author Giedrius Kristinaitis
     */
    protected class Element<K, V> {

        protected K key;
        protected V value;

        protected Element(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString(){
            return key.toString() + " " + value.toString();
        }
    }
}
