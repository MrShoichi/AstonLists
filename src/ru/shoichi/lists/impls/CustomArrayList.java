package ru.shoichi.lists.impls;

import ru.shoichi.lists.interfaces.ICustomList;

import java.util.Comparator;

/**
 * ru.shoichi.lists.impls.CustomArrayList - реализация списка с динамическим массивом.
 * <p>
 * Этот класс предоставляет основные операции работы со списком.
 *
 * @param <T> тип элементов списка
 */
public class CustomArrayList<T> implements ICustomList<T> {

    /**
     * Массив для хранения элементов
     */
    private T[] array;
    /**
     * Количество элементов в списке
     */
    private int size;
    /**
     * Стандартный размер списка
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * Текущий размер списка
     */
    private int capacity;

    /**
     * Конструктор для создания списка с заданным размером.
     *
     * @param capacity начальный размер списка.
     */
    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.size = 0;
    }

    /**
     * Базовый конструктор с базовым размером - 16.
     */
    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }


    @Override
    public void add(T o) {
        if (array == null) {
            array = (T[]) new Object[DEFAULT_CAPACITY];
        }
        if (size == capacity) {
            resize(capacity * 2);
        }
        array[size++] = o;
    }


    @Override
    public void add(int index, T o) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (array == null) {
            array = (T[]) new Object[DEFAULT_CAPACITY];
        }
        if (size == capacity) {
            resize(capacity * 2);
        }


        for (int i = size++; i >= index + 1; i--) {
            array[i] = array[i - 1];
        }
        array[index] = o;
    }

    /**
     * Метод изменения размера списка
     *
     * @param newCapacity новый размер списка
     */
    private void resize(int newCapacity) {
        var temp = array;
        capacity = newCapacity;
        array = (T[]) new Object[newCapacity];
        System.arraycopy(temp, 0, array, 0, temp.length);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        var temp = array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        return temp;
    }



    @Override
    public boolean remove(T o) {
        int index = find(o);
        if (index == -1) {
            return false;
        }
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        return true;
    }


    private int find(T o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void clear() {
        capacity = DEFAULT_CAPACITY;
        for(int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void sort(Comparator<? super T> c) {
        if (size == 0) {
            return;
        }
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size - i; j++) {
                if (c.compare(array[j], array[j + 1]) >= 0) {
                    var temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean contains(T o) {
        return find(o) != -1;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        var arrayString = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            arrayString.append(array[i]).append(", ");
        }
        arrayString.append(array[size - 1]);
        arrayString.append("]");
        return arrayString.toString();
    }


}
