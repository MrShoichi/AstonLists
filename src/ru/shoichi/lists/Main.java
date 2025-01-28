package ru.shoichi.lists;

import ru.shoichi.lists.impls.CustomArrayList;
import ru.shoichi.lists.impls.CustomLinkedList;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(0, 3);
        list.remove(0);
        System.out.println(list);
    }
}