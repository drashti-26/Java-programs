package service;
import java.lang.reflect.Field;
import java.util.*;
import model.annotation.Id;
/** Generic in-memory repository; @Id identifies each entity's key. */
public class Repository<T> {
    private final HashMap<String,T> data=new HashMap<>();
    public synchronized T save(T entity) {
        Objects.requireNonNull(entity,"entity"); String id=readId(entity); data.put(id,entity); return entity;
    }
    @SafeVarargs public final synchronized void saveAll(T... items) { for(T item:items) save(item); }
    public synchronized T findById(String id) { return data.get(id); }
    public synchronized ArrayList<T> findAll() { return new ArrayList<>(data.values()); }
    public synchronized boolean delete(String id) { return data.remove(id)!=null; }
    public synchronized int size() { return data.size(); }
    private String readId(T entity) {
        for(Class<?> c=entity.getClass();c!=null && c!=Object.class;c=c.getSuperclass()) for(Field f:c.getDeclaredFields())
            if(f.isAnnotationPresent(Id.class)) try { f.setAccessible(true); Object v=f.get(entity); return Objects.toString(v); }
            catch(ReflectiveOperationException e) { throw new IllegalStateException("Cannot read @Id",e); }
        throw new IllegalArgumentException("Entity has no field annotated with @Id: "+entity.getClass().getName());
    }
    public static <N extends Number> double average(N[] values) {
        if(values==null || values.length==0) return 0.0; double sum=0; for(N n:values) sum+=n.doubleValue(); return sum/values.length;
    }
}
