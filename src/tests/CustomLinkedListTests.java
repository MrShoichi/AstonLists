package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shoichi.lists.impls.CustomLinkedList;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CustomLinkedListTests {
    private static CustomLinkedList<Integer> list;

    @BeforeAll
    public static void setUp() {
        list = new CustomLinkedList<>();
    }

    @AfterEach
    public void tearDown() {
        list.clear();
    }


    @Test
    public void testAdd() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testAddInPosition() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1, 5);
        assertEquals(5, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(1, list.get(0));
    }

    @Test
    public void testAddFirst() {
        list.add(1);
        list.addFirst(2);
        assertEquals(2, list.get(0));
        assertEquals(1, list.get(1));
        list.add(3);
        list.addFirst(4);
        assertEquals(4, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    public void testAddLast() {
        list.add(1);
        list.addLast(2);
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(0));
        list.add(3);
        list.addLast(4);
        assertEquals(4, list.get(3));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testPositiveRemoveForIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resRemove = list.remove(0);
        assertEquals(1, resRemove);
        assertEquals(2, list.get(0));
        list.add(4);
        resRemove = list.remove(1);
        assertEquals(3, resRemove);
        assertEquals(4, list.get(1));
    }

    @Test
    public void testRemoveFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resRemove = list.removeFirst();
        assertEquals(1, resRemove);
        assertEquals(2, list.get(0));
        list.addFirst(4);
        resRemove = list.removeFirst();
        assertEquals(4, resRemove);
        assertEquals(3, list.get(1));
    }

    @Test
    public void testRemoveLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resRemove = list.removeLast();
        assertEquals(3, resRemove);
        assertEquals(1, list.get(0));
        list.addLast(4);
        resRemove = list.removeLast();
        assertEquals(4, resRemove);
        assertEquals(2, list.get(1));
    }

    @Test
    public void testPositiveRemoveForObject() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resResult = list.remove((Integer) 2);
        assertTrue(resResult);
        assertEquals(3, list.get(1));
        list.add(4);
        resResult = list.remove((Integer) 1);
        assertTrue(resResult);
        assertEquals(4, list.get(1));
    }

    @Test
    public void testNegativeRemoveForIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.remove(-1);
            list.remove(4);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        fail("Ожидалось исключение IndexOutOfBoundsException");
    }

    @Test
    public void testNegativeRemoveForObject() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resResult = list.remove((Integer) 5);
        assertFalse(resResult, "Элемент не должен был быть удален, так как его в списке нет");
    }

    @Test
    public void testNegativeRemoveFirst() {
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            return;
        }
        fail("Ожидалось исключение NoSuchElementException");
    }

    @Test
    public void testNegativeRemoveLast() {
        try {
            list.removeLast();
        } catch (NoSuchElementException e) {
            return;
        }
        fail("Ожидалось исключение NoSuchElementException");
    }



    @Test
    public void testSort() {
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(1);
        list.sort(Integer::compareTo);
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(4, list.get(2));
        assertEquals(5, list.get(3));
    }

    @Test
    public void testContains() {
        list.add(1);
        list.add(2);
        list.add(3);
        var resResult = list.contains(2);
        assertTrue(resResult, "Элемент 2 должен был быть в списке");
        resResult = list.contains(5);
        assertFalse(resResult, "Элемент не должен был быть найден, так как его в списке нет");
    }



    @Test
    public void testClear() throws NoSuchFieldException, IllegalAccessException {
        list.add(1);
        list.add(2);
        list.add(3);
        list.clear();
        assertEquals(0, list.size());
        var headField = list.getClass().getDeclaredField("head");
        headField.setAccessible(true);
        var tailField = list.getClass().getDeclaredField("tail");
        tailField.setAccessible(true);

        assertNull(headField.get(list), "Начальный узел списка должна быть null");
        assertNull(tailField.get(list), "Конечный узел списка должна быть null");

    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty(), "Список должен быть пустым");
        list.add(1);
        list.add(2);
        assertFalse(list.isEmpty(), "Список не должен быть пустым");
    }


}
