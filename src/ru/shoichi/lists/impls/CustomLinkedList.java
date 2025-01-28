package ru.shoichi.lists.impls;

import ru.shoichi.lists.interfaces.ICustomList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * ru.shoichi.lists.impls.CustomLinkedList - реализация двусвязного списка. Класс предоставляет основные методы работы со списком.
 * @param <T> тип элементов списка
 */
public class CustomLinkedList<T> implements ICustomList<T> {

    /**
     * Начало списка.
     */
    private Node<T> head;
    /**
     * Конец списка
     */
    private Node<T> tail;
    /**
     * Количество элементов списка
     */
    private int size;


    /**
     * Метод добавления элемента в список.
     * Скорость O(1).
     * @param o добавляемый элемент.
     */
    @Override
    public void add(T o) {
        addLast(o);
    }

    /**
     * Метод вставки элемента на определенную позицию списка.
     * Средняя скорость O(n).
     *
     * @param index индекс в списке.
     * @param o добавляемый элемент.
     * @throws IndexOutOfBoundsException Если индекс зашел за границы списка. (index < 0 || index >= size)
     */
    @Override
    public void add(int index, T o) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(o);
            return;
        }

        var existsNode = getNode(index);
        existsNode.last.next = new Node<>(o, existsNode, existsNode.last);
        existsNode.last = existsNode.last.next;
        size++;

    }

    /**
     * Метод добавления элемента в начало списка.
     * Скорость O(1)
     *
     * @param o добавляемый элемент.
     */
    public void addFirst(T o) {
        var node = new Node<>(o);
        if (head != null) {
            head.last = node;
        }
        if (tail == null) {
            tail = node;
        }
        node.next = head;
        head = node;

        size++;
    }

    /**
     * Метод добавления элемента в конец списка.
     * Скорость O(1)
     *
     * @param o добавляемый элемент.
     */
    public void addLast(T o) {
        var node = new Node<>(o, tail);

        if (tail != null) {
            tail.next = node;
        }
        if (head == null) {
            head = node;
        }
        tail = node;

        size++;
    }

    /**
     * Метод получения элемента списка на позиции.
     * Средняя скорость O(n).
     *
     * @param index индекс элемента.
     * @return элемент списка.
     * @throws IndexOutOfBoundsException Если индекс зашел за границы списка. (index < 0 || index >= size)
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).data;
    }

    /**
     * Метод удаления элемента списка на позиции.
     * Средняя скорость O(n).
     *
     * @param index индекс элемента.
     * @return удаленный элемент списка.
     * @throws IndexOutOfBoundsException Если индекс зашел за границы списка. (index < 0 || index >= size)
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }

        var node = getNode(index);
        node.next.last = node.last;
        node.last.next = node.next;
        size--;
        return node.data;
    }

    /**
     * Метод удаления последнего элемента списка.
     * Скорость O(1).
     *
     * @return удаленный элемент списка.
     * @throws NoSuchElementException Если список пуст.
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        var temp = tail;
        if (size != 1) {
            tail.last.next = null;
            tail = tail.last;
        } else {
            clear();
        }
        size--;
        return temp.data;
    }
    /**
     * Метод удаления первого элемента списка.
     * Скорость O(1).
     *
     * @return удаленный элемент списка.
     * @throws NoSuchElementException Если список пуст.
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        var tempHead = head;

        if(size == 1) {
            clear();
            return tempHead.data;
        }
        head.next.last = null;
        head = head.next;

        size--;

        return tempHead.data;
    }

    /**
     * Метод удаления определеноого элемента из списка.
     * Средня скорость O(n).
     *
     * @param o удаляемый элемен списка.
     * @return True если элемент был удален, иначе False.
     */
    @Override
    public boolean remove(T o) {
        var node = getNode(o);
        if (node == null) {
            return false;
        }
        if (head == node) {
            removeFirst();
        } else if (tail == node) {
            removeLast();
        } else {
            node.next.last = node.last;
            node.last.next = node.next;
            size--;
        }
        return true;
    }

    /**
     * Метод поиска узла по индексу.
     *
     * @param index "индекс" узла.
     * @return найденный узел.
     */
    private Node<T> getNode(int index) {
        var node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    /**
     * Метод поиска узла по данным.
     *
     * @param o данные узла.
     * @return найденный узел.
     */
    private Node<T> getNode(T o) {
        var node = head;
        while (node != null) {
            if(node.data.equals(o))
                return node;
            node = node.next;
        }
        return node;
    }


    /**
     * Метод очистки списка.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Метод сортировки списка. Метод принимает компаратор и
     * использует его для сравнения элементов списка и их сортировки.
     * Средняя скорость O(n^2).
     *
     * @param c Компоратор для сравнения элементов списка
     */
    @Override
    public void sort(Comparator<? super T> c) {
        if (size <= 2) {
            return;
        }
        for (int i = 1; i < size; i++) {
            var firstNode = head;
            for (int j = 0; j < size - i; j++) {
                var nextNode = firstNode.next;
                if (c.compare(firstNode.data, nextNode.data) > 0) {
                    if(firstNode == head) {
                        head = nextNode;
                    }
                    if(nextNode == tail) {
                        tail = firstNode;
                    }
                    swap(firstNode, nextNode);
                }
                else {
                    firstNode = nextNode;
                }
            }
        }
    }

    /**
     * Метод замены местами двух узлов в двусвязном списке.
     * Метод меняет ссылкы двух узлов так, чтобы поменять их местами.
     *
     * @param firstNode Первый узел
     * @param secondNode Второй узел
     */
    private static <T> void swap(Node<T> firstNode, Node<T> secondNode) {
        if(firstNode.last != null) {
            firstNode.last.next = secondNode;
        }
        if(secondNode.next != null) {
            secondNode.next.last = firstNode;
        }
        firstNode.next = secondNode.next;
        secondNode.last = firstNode.last;
        firstNode.last = secondNode;
        secondNode.next = firstNode;
    }

    /**
     * Метод получения размера списка.
     *
     * @return Размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Метод поиска элемента в списке.
     *
     * @param o элемент, чей поиск будет производиться.
     * @return True если такой элемент есть в списке, иначе False
     */
    @Override
    public boolean contains(T o) {
        return getNode(o) != null;
    }

    /**
     * Метод проверки пустой ли список.
     *
     * @return True если список пустой, иначе False
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        var node = head;
        var arrayString = new StringBuilder("[");
        while (node.next != null) {
            arrayString.append(node.data).append(", ");
            node = node.next;
        }
        arrayString.append(node.data);
        arrayString.append("]");
        return arrayString.toString();
    }

    /**
     * Класс реализация узла списка
     * @param <T> тип хранимых данных узлом
     */
    private static class Node<T> {
        /**
         * Данные узла
         */
        T data;
        /**
         * Ссылка на следующий узел
         */
        Node<T> next;
        /**
         * Ссылка на следующий предыдущий узел
         */
        Node<T> last;

        /**
         * Конструктор для создания узла c данными и ссылками.
         *
         * @param data данные узла.
         * @param next ссылка на следующий узел.
         * @param last ссылка на предыдущий узел.
         */
        public Node(T data, Node<T> next, Node<T> last) {
            this.data = data;
            this.next = next;
            this.last = last;
        }

        /**
         * Конструктор для создания узла c данными.
         * Ссылки по-умолчанию равны null.
         *
         * @param data данные узла.
         */
        public Node(T data) {
            this(data, null, null);
        }

        /**
         * Конструктор для создания узла c данными и ссылкой на прошлый узел.
         * Ссылка на следующий узел по-умолчанию равны null.
         *
         * @param data данные узла.
         * @param last ссылка на предыдущий узел.
         */
        public Node(T data, Node<T> last) {
            this(data, null, last);
        }


    }

}
