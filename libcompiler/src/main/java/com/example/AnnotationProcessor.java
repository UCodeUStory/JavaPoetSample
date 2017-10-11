package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by qiyue on 2017/10/11.
 */
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            createPersonClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void createPersonClass() throws IOException {
        FieldSpec age = FieldSpec.builder(int.class, "age")
                .addModifiers(Modifier.PRIVATE)
                .build();
        FieldSpec name = FieldSpec.builder(String.class, "name")
                .addModifiers(Modifier.PRIVATE)
                .initializer("$S","qiyue")
                .build();
        MethodSpec constructorMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class,"name")
                .addStatement("this.$N = $N", "name", "name")
                .build();
        MethodSpec getAgeMethod = MethodSpec.methodBuilder("getAge")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("return age")
                .build();
        MethodSpec getNameMethod = MethodSpec.methodBuilder("getName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return name")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Person")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getNameMethod)
                .addMethod(getAgeMethod)
                .addMethod(constructorMethod)
                .addField(age)
                .addField(name)
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.person",typeSpec).build();

        javaFile.writeTo(processingEnv.getFiler());
    }
}
