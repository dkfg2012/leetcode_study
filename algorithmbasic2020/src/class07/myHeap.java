package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class myHeap<T> {

    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comparator;

    public myHeap(Comparator<T> comparator){
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
        this.comparator = comparator;
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int size(){
        return heapSize;
    }

    public boolean contains(T obj){
        return indexMap.containsKey(obj);
    }

    public T peek(){
        return heap.get(0);
    }

    private void swap(int i, int j){
        T t = heap.get(i);
        T tt = heap.get(j);
        heap.set(i, tt);
        heap.set(j, t);
        indexMap.put(t, j);
        indexMap.put(tt, i);
    }

    public void push(T obj){
        heap.add(obj);
        heapSize++;
        int TIndex = heapSize - 1;
        indexMap.put(obj, TIndex);
        heapInsert(TIndex);
    }

    private void heapInsert(int index){
        int treeHead = (index - 1) / 2;
        while(comparator.compare(heap.get(index), heap.get(treeHead)) < 0){
            swap(index, treeHead);
            index = treeHead;
            treeHead = (index - 1) / 2;
        }
    }

    public T pop(){
        T replace = heap.get(heapSize - 1);
        T tobePop = heap.get(0);
        swap(heapSize - 1, 0);
        indexMap.remove(tobePop);
        heap.remove(heapSize - 1);
        heapSize--;
        heapify(0);
        return tobePop;
    }

    private void heapify(int index){
        int leftTreeIndex = index * 2 + 1;
        int rightTreeIndex = index * 2 + 2;
        while(leftTreeIndex < heapSize){
            int largerSubTree = rightTreeIndex < heapSize && comparator.compare(heap.get(rightTreeIndex),  heap.get(leftTreeIndex)) < 0 ? rightTreeIndex : leftTreeIndex;
            int largestIndex = comparator.compare(heap.get(largerSubTree), heap.get(index)) < 0 ? largerSubTree : index;
            if(largestIndex == index){
                break;
            }
            swap(index, largestIndex);
            index = largestIndex;
            leftTreeIndex = index * 2 + 1;
            rightTreeIndex = index * 2 + 2;
        }
    }

    public List<T> getAllElements(){
        List<T> r = new ArrayList<>();
        for(T obj : heap){
            r.add(obj);
        }
        return r;
    }

    public void remove(T obj){
        int removeIndex = indexMap.get(obj);
        T replaceObj = heap.get(heapSize - 1);
        indexMap.remove(obj);
        heap.remove(heapSize - 1);
        heapSize--;
        if(obj != replaceObj){
            heap.set(removeIndex, replaceObj);
            indexMap.put(replaceObj, removeIndex);
            resign(replaceObj);
        }
    }

    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }
}
