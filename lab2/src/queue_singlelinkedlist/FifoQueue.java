package queue_singlelinkedlist;

import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible post: The
	 * specified element is added to the rear of this queue
	 * 
	 * @param e the element to insert
	 * @return true if it was possible to add the element to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> node = new QueueNode<E>(e);

		if (last == null) {
			node.next = node;
		} else {
			// kopierar next-referens från förra last till nya last next-referens.
			// Sedan sätts förra last next-referens till nya last.
			node.next = last.next;
			last.next = node;
		}

		last = node;
		size++;

		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty
	 * 
	 * @return the head element of this queue, or null if this queue is empty
	 */
	public E peek() {
		if (last != null) {
			return last.next.element;
		}
		return null;
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		if (last != null) {
			QueueNode<E> first = last.next;
			last.next = last.next.next;
			size--;
			if (size == 0) {
				last = null;
			}
			return first.element;
		}
		return null;
	}
	
	/**
	* Appends the specified queue to this queue
	* post: all elements from the specified queue are appended
	* to this queue. The specified queue (q) is empty after the call.
	* @param q the queue to append
	* @throws IllegalArgumentException if this queue and q are identical
	*/
	public void append(FifoQueue<E> q) {
		if(q == this)  {  
			throw new IllegalArgumentException();
		}
		
		if(last == null) { //Kollar om listan är tom
			last = q.last;
		} else if(q.last != null) {
			QueueNode<E> qLast = q.last;
			QueueNode<E> qFirst = q.last.next;
			
			qLast.next = last.next; 
			last.next = qFirst; 
			last = qLast; 
		}
		
		size += q.size;
		//tömmer nya listan (Som nu konkanerats i vår första).
		q.last = null;
		q.size = 0;
	}
	
	

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> itr;

		private QueueIterator() {
			if(last!=null) {
			itr = last.next;
			} else {
				itr = null;
			}
		}

		public boolean hasNext() {
			if(itr!=null) {
				return true;
			} else {
				return false;
			}
		}

		public E next() {
			if (hasNext()) {
				E e = itr.element;
				//Kollar att det inte går runt i cirklar
				if(itr == last) {
					itr = null;
				} else {
					itr = itr.next;
				}
				return e;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}
