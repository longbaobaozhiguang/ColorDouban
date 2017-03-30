package com.longb.mapper;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class CursorProcessor extends AbstractProcessor {




    private static final String ANNOTATION = "@" + CursorMapper.class.getSimpleName();

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(CursorMapper.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CursorMapper.class);

        for (Element element : elements) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, ANNOTATION + " only support class");
                continue;
            }
            AnnotatedClass annotatedClass = buildAnnotatedClass((TypeElement) element);
            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(annotatedClass.typeElement.getSimpleName().toString() + "Mapper");
            String packageName = getPackageName(processingEnv.getElementUtils(), annotatedClass.typeElement);
            new Cursor2Bean().generateCode(typeSpecBuilder, annotatedClass);
            new Bean2ContentValue().generateCode(typeSpecBuilder,annotatedClass);
            writeToFile(packageName, typeSpecBuilder.build());
        }
        return true;
    }

    private void writeToFile(String packageName, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "cannot write java file");
        }
    }


    private AnnotatedClass buildAnnotatedClass(TypeElement typeElement) {
        List<Element> varibaleElements = new ArrayList<>();
        for (Element e : typeElement.getEnclosedElements()) {
            if (e instanceof VariableElement) {
                varibaleElements.add(e);
            }
        }
        return new AnnotatedClass(typeElement, varibaleElements);
    }

    private String getPackageName(Elements elements, TypeElement typeElement) {
        PackageElement pkg = elements.getPackageOf(typeElement);
        return pkg.getQualifiedName().toString();
    }


}
