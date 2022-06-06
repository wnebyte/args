package com.github.wnebyte.jarguments;

import java.util.Set;
import java.util.Collection;
import com.github.wnebyte.jarguments.adapter.TypeAdapter;
import com.github.wnebyte.jarguments.exception.ParseException;
import com.github.wnebyte.jarguments.util.Objects;
import com.github.wnebyte.jarguments.util.Strings;

/**
 * This class represents an optional <code>Argument</code> that has both a name and a value.
 */
public class Optional extends Argument {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    protected final String defaultValue;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public <T> Optional(
            final Set<String> name,
            final String description,
            final String metavar,
            final Set<String> choices,
            final int index,
            final Class<T> type,
            final TypeAdapter<T> typeAdapter,
            final Collection<Constraint<T>> constraints,
            final String defaultValue
    ) {
        super(name, description, metavar, choices, index, type, typeAdapter, constraints);
        this.defaultValue = defaultValue;
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    @Override
    protected String pattern(final Set<String> names, final Class<?> type) {
        return "((" + String.join("|", names) + ")" +  "\\s" +
                (isArray() ? ARRAY_VALUE_PATTERN : DEFAULT_VALUE_PATTERN) + "|)";
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
                    .setValue(value)
                    .normalize(isArray())
                    .get();
            return initializer.apply(val);
        }
    }

    public final String getDefaultValue() {
        return defaultValue;
    }

    public final boolean hasDefaultValue() {
        return (defaultValue != null) && !(defaultValue.equals(Strings.EMPTY));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (!(o instanceof Optional)) { return false; }
        Optional optional = (Optional) o;
        return Objects.equals(optional.defaultValue, this.defaultValue) &&
                super.equals(optional);
    }

    @Override
    public int hashCode() {
        int result = 87;
        return result +
                Objects.hashCode(this.defaultValue) +
                super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}