package org.checkerframework.checker.mustcall.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.JavaExpression;

@Target({ElementType.METHOD})
@InheritedAnnotation
@Repeatable(List.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatesMustCallFor {

    @Documented
    @Target({ElementType.METHOD})
    @InheritedAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        CreatesMustCallFor[] value();
    }

    @JavaExpression
    String value() default "this";
}
