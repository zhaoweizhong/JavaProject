package main;

import java.util.ArrayList;
import java.util.List;
import data.Data;

public class MainTest {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<>();
        list.add("王利虎");
        list.add("张三");
        list.add("李四");
        System.out.println(list);
        String str = Data.toString(list);
        System.out.println(str);
        System.out.println(Data.toArrayList(str));

    }
}
