package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * T一定要是非基础类型，有基础类型需求包一层
 */

//加強堆,用戶可要求刪除數據,堆需要快速找到對應的數據刪除後也要保持堆結構
public class HeapGreater<T> {

	private ArrayList<T> heap;
	private HashMap<T, Integer> indexMap;
	private int heapSize;
	private Comparator<? super T> comp;

	public HeapGreater(Comparator<T> c) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>(); //建立object和index位置的映射表,用戶輸入後能夠立刻返回obj在heap array中的位置
		heapSize = 0;
		comp = c;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}

	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	public T peek() {
		return heap.get(0);
	}

	public void push(T obj) {
		//push是在array的最後添加
		heap.add(obj);
		indexMap.put(obj, heapSize);
		heapInsert(heapSize++);
	}

	public T pop() {
		T ans = heap.get(0);
		swap(0, heapSize - 1);
		indexMap.remove(ans);
		heap.remove(--heapSize);
		heapify(0);
		return ans;
	}

	public void remove(T obj) {
		//拿最後的obj作為替代
		//先獲取要remove的obj的位置
		//heap size - 1, 並且把上述的位置交給最後obj(作為替代), 把其插入進這個index內,並且更新hashmap
		//然後進行heapify確保heap的性質
		T replace = heap.get(heapSize - 1);
		int index = indexMap.get(obj);
		indexMap.remove(obj);
		heap.remove(--heapSize);
		if (obj != replace) {
			heap.set(index, replace);
			indexMap.put(replace, index);
			resign(replace);
		}
	}

	public void resign(T obj) {
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}

	// 请返回堆上的所有元素
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		for (T c : heap) {
			ans.add(c);
		}
		return ans;
	}

	private void heapInsert(int index) {
		//對比index和它的tree head,從最底層一層一層往上推來確保heap的特性沒變
		//返回負數即index即index的obj應該放在head的前面,即兩者需要互換
		//這裡並沒有提供對比的細節,但是如果compare(a,b)得出-1,即a在b的前面
		//而這裡,index毫無疑問是在(index-1)/2 (index-1/2 > index)的後面
		//說明放的位置不對,需要交換
		while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	private void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
			best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
			if (best == index) {
				break;
			}
			swap(best, index);
			index = best;
			left = index * 2 + 1;
		}
	}

	private void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		heap.set(i, o2);
		heap.set(j, o1);
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}

}
