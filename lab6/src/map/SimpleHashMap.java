package map;

import java.util.Random;

public class SimpleHashMap<K, V> implements Map<K, V> {
	private Entry<K, V>[] table;
	private int capacity;
	private double loadFactor = 0.75;
	private int size;

	public static void main(String[] args) {
		Random rand = new Random();
		SimpleHashMap<Integer, Integer> map = new SimpleHashMap<Integer, Integer>(10);

		int nbr = 20;
		for (int i = 0; i < nbr; i++) {
			int r = rand.nextInt(100);
			map.put(r, r);
		}

		System.out.println(map.show());

	}

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and the
	 * default load factor (0.75).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashMap() {
		capacity = 16;
		table = (Entry<K, V>[]) new Entry[capacity];
		size = 0;
		

	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		table = (Entry<K, V>[]) new Entry[capacity];
		size = 0;
	}

	@Override
	public V get(Object k) {
		@SuppressWarnings("unchecked")
		Entry<K, V> entry = find(index((K) k), (K) k);
		if (entry != null) {
			return entry.getValue();
		} else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		// if size is 0, it's empty
		return size() == 0;
	}

	@Override
	public V put(K k, V v) {
		int i = index(k);
		Entry<K, V> temp = find(i, k);

		// Checking old value
		V old = null;
		if (temp != null) {
			old = temp.getValue();
			temp.setValue(v);
		} else {
			Entry<K, V> entry = new Entry<>(k, v);
			
			if (table[i] == null) {
				table[i] = entry;
				size++;

			} else { 
				temp = table[i];
				
				
				while (temp.next != null) {
					temp = temp.next;
					
				}
			size++;
			temp.setNext(entry);

			}

		}

		// Higher than load factor
		if (size() >= loadFactor * table.length) {
			rehash();
		}

		return old;
	}

	@Override
	public V remove(Object key) { // ta bort en size
		K k = (K) key;
		int i = index(k);

		// table is empty
		if (isEmpty()) {
			return null;
		}
		
		Entry<K, V> entry = table[i];
		// no entry matches key
		if (entry == null) {
			return null;
		}

		// Checks first value
		if (entry.key.equals(k)) {
			table[i] = entry.next;
			size--;
			return entry.value;
		}

		// finding value later in list
		Entry<K, V> p = entry;
		entry = entry.next;

		while (entry != null) {
			if (entry.key.equals(k)) {
				p.next = entry.next;
				size--;
				return entry.value;
			}

			p = entry;
			entry = entry.next;
		}

		return null;

	}

	@Override
	public int size() { 
		/*
		 * int size = 0; for (int i = 0; i < table.length; i++) { Entry<K, V> temp =
		 * table[i];
		 * 
		 * while (temp != null) { temp = temp.next; size++;
		 * 
		 * } }
		 */
		 
		return size;
	}

	public String show() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				sb.append(i + "\t null \n");
			} else {
				sb.append(i + "\t" + table[i].toString());

				Entry<K, V> t = table[i];
				while (t.next != null) {
					sb.append("\t" + table[i].next.toString());
					t = t.next;
				}
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Private method to return the specific index for key
	 */
	@SuppressWarnings("unused")
	private int index(K key) {
		int i = key.hashCode() % table.length;
		if (i < 0) {
			return i + table.length;
		} else {
			return i;
		}
	}

	/**
	 * Private method to return the Entry pair containing key in position index in
	 * the hashtable. If not found, return null
	 */
	@SuppressWarnings("unused")
	private Entry<K, V> find(int index, K key) {
		Entry<K, V> temp = table[index];
		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return temp;
			}
			temp = temp.next;
		}
		return null;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void rehash() {

		Entry<K, V>[] previous = table;
		table = (Entry<K, V>[]) new Entry[table.length * 2]; // doubling the length of table

		size = 0;
		// loop through old/previous values
		for (Entry<K, V> temp : previous) {
			Entry<K, V> e = temp;
			

			while (e != null) { 
				put(e.getKey(), e.getValue());
				e = e.next;
			}
		}

	}

	public static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public V setValue(V val) {
			this.value = val;
			return value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

		public Entry<K, V> setNext(Entry<K, V> n) {
			next = n;
			return next;
		}

	}

}
