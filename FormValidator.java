import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank { }

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    String name;

    @MaxLength(10)
    String username;

    SignupForm(String name, String username) {
        this.name = name;
        this.username = username;
    }
}

public class FormValidator {
    public static String[] validate(Object obj) {
        ArrayList<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                String str = value == null ? "" : value.toString();

                if (field.isAnnotationPresent(NotBlank.class) && str.trim().isEmpty()) {
                    errors.add(field.getName() + " must not be blank");
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    int max = field.getAnnotation(MaxLength.class).value();
                    if (str.length() > max) {
                        errors.add(field.getName() + " must be at most " + max + " characters");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Could not validate " + field.getName());
            }
        }
        return errors.toArray(new String[0]);
    }

    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "verylongusername");
        String[] errors = validate(form);
        for (String error : errors) System.out.println(error);
        if (errors.length == 0) System.out.println("Form is valid");
    }
}
