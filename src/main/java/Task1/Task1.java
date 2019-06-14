//        Создать аннотацию, которая принимает параметры для метода.
//        Написать код, который вызовет метод, помеченный этой аннотацией,
//        и передаст параметры аннотации в вызываемый метод.
//        @Test(a=2, b=5)
//

package Task1;

import java.lang.annotation.*;
import java.lang.reflect.Method;


public class Task1 {

    public static void main(String[] args) throws NoSuchMethodException {

        MyClass myClass = new MyClass();
        Class clss = myClass.getClass();

        // Вызываем метод, помеченный аннотацией и передаем в него аттрибуты анотации
        try {
            System.out.println("Run the annotated method");
            Method m = clss.getMethod("myMethod", int.class, int.class);
            Test t = m.getAnnotation(Test.class);
            int a = t.a();
            int b = t.b();
            System.out.println("The result of annotated method is " + myClass.myMethod(a, b));
        }catch (NoSuchMethodException e){
            System.out.println("Exception NoSuchMethodException");
        }


        // То же самое, но короче
        try {

            Test ann = MyClass.class.getMethod("myMethod", int.class, int.class).getAnnotation(Test.class);
            System.out.println("The second variant - result of annotated method is " + myClass.myMethod(ann.a(), ann.b()));
        }catch(NoSuchMethodException e){
            System.out.println("Exception NoSuchMethodException");

        }
    }
}

class MyClass{


// создаем метод и добавляем аннотацию
@Test(a = 28, b = 11)
    public int myMethod(int a, int b){

    return a + b;
    }

}

// создаем аннотацию
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Test{
    int a();
    int b();
}



