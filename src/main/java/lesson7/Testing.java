package lesson7;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Testing {

    private static Object obj;
    public Class myClassObject;
    public static int A;
    public static int B;

    public Testing(Object obj, int a, int b) {
        this.obj = obj;
        myClassObject = obj.getClass();
        A = a;
        B = b;
        start(myClassObject);
    }

    public String getNameClass(){
        return myClassObject.getName();
    }

    public String getSimpleNameClass(){
        return myClassObject.getSimpleName();
    }

    public int getModifiersClass(){
        return myClassObject.getModifiers();
    }

    public Package getPackageClass(){
        return myClassObject.getPackage();
    }

    public Class getSuperclass(){
        return myClassObject.getSuperclass();
    }

    public Class[] getInterfacesSuperClass(){
        return myClassObject.getInterfaces();
    }

    public Constructor[] getConstructorsClass(){
        return myClassObject.getConstructors();
    }

    public Constructor getConstructor(){
        try {
            return myClassObject.getConstructor(new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class[] getParameterTypes() throws NoSuchMethodException {
        return myClassObject.getConstructor().getParameterTypes();
    }

    public Field[] getFields(){
        return myClassObject.getDeclaredFields();
    }

    public Field getField(String name){
        try {
            return myClassObject.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void start(Class cls) {
        int as = 0;
        int bs = 0;
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class)) as++;
            if (method.isAnnotationPresent(BeforeSuite.class)) bs++;
            if (as > 1 || bs > 1) throw new RuntimeException();
        }
        try {
            for (Method method : methods) {
                if (method.isAnnotationPresent(BeforeSuite.class)) {
                    method.invoke(obj);
                    break;
                }
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(Test.class))
                    info(cls, method);
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(AfterSuite.class)) {
                    method.invoke(obj);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void info(Class cls, Method method) throws IllegalAccessException, InvocationTargetException {
        System.out.println(method + " " +
                cls.getDeclaredFields()[0].getName() + " = " + A + " "  + cls.getDeclaredFields()[1].getName()  + " = " + B + " "  + "Result=" + method.invoke(obj,A,B));
    }

    public static void start(String name){
//        Class aClass = SomeObject.class;
//        Field getField = aClass.getField("stringName");
//        SomeObject instance = new SomeObject();
//        Object value = getField.get(instance);

    }

}
/*
1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями @Test.
Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект типа Class, или имя класса.
Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test,
а по завершению всех тестов – метод с аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10),
в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения. Методы с аннотациями
@BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».

2. Написать программу для проверки ДЗ
(Проанализировать папку с компилированными классами и вызвать методы, проверить результат)
*/