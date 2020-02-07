package com.chibik.perf.util;

import com.itextpdf.text.pdf.PdfEFStream;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdditionalForkJvmKeys  {

    String[] value() default {};
}
