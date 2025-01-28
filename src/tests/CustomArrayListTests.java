package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shoichi.lists.impls.CustomArrayList;
import ru.shoichi.lists.interfaces.CustomList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CustomArrayListTests {
    private static CustomList<Integer> list;

    @BeforeAll
    public static void setUp() {
        list = new CustomArrayList<>();
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
    public void testAddWithResize() throws NoSuchFieldException, IllegalAccessException {
        list.add(1);
        list.add(2);
        list.add(3);
        var capacityField = list.getClass().getDeclaredField("capacity");
        capacityField.setAccessible(true);
        assertEquals(16, capacityField.get(list));
        for (int i = 0; i < 14; i++) {
            list.add(i);
        }
        assertEquals(32, capacityField.get(list));
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
    public void testNegativeRemoveBeforeBoundsForIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        fail("Ожидалось исключение IndexOutOfBoundsException");
    }

    @Test
    public void testNegativeRemoveAfterBoundsForIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        try {
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
        list.add(1);
        list.add(2);
        list.add(3);
        var resResult = list.remove((Integer) 5);
        assertFalse(resResult, "Элемент не должен был быть удален, так как его в списке нет");
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
    public void testClear() throws NoSuchFieldException, IllegalAccessException {
        list.add(1);
        list.add(2);
        list.add(3);
        list.clear();
        assertEquals(0, list.size());
        var capacityField = list.getClass().getDeclaredField("capacity");
        capacityField.setAccessible(true);
        assertEquals(16, capacityField.get(list), "Вместимость списка должна быть сброшена до 16");
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty(), "Список должен быть пустым");
        list.add(1);
        list.add(2);
        assertFalse(list.isEmpty(), "Список не должен быть пустым");
    }


}
