package koreatech.cse.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class SystemUtil {
    public static boolean setObjectFieldValue(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                if(field.getType().isAssignableFrom(String.class) )
                    field.set(object, fieldValue);
                else if(field.getType().isAssignableFrom(Integer.TYPE) )
                    field.set(object, Integer.parseInt((String)fieldValue));
                else if(field.getType().isAssignableFrom(Double.TYPE) )
                    field.set(object, Double.parseDouble((String)fieldValue));
                else if(field.getType().isAssignableFrom(Boolean.TYPE) )
                    field.set(object, Boolean.parseBoolean((String)fieldValue));
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}