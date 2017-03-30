package com.longb.mapper;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

class Cursor2Bean {

    private static final ClassName CURSOR = ClassName.get("android.database", "Cursor");
    private static final ClassName FUNCTION = ClassName.get("rx.functions", "Func1");

    private static final String FUNCTION_METHOD_NAME = "call";

    public void generateCode(TypeSpec.Builder typeSpecBuilder, AnnotatedClass annotatedClass) {
        String instanceName = annotatedClass.typeElement.getSimpleName().toString().toLowerCase();
        String methodName = "map";
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(CURSOR, "cursor")
                .addStatement("$T $N = new $L()", annotatedClass.getType(), instanceName, annotatedClass.getType())
                .returns(ClassName.get(annotatedClass.getType()));
        addConveter(annotatedClass, instanceName, methodSpecBuilder);
        methodSpecBuilder.addStatement("return $L", instanceName);

        FieldSpec fieldSpec = createRxJavaMapper(annotatedClass, methodName);

        typeSpecBuilder.addMethod(methodSpecBuilder.build()).addField(fieldSpec);
    }

    private void addConveter(AnnotatedClass annotatedClass, String instanceName, MethodSpec.Builder builder) {
        for (Element e : annotatedClass.variables) {
            if (e.getModifiers().contains(STATIC) || e.getModifiers().contains(PRIVATE)) {
                continue;
            }
            TypeName typeName = TypeName.get(e.asType());
            String cursorMethod = getCursorMethod(typeName);
            CodeBlock codeBlock = cursorMethod == null ? null : CodeBlock.builder().add(getCursorMethod(typeName), getColumnIndexOrThrow(e).toString()).build();
            builder.addStatement("$N.$L = $L", instanceName, e.getSimpleName().toString(), codeBlock);
        }
    }

    /**
     * 生成RxJava mapper
     * @param annotatedClass
     * @param methodName
     * @return
     */
    private FieldSpec createRxJavaMapper(AnnotatedClass annotatedClass, String methodName) {
        TypeName rTypeName = TypeName.get(annotatedClass.getType());
        TypeName functionName = ParameterizedTypeName.get(FUNCTION, CURSOR, rTypeName);
        MethodSpec functionMethod =
                MethodSpec.methodBuilder(FUNCTION_METHOD_NAME)
                        .addAnnotation(Override.class)
                        .addModifiers(PUBLIC)
                        .addParameter(CURSOR, "c")
                        .returns(rTypeName)
                        .addStatement("return $L($N)", methodName, "c")
                        .build();
        TypeSpec function =
                TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(functionName)
                        .addMethod(functionMethod)
                        .build();
        return FieldSpec.builder(functionName, annotatedClass.typeElement.getSimpleName().toString() + "Mapper", STATIC, FINAL)
                .initializer("$L", function)
                .build();
    }

    private CodeBlock getColumnIndexOrThrow(Element element) {
        return CodeBlock.builder().add("cursor.getColumnIndexOrThrow($S)", element.getSimpleName()).build();
    }

    private String getCursorMethod(TypeName type) {
        if (type.equals(TypeName.DOUBLE) || type.equals(TypeName.DOUBLE.box())) {
            return "cursor.getDouble($L)";
        }
        if (type.equals(TypeName.FLOAT) || type.equals(TypeName.FLOAT.box())) {
            return "cursor.getFloat($L)";
        }
        if (type.equals(TypeName.INT) || type.equals(TypeName.INT.box())) {
            return "cursor.getInt($L)";
        }
        if (type.equals(TypeName.LONG) || type.equals(TypeName.LONG.box())) {
            return "cursor.getLong($L)";
        }
        if (type.equals(TypeName.SHORT) || type.equals(TypeName.SHORT.box())) {
            return "cursor.getShort($L)";
        }
        if (type.equals(TypeName.get(String.class))) {
            return "cursor.getString($L)";
        }
        if (type.equals(TypeName.BOOLEAN) || type.equals(TypeName.BOOLEAN.box())) {
            return "cursor.getInt($L) == 1";
        }
        if (type.equals(TypeName.get(byte[].class)) || type.equals(TypeName.get(Byte[].class))) {
            return "cursor.getBlob($L)";
        }
        return null;
    }
}
