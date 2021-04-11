package List.ArraiList;

import com.sun.org.apache.xml.internal.serializer.EmptySerializer;

import java.util.*;
import java.util.function.ObjIntConsumer;

/**
 * @author wangsiyuan
 */
public class MyArrayList<E> extends AbstractList<E> implements List<E>,Cloneable, java.io.Serializable{

    private static final long serialVersionUID = 8904707232016090463L;

    /**
     *  设置默认大小
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     *  空数组(暂时不知道啥用)
     */
    private static final Object[] EMPTY_ARRAY = {};

    /**
     *  另一个空数组(还是不知道干什么用)
     */
    private static final Object[] ELSE_EMPTY_ARRAY = {};

    /**
     *  它还是一个数组(我还是暂时不知道干什么用)
     */
    transient Object[] elementData;

    /**
     *  ArrayList的数组长度(我终于知道这个是干什么的了)
     */
    private int size;

    /**
     * 构造一个空ArrayList,并制定大小
     * @param initCapacity 初始化的大小
     */
    public MyArrayList(int initCapacity){
        if(initCapacity > 0){
            this.elementData = new Object[initCapacity];
        }else if(initCapacity == 0){
            this.elementData = EMPTY_ARRAY;
        }else{
            throw new IllegalArgumentException("数组初始化大小不能小于0");
        }
    }

    /**
     * 空参构造器(还不知道为什么，有参和空参分别赋值的数组不同)
     */
    public MyArrayList(){
        this.elementData = ELSE_EMPTY_ARRAY;
    }


    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        rangeCheckForAdd(index);
        E old = elementData(index);
        fastRemove(index);
        return old;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            for (int i = 0; i < size;i++){
                if(elementData[i] == null){
                    fastRemove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0;i < size;i++){
                if(o.equals(elementData[i])){
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }

    private void fastRemove(int index){
        if (index != size - 1) {
            for (int i = index; i < size - 1; i++) {
                elementData[i] = elementData[i + 1];
            }
        }
        elementData[--size] = null;
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index){
        return (E) elementData[index];
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        Object temp = element;
        size++;
        for(int i = index;i < size;i++){
            Object temp1 = elementData[index];
            elementData[i] = temp;
            temp = temp1;
        }
    }

    /**
     * 检查添加的位置是否越界
     * @param index
     */
    public void rangeCheckForAdd(int index){
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     *  判断数组是否为一开始赋的初始数组
     */
    private int calculateCapacity(int minCapacity){
        if (elementData == ELSE_EMPTY_ARRAY){
            return Math.max(DEFAULT_CAPACITY,minCapacity);
        }
        return minCapacity;
    }

    /**
     * 确保数组的大小足够
     * @param minCapacity 最少需要的空间
     */
    private void ensureCapacityInternal(int minCapacity){
        ensureExplicitCapacity(calculateCapacity(minCapacity));
    }

    /**
     *  判断数组的空间是否足够，不够的进行扩容
     */
    private void ensureExplicitCapacity(int minCapacity){
        //如果数组大小不够的话进行扩容
        if(minCapacity - elementData.length > 0){
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        int old = elementData.length;
        int cur = old + (old >> 1);
        //确保扩容以后容量足够
        if(minCapacity > cur){
            cur = minCapacity;
        }
        elementData = Arrays.copyOf(elementData,cur);
    }

    public MyArrayList(Collection<? extends E> c){
        this.elementData = c.toArray();
        size = elementData.length;
        if(size == 0){
            this.elementData = EMPTY_ARRAY;
        }
    }



    @Override
    public E get(int index) {
        return elementData(index);
    }

    @Override
    public int size() {
        return size;
    }
}
