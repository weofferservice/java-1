package org.zcorp.java1;

import org.zcorp.java1.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws Exception {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(r));

        System.out.println(r);
    }
}