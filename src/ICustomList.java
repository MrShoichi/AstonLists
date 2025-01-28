import java.util.Comparator;

public interface ICustomList<T> {
    void add(T o);
    void add(int index, T o);
    T get(int index);
    T remove(int index);
    boolean remove(T o);
    void clear();
    void sort(Comparator<? super T> c);
    int size();
    boolean contains(T o);
    boolean isEmpty();
}
