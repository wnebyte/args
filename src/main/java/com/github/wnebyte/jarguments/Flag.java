package com.github.wnebyte.jarguments;

import java.util.Set;
import java.util.Collection;
import com.github.wnebyte.jarguments.adapter.TypeAdapter;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.util.Objects;
import com.github.wnebyte.jarguments.util.Strings;

/**
 * This class represents an optional <code>Argument</code> that has only a name.
 */
public class Flag extends Optional {

    /*
    ###########################
    #         FIELDS          #
    ###########################
    */

    /**
     * The value to use during initialization when the option has been included.
     */
    protected final String value;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public <T> Flag(
            final Set<String> name,
            final String description,
            final String metavar,
            final int index,
            final Class<T> type,
            final TypeAdapter<T> typeAdapter,
            final Collection<Constraint<T>> constraints,
            final String value,
            final String defaultValue
    ) {
        super(name, description, metavar, null, index, type, typeAdapter, constraints, defaultValue);
        this.value = value;
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    @Override
    protected String pattern(final Set<String> names, final Class<?> type) {
       // return "(\\s" + "(" + String.join("|", names) + ")" + "|)";
        return "(" + String.join("|", names) + "|)";
    }

    @Override
    protected Object initialize(final String value) throws ParseException {
        if (value == null || value.equals(Strings.EMPTY)) {
            if (hasDefaultValue()) {
                String val = new Splitter()
                        .setValue(defaultValue)
                        .normalize(isArray())
                        .get();
                return typeAdapter.convert(val);
            } else {
                return typeAdapter.defaultValue();
            }
        } else {
            String val = new Splitter()
                    .setValue(this.value)
                    .normalize(isArray())
                    .get();
            return typeAdapter.convert(val);
        }
    }

    public final String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Flag))
            return false;
        Flag flag = (Flag) o;
        return Objects.equals(flag.value, this.value) &&
                super.equals(flag);
    }

    @Override
    public int hashCode() {
        int result = 12;
        return 4 * result + Objects.hashCode(value) + super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}