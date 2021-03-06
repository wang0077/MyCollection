package List.LinkedList;

import java.util.*;

/**
 * @author wangsiyuan
 */
public class MyLinkedList<E> extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable{
    private static final long serialVersionUID = -7621069716599770209L;

    transient int size = 0;

    transient Node<E> head;

    transient Node<E> tail;

    public MyLinkedList(){

    }

    public MyLinkedList(Collection<? extends E> c){
        this();
        addAll(c);
    }

    private void checkPositionIndex(int index){
        if(!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isPositionIndex(int index){
        return index >= 0 && index <= size;
    }

    /**
     *
     * head     pred                succ        tail
     * [A]<---->[B]<----->[C]<----->[D]<------->[E]---->NULL
     * 假设有多个数据要插入B和C的中间
     * pred ：记录B节点
     * succ ：记录C节点
     * 这样保证可以链接上D节点
     *
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int cur = a.length;
        if(cur == 0){
            return false;
        }

        Node<E> pred, succ;
        if(index == size){
            pred = tail;
            succ = null;
        }else{
            succ = node(index);
            pred = succ.prev;
        }

        for(Object o : a){
            @SuppressWarnings("unchecked")
            E e = (E) o;
            Node<E> newNode = new Node<>(e, null, pred);
            if(pred == null){
                head = newNode;
            }else{
                pred.next = newNode;
            }
            pred = newNode;
        }

        if(succ == null){
            tail = pred;
        }else{
            pred.next = succ;
            succ.prev = pred;
        }

        size += cur;
        return true;
    }

    Node<E> node(int index){
        Node<E> x;
        if(index < (size >> 1)){
            x = head;
            for (int i = 0; i < index;i++){
                x = x.next;
            }
        }else{
            x = tail;
            for(int i = size - 1;i > index;i--){
                x = x.prev;
            }
        }
        return x;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size,c);
    }

    private static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 头部插入数据
     */
    private void linkFirst(E e){
        final Node<E> h = head;
        final Node<E> newNode = new Node<>(e,h,null);
        head = newNode;
        if(h == null){
            tail = newNode;
        }else{
            h.prev = newNode;
        }
        size++;
    }

    /**
     * 尾部插入数据
     */
    private void linkLast(E e){
        final Node<E> t = tail;
        final Node<E> newNode = new Node<>(e,null,t);
        tail = newNode;
        if(t == null){
            head = newNode;
        }else{
            t.next = newNode;
        }
        size++;
    }

    /**
     *  在succ前插入元素e
     */
    void linkBefore(E e,Node<E> succ){
        Node<E> prev = succ.prev;
        Node<E> node = new Node<>(e,succ,prev);
        succ.prev = node;
        if(prev == null){
            head = node;
        }else{
            prev.next = node;
        }
        size++;
    }


    /**
     * 删除头结点
     */
    private E unlinkFirst(Node<E> h){
        final E element = h.item;
        final Node<E> next = h.next;
        h.item = null;
        h.next = null;
        head = next;
        if(next == null){
            tail = null;
        }else{
            next.prev = null;
        }
        size++;
        return element;
    }

    /**
     * 删除尾结点
     */
    private E unlinkLast(Node<E> t){
        final E element = t.item;
        final Node<E> prev = t.prev;
        t.prev = null;
        t.item = null;
        tail = prev;
        if(prev == null){
            head = null;
        }else{
            prev.next = null;
        }
        size++;
        return element;
    }

    /**
     * 删除指定元素并返回
     */
    E unlink(Node<E> node){
        final E element = node.item;
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;

        if(prev == null){
            head = next;
        }else{
            prev.next = next;
            node.next = null;
        }

        if(next == null){
            tail = prev;
        }else{
            next.prev = prev;
            node.prev = null;
        }
        node.item = null;
        size--;
        return element;
    }

    /**
     *  删除链表中第一个的指定元素
     */
    @Override
    public boolean remove(Object o) {
        if(o == null){
            for(Node<E> node = head;node != null;node = node.next){
                if(node.item == null){
                    unlink(node);
                    return true;
                }
            }
        }else {
            for (Node<E> node = head;node != null;node = head.next){
                if(o.equals(node.item)){
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return  null;
    }

    /**
     *  队列操作
     *  数据插入队首
     *  无返回值的
     */
    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    /**
     *  队列操作
     *  数据插入队尾
     *  无返回值
     */
    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    /**
     *  队列操作
     *  数据插入队首
     *  有返回值，返回操作是否成功
     */
    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }


    /**
     *  队列操作
     *  数据插入队尾
     *  有返回值，返回操作是否成功
     */
    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }


    /**
     *  队列操作
     *  数据移除队首
     *  有返回值，返回移除的元素
     */
    @Override
    public E removeFirst() {
        final Node<E> h = head;
        if(head == null){
            throw new NoSuchElementException();
        }
        return unlinkFirst(h);
    }


    /**
     *  队列操作
     *  数据移除队尾
     *  有返回值，返回移除的数据
     */
    @Override
    public E removeLast() {
        final Node<E> t = tail;
        if(tail == null){
            throw new NoSuchElementException();
        }
        return unlinkLast(t);
    }

    /**
     *  队列操作
     *  获取队列的队首,并移除
     */
    @Override
    public E pollFirst() {
        Node<E> node = head;
        return node == null ? null : unlinkFirst(node);
    }

    /**
     *  队列操作
     *  获取队列的队尾，并移除
     */
    @Override
    public E pollLast() {
        Node<E> node = tail;
        return node == null ? null : unlinkLast(node);
    }

    /**
     *  通过index获取该位置上的元素
     * @param index 需要获取元素的下标位置
     * @return  返回该下标位置的元素
     */
    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /**
     *  将index位置上的element进行替换
     * @param index 需要替换的下标
     * @param element 替换的新元素
     * @return 返回被替换前这个位置的元素
     */
    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> node = node(index);
        E old = node.item;
        node.item = element;
        return old;
    }

    /**
     *  在index的插入element元素
     * @param index 插入元素的位置
     * @param element 要插入的元素
     */
    @Override
    public void add(int index, E element) {
        checkElementIndex(index);
        if(index == size){
            addLast(element);
        }else{
            linkBefore(element,node(index));
        }
    }

    /**
     *  删除index标记位置上的元素
     * @param index 需要删除的元素索引下标
     * @return 返回被删除的元素
     */
    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    /**
     *  查找元素在列表第一次出现的索引下标
     * @param o 需要查找的元素
     * @return  返回对应的索引下标，如果找不到的返回-1
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if(o == null){
            for(Node<E> node = head;node != null;node = node.next){
                if(node.item == null){
                    return index;
                }
                index++;
            }
        }else {
            for(Node<E> node = head;node != null;node = node.next){
                if(o.equals(node.item)){
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    /**
     *  查找元素在列表最后一次出现的索引下标
     * @param o 需要查找的元素
     * @return 返回对应的索引下标，如果没有找到返回-1
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = size;
        if(o == null){
            for(Node<E> node = tail;node != null;node = node.prev){
                index--;
                if(node.item == null){
                    return index;
                }
            }
        }else {
            for (Node<E> node = tail;node != null;node = node.prev){
                index--;
                if(o.equals(node.item)){
                    return index;
                }
            }
        }
        return  -1;
    }

    /**
     *  清空的链表
     */
    @Override
    public void clear() {
        for(Node<E> node = head;node != null;){
            Node<E> next = node.next;
            node.item = null;
            node.prev = null;
            node.next = null;
            node = next;
        }
        tail = null;
        head = null;
        size = 0;
    }



    /**
     * 检查index的索引下标是否越界
     * @param index 需要检查的下标索引
     */
    private void checkElementIndex(int index){
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isElementIndex(int index){
        return index >= 0 && index <= size;
    }


    /**
     *  队列操作
     *  获取队列的队首，不移除
     */
    @Override
    public E getFirst() {
        Node<E> node = head;
        if(node == null){
            throw new NoSuchElementException();
        }
        return node.item;
    }

    /**
     *  队列操作
     *  获取队列的队尾，不移除
     */
    @Override
    public E getLast() {
        Node<E> node = tail;
        if(tail == null){
            throw new NoSuchElementException();
        }
        return node.item;
    }

    /**
     *  队列操作
     *  获取队列的队首，不移除，如果为null不抛出异常
     */
    @Override
    public E peekFirst() {
        Node<E> node = head;
        return node == null ? null : node.item;
    }

    /**
     *  队列操作
     *  获取队列的队尾，不移除，如果为null不抛出异常
     */
    @Override
    public E peekLast() {
        Node<E> node = tail;
        return tail == null ? null : node.item;
    }

    /**
     *  从头结点开始遍历，寻找指定元素，并删除。如果没有找到不发生改变
     */
    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    /**
     *  从尾结点开始遍历，寻找指定元素，并删除。如果没有找到不发生改变
     */
    @Override
    public boolean removeLastOccurrence(Object o) {
        if(o == null){
            for (Node<E> node = tail;node != null;node = node.prev){
                if(node.item == null){
                    unlink(node);
                    return true;
                }
            }
        }else{
            for (Node<E> node = tail;node != null;node = node.prev){
                if(o.equals(node.item)){
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }



    /**
     *  将元素追加至队尾
     */
    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 将元素追加在队尾
     */
    @Override
    public boolean offer(E e) {
        return add(e);
    }

    /**
     * 删除队首元素并返回
     */
    @Override
    public E remove() {
        return removeFirst();
    }

    /**
     *  队列操作
     *  获取队首元素，并移除
     */
    @Override
    public E poll() {
        Node<E> node = head;
        return node == null ? null : node.item;
    }

    /**
     *  队列操作
     *  检索队首元素，但是不删除
     */
    @Override
    public E element() {
        return getFirst();
    }

    /**
     *  队列操作
     *  获取队首元素，如果为null不抛出异常
     */
    @Override
    public E peek() {
        Node<E> node = head;
        return node == null ? null : node.item;
    }

    /**
     *  栈操作
     *  入栈操作
     */
    @Override
    public void push(E e) {
        addFirst(e);
    }

    /**
     *  栈操作
     *  出栈操作
     */
    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
