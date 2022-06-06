package com.github.wnebyte.jarguments;

import java.util.*;
import com.github.wnebyte.jarguments.exception.ParseException;
import static com.github.wnebyte.jarguments.util.Collections.isNullOrEmpty;

/**
 * Support class for working with instances of {@link Argument}.
 */
public class ArgumentSupport {

    public static <T extends Argument> Collection<T> getArguments(Collection<Argument> c, Class<T> cls) {
        Collection<T> cNew = new ArrayList<>();
        if (isNullOrEmpty(c) || cls == null) {
            return cNew;
        }
        for (Argument argument : c) {
            if (cls.isAssignableFrom(argument.getClass())) {
                T obj = cls.cast(argument);
                cNew.add(obj);
            }
        }

        return cNew;
    }

    public static <T extends Argument> boolean containsArgument(Collection<Argument> c, Class<T> cls) {
        if (isNullOrEmpty(c) || cls == null) {
            return false;
        }
        return c.stream().anyMatch(arg -> arg.getClass().equals(cls));
    }

    /**
     * Returns the first <code>Argument</code> from the specified <code>Collection</code> whose index
     * <code>equals</code> the specified <code>index</code>.
     * @param c the elements.
     * @param index the index.
     * @return the first <code>Argument</code> from the specified <code>Collection</code> whose index
     * <code>equals</code> the specified <code>index</code> if one exists, otherwise <code>null</code>.
     */
    public static Argument getByIndex(Collection<Argument> c, int index) {
        if (isNullOrEmpty(c) || index < 0) {
            return null;
        }
        return c.stream().filter(arg -> arg.getIndex() == index)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the first <code>Argument</code> from the specified <code>Collection</code> whose set of names
     * <code>contains</code> the specified <code>name</code>.
     * @param c the elements.
     * @param name the name.
     * @return the first <code>Argument</code> from the specified <code>Collection</code> whose set of names
     * <code>contains</code> the specified <code>name</code> if one exists, otherwise <code>null</code>.
     */
    public static Argument getByName(Collection<Argument> c, String name) {
        if (isNullOrEmpty(c) || name == null) {
            return null;
        }
        return c.stream().filter(arg -> arg.hasNames() && arg.getNames().contains(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the first <code>Positional</code> from the specified <code>Collection</code> whose position
     * <code>equals</code> the specified <code>position</code>.
     * @param c the elements.
     * @param position the position.
     * @return the first <code>Positional</code> whose position <code>equals</code> the specified
     * <code>position</code> if one exists, otherwise <code>null</code>.
     */
    public static Argument getByPosition(Collection<Argument> c, int position) {
        if (isNullOrEmpty(c) || position < 0) {
            return null;
        }
        return c.stream().filter(arg -> arg instanceof Positional)
                .map(arg -> (Positional) arg)
                .filter(arg -> arg.getPosition() == position)
                .findFirst()
                .orElse(null);

    }

    /**
     * Initializes the specified <code>Argument</code> with the specified <code>value</code>.
     * @param argument to be initialized.
     * @param value to initialize with.
     * @return the result.
     * @throws ParseException if the initialization fails.
     */
    public static Object initialize(Argument argument, String value) throws ParseException {
        return argument.initialize(value);
    }

    public static boolean matches(Argument argument, String value) {
        return (argument != null) && (value != null) && (argument.matches(value));
    }
}