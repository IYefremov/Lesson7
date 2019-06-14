//Написать код, который сериализирует и десериализирует в/из файла все значения полей класса, которые
//        отмечены аннотацией @Save.


package Task3;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class Task3 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException {

        // создаем объект ClassForSerialise
        ClassForSerialise clsSer = new ClassForSerialise("Petro", "Ivanov", 25, "Egor", 15.45, 26.17f );

        Class clss = clsSer.getClass();
        Field[] fields = clss.getDeclaredFields();

        ClassForSerialise tmpSerCl = new ClassForSerialise();
        Class tmpCclss = tmpSerCl.getClass();
        Field[] tmpField = tmpCclss.getDeclaredFields();


        System.out.println("\nOur base object");
        for (Field aField : fields){

            System.out.println("Field " + aField.getName() + " = " + aField.get(clsSer));
        }
        // проверяем поля на наличие ClassForSerialise tmpSerCl;

        for (Field aField: fields) {
            if (aField.isAnnotationPresent(Safe.class)){  // если поле содержит аннотацию

                for (Field tmpF : tmpField){
                    if (tmpF.getName().equals(aField.getName())){   // записываем значение полей во временный объект
                        tmpF.set(tmpSerCl,aField.get(clsSer) );
                        //System.out.println(aField.get(clsSer));

                    }
                }
            }else {       // если поле не содержит аннотацию для этого поля устанавливаем значение  null

                 for (Field tmpF : tmpField){

                    if (tmpF.getName().equals(aField.getName())){
                        tmpF.set(tmpSerCl, null ); // если не аннотиресое поле устанавливаем null
                        //System.out.println("We add null for field " + tmpF.getName());
                    }
                }

            }
        }

        System.out.println("\n We have such object for serialisation. If field value is equal null - this field isn't annotated");
        for (Field aField : tmpField){

            System.out.println("Field " + aField.getName() + " = " + aField.get(tmpSerCl));
        }

        // открываем потоки для серриализации
        FileOutputStream fileOutputStream = new FileOutputStream("MySerrialFile");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        // помещаем наш объект
        objectOutputStream.writeObject(tmpSerCl);

        objectOutputStream.close();

        // десериализация
        FileInputStream fileInputStream = new FileInputStream("MySerrialFile");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ClassForSerialise  newClsSer = (ClassForSerialise) objectInputStream.readObject();
        objectInputStream.close();

        // выводим в консоль значения объекта после дессериализации
        System.out.println("\nAfter deserialisation we have ");
        System.out.println(newClsSer.name + " " +  newClsSer.surname + " " + newClsSer.age + " " + newClsSer.futhersName +
                " " + newClsSer.aDouble + " " + newClsSer.aFloat);

    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Safe{
}



// создаем класс, который будем серриализовать
class ClassForSerialise implements Serializable{

    @Safe
    String name;

    @Safe
    String surname;

    @Safe
    Integer age;

    String futhersName;

    @Safe
    Double aDouble;

    Float  aFloat;

    ClassForSerialise(String name, String surmane, Integer age, String futhersName, Double aDouble, Float aFloat ){
        this.name = name;
        this.surname = surmane;
        this.age = age;
        this.futhersName = futhersName;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
    }
    ClassForSerialise(){

    }
}