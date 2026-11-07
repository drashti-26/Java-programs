package util;
import java.lang.reflect.Field;
import java.util.*;
import model.annotation.*;
public final class AnnotationValidator {
    private AnnotationValidator() {}
    public static String[] validate(Object obj) {
        List<String> errors=new ArrayList<>();
        for (Class<?> c=obj.getClass(); c!=null && c!=Object.class; c=c.getSuperclass()) {
            for (Field f:c.getDeclaredFields()) {
                try { f.setAccessible(true); Object value=f.get(obj);
                    if (f.isAnnotationPresent(Positive.class) && value instanceof Number n && n.longValue()<=0)
                        errors.add(f.getName()+": "+f.getAnnotation(Positive.class).message());
                    if (f.isAnnotationPresent(MaxLength.class) && value instanceof String s && s.length()>f.getAnnotation(MaxLength.class).value())
                        errors.add(f.getName()+": maximum length is "+f.getAnnotation(MaxLength.class).value());
                } catch (ReflectiveOperationException | RuntimeException ex) { errors.add("Could not validate "+f.getName()); }
            }
        }
        return errors.toArray(String[]::new);
    }
}
