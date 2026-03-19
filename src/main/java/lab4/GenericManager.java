/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4;

/**
 *
 * @author PC
 */
import java.util.ArrayList;

public class GenericManager<T> {
    private ArrayList<T> list = new ArrayList<>();

    public void add(T obj) {
        list.add(obj);
    }

    public void showAll() {
        for (T obj : list) {
            System.out.println(obj.toString());
        }
    }

    public int size() {
        return list.size();
    }

    public T getFirst() throws EmptyListException {
        if (list.isEmpty()) throw new EmptyListException("Danh sách rỗng!");
        return list.get(0);
    }

    public T getLast() throws EmptyListException {
        if (list.isEmpty()) throw new EmptyListException("Danh sách rỗng!");
        return list.get(list.size() - 1);
    }
}
