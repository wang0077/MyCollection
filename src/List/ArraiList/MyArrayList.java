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

    /**
     * 将传入的集合数据添加到新的ArrayList
     * @param c 旧的集合数据
     */
    public MyArrayList(Collection<? extends E> c){
        this.elementData = c.toArray();
        size = elementData.length;
        if(size == 0){
            this.elementData = EMPTY_ARRAY;
        }
    }

    /**
     * 在数组末端添加数据
     * @param e Data
     */
    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 删除指定位置的Data
     * @param index 指定数组位置
     * @return 返回删除的Data
     */
    @Override
    public E remove(int index) {
        rangeCheckForAdd(index);
        E old = elementData(index);
        fastRemove(index);
        return old;
    }

    /**
     * 删除数组的指定数据,如果有多个数据，删除最前的一个数据
     * @param o 需要删除的数据
     */
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

    /**
     * 快速删除，如果在数组中间的位置删除的话，对数组空隙进行移动
     * @param index 如果删除的数组下标
     */
    private void fastRemove(int index){
        if (index != size - 1) {
            for (int i = index; i < size - 1; i++) {
                elementData[i] = elementData[i + 1];
            }
        }
        elementData[--size] = null;
    }

    /**
     * 获取指定下标的数组元素
     * @param index 指定下标
     * @return 数组元素
     */
    @SuppressWarnings("unchecked")
    private E elementData(int index){
        return (E) elementData[index];
    }

    /**
     * 在数组的指定位置添加数据
     * @param index 指定的数组下标
     * @param element Data
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        Object temp = element;
        size++;
        for(int i = index;i < size;i++){
            Object temp1 = elementData[i];
            elementData[i] = temp;
            temp = temp1;
        }
    }

    /**
     * 检查添加的位置是否越界
     * @param index 指定的下标
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


    /**
     * 清空ArrayList
     */
    @Override
    public void clear() {
        for(int i = 0; i < size;i++){
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * 获取数组中指定元素的最后一个下标位置
     * @param o 指定元素
     * @return 返回最后一个下标位置,如果没有找打返回-1
     */
    @Override
    public int lastIndexOf(Object o) {
        if(o == null){
            for (int i = size - 1;i >= 0;i--){
                if(elementData[i] == null){
                    return i;
                }
            }
        }else{
            for (int  i = size - 1;i >= 0;i--){
                if(o.equals(elementData[i])){
                    return i;
                }
            }
        }
        return  -1;
    }

    /**
     * 替换指定下标位置的元素
     * @param index 下标位置
     * @param element 添加元素
     * @return 返回之前的数组元素
     */
    @Override
    public E set(int index, E element) {
        rangeCheckForAdd(index);
        E old = elementData(index);
        elementData[index] = element;
        return old;
    }

    /**
     * 获取传入数据在ArrayList的下标
     * @param o 传入数据
     * @return 下标,如果没有找到返回-1
     */
    @Override
    public int indexOf(Object o) {
        if(o == null){
            for(int i = 0;i < elementData.length;i++){
                if(elementData[i] == null){
                    return i;
                }
            }
        }else{
            for (int i = 0; i < elementData.length;i++){
                if(o.equals(elementData[i])){
                    return i;
                }
            }
        }
        return -1;
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
