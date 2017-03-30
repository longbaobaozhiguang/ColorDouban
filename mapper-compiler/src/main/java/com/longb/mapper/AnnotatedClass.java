package com.longb.mapper;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;


class AnnotatedClass {
    // 整个类元素
    public final TypeElement typeElement;
    // 类名
    public final String annotatedClassName;
    // 成员变量
    public final List<Element> variables;

    public AnnotatedClass(TypeElement typeElement, List<Element> variableNames) {
        this.annotatedClassName = typeElement.getSimpleName().toString();
        this.variables = variableNames;
        this.typeElement = typeElement;
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }
}
