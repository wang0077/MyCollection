package Set;

import java.util.*;

/**
 * @author wangsiyuan
 */
public class MyHashSet<E> extends AbstractSet<E>
        implements Set<E>, Cloneable, java.io.Serializable {

    private static final long serialVersionUID = -3542665421189617210L;

    private transient HashMap<E,Object> map;

    private static final Object PRESENT = new Object();

    public MyHashSet(){
        map = new HashMap<>();
    }

    public MyHashSet(Collection<? extends E> c){
        map = new HashMap<>(Math.max((int)(c.size() / 0.75f),16));
        addAll(c);
    }

    public MyHashSet(int initialCapacity,float loadFactor){
        map = new HashMap<>(initialCapacity,loadFactor);
    }

    public MyHashSet(int initialCapacity){
        map = new HashMap<>(initialCapacity);
    }

    MyHashSet(int initialCapacity,float loadFactor,boolean dummy){
        map = new LinkedHashMap<>(initialCapacity,loadFactor);
    }



    @Override
    public boolean add(E e) {
        return map.put(e,PRESENT) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object clone(){
        try {
            MyHashSet<E> newSet = (MyHashSet<E>)super.clone();
            newSet.map = (HashMap<E, Object>) newSet.map.clone();
            return newSet;
        }catch (CloneNotSupportedException e){
            throw new InternalError(e);
        }
    }
}

