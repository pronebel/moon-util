package com.moon.office.excel.annotations;

/**
 * @author benshaoye
 */
public @interface TableRow {

    String var() default "";

    TableCell[] value() default {};

    String when() default "true";

    String skipRows() default "0";

    String[] delimiters() default {};

    String className() default "";
}
