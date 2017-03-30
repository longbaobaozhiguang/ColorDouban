package com.longb.mapper;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by longb on 2017/3/21.
 */

public class Bean2ContentValue {
    private static final ClassName CONTENT_VALUES = ClassName.get("android.content", "ContentValues");

    private static final String CONTENT_VALUES_NAME = "contentValues";

    public void generateCode(TypeSpec.Builder typeSpecBuilder, AnnotatedClass annotatedClass) {
        String beanName = annotatedClass.typeElement.getSimpleName().toString().toLowerCase();
        String methodName = "map2ContentValues";
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(methodName)
                .addModifiers(PUBLIC, STATIC)
                .addParameter(TypeName.get(annotatedClass.getType()), beanName)
                .addStatement("$T $N = new $L()", CONTENT_VALUES, CONTENT_VALUES_NAME, CONTENT_VALUES)
                .returns(CONTENT_VALUES);
        addConveter(annotatedClass,beanName,methodSpecBuilder);
        methodSpecBuilder.addStatement("return $N", CONTENT_VALUES_NAME);
        typeSpecBuilder.addMethod(methodSpecBuilder.build());
    }

    private void addConveter(AnnotatedClass annotatedClass, String instanceName, MethodSpec.Builder builder) {
        for (Element e : annotatedClass.variables) {
            if (e.getModifiers().contains(STATIC) || e.getModifiers().contains(PRIVATE)) {
                continue;
            }
            String elementName = e.getSimpleName().toString();
            builder.addStatement("$N.put($S, $N)",CONTENT_VALUES_NAME,elementName,instanceName + "." + elementName);
        }
    }
}
