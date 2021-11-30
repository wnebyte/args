package com.github.wnebyte.args;

import com.github.wnebyte.args.factory.ArgumentFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.regex.Pattern;

public class IntegrationTest {

    @Test
    public void testOptionalArguments() throws Exception {
        List<Argument> arguments = new ArgumentFactoryBuilder().build()
                .setNames("-a", "--a")
                .setType(double.class)
                .isOptional()
                .create()
                .setNames("-b", "--b")
                .setType(short.class)
                .isOptional()
                .create()
                .getArguments();
        String input = "--a 100.5";
        Pattern pattern = new PatternCreator().create(arguments);
        boolean matches = matches(pattern, input);
        IParser parser = new Parser(arguments);
        Object[] args = parser.parse(input);
        double a = (double) args[0];
        Assert.assertTrue(matches);
        Assert.assertEquals(100.5, a, 0.0);
        Assert.assertTrue(matches(pattern,
                "-b 5",
                "--b 5",
                "",
                "--a 10.2 -b 99",
                "--b 99 -a 10.2")
        );
    }

    private boolean matches(final Pattern pattern, final String... input) {
        for (String str : input) {
            boolean matches = pattern.matcher(str).matches();
            if (!matches) {
                return false;
            }
        }
        return true;
    }
}