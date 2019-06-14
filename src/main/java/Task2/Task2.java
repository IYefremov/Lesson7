//Написать класс TextContainer, который содержит в себе строку. С помощью механизма аннотаций указать
//        1) в какой файл должен сохраниться текст 2) метод, который выполнит сохранение.
//          Написать класс Saver,  который сохранит поле класса TextContainer в указанный файл.
//@SaveTo(path=“c:\\file.txt”)
//class Container {
//    String text = “...”;
//    @Saver
//    public void save(..) {...}
//}
//

package Task2;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Task2 {

    public static void main(String[] args) throws Exception {
        String filePath = "";
        try {
            TextContainer textContainer = new TextContainer();
            Class clss = textContainer.getClass();

            Field f = clss.getDeclaredField("pathString");
            SaveTo ann = f.getAnnotation(SaveTo.class);

            System.out.println("The annotation contains such path " + ann.annPath());
            filePath = ann.annPath();

        } catch (NoSuchFieldException e) {
            System.out.println("My Exception " + e.toString());
        }


        Saver saver = new Saver();
        Class clssSaver = saver.getClass();
        Method[] meth = clssSaver.getDeclaredMethods();

        for (Method aMetods : meth) {
            if (aMetods.isAnnotationPresent(SaveToMethod.class)) {
                System.out.println("The method " + aMetods.getName() + " contains the annotation");
                aMetods.invoke(saver, filePath, aMetods.getName());
            } else {
                System.out.println("The methods in the " + clssSaver.getName() + " are not annotated");
            }
        }

    }


    static class TextContainer {

        @SaveTo(annPath = "d:\\\\file1.txt")
        String pathString;
    }
}

// создаем аннотацию
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SaveTo {
    String annPath();
}

// создаем аннотацию
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface SaveToMethod {
}


class Saver {

    @SaveToMethod
    public void save(String s, String text) {

        File log = new File(s);

        try {
            if (!log.exists()) {
                System.out.println("We had to make a new file.");
                log.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(log, false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text + "\n");
            bufferedWriter.close();

            System.out.println("The text " + "\"" + text + "\"" + " is pushed into the file " + s);

        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }

    }

}

