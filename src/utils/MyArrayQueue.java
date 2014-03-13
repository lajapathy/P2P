package utils;

import java.util.Iterator;

public class MyArrayQueue<Item> implements Iterable<Item> {
	
	private Item[] itemArray = (Item[]) new Object[1];
	private int head=0;
	private int tail=-1;
	
	public void enqueue(Item item) {
		if (head==itemArray.length) {
			if (tail<(head/2)) {
				resizeArray(2*itemArray.length);
			} else {
				shiftArray();
			}
		}
		itemArray[head++] = item;
	}
	
	public Item dequeue() {
		if (!isEmpty()) {
			tail++;
			Item out = itemArray[tail];
			itemArray[tail] = null;
			if (head<(itemArray.length/4)) {
				if (tail<(head/2)) {
					resizeArray(itemArray.length/2);
				} else {
					shiftArray();
					while (true)
					{						
						if (head<(itemArray.length/4)) {
							resizeArray(itemArray.length/2);
						} else {
							break;
						}
					}
				}
			}
			return out;
		} else {
			return null;
		}
	}
	
	public Boolean isEmpty() {
		return ((head-tail)==1);
	}
	
	private void resizeArray(int resize) {
		Item[] copy = (Item[]) new Object[resize];
		int length = Math.min(resize, itemArray.length);
		for (int idx=0;idx<length;idx++) {
			copy[idx] = itemArray[idx];
			itemArray[idx] = null;
		}
		itemArray = copy;		
	}
	
	private void shiftArray()
	{
		int idx, dstIdx;
		for (idx=tail+1,dstIdx=0;idx<head;idx++,dstIdx++) {
			itemArray[dstIdx] = itemArray[idx];
			itemArray[idx] = null;
		}
		head = dstIdx;
		tail = -1;		
	}
	
	public Iterator<Item> iterator() {
		return (new MyListIterator());
	}
	
	private class MyListIterator implements Iterator<Item> {
		private int cur = tail;
		public boolean hasNext() {
			return ((head-cur)==1);
		}
		public Item next() {
			Item out = itemArray[cur];
			cur++;
			return out;
		}
		public void remove() {
			// not implemented
		}
	}

}
