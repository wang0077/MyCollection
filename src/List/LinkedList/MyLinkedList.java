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


    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        final Node<E> h = head;
        if(head == null){
            throw new NoSuchElementException();
        }
        return unlinkFirst(h);
    }

    @Override
    public E removeLast() {
        final Node<E> t = tail;
        if(tail == null){
            throw new NoSuchElementException();
        }
        return unlinkLast(t);
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
