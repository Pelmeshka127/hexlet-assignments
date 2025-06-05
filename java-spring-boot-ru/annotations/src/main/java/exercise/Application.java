package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);
        for (Method method: Address.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inspect.class)) {
                try {
                    method.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("Method " + method.getName());
                System.out.println(" returns a value of type " + method.getAnnotatedReturnType());
            }
        }
    }
}
