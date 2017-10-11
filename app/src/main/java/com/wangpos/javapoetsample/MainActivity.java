package com.wangpos.javapoetsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.Test;

/**
 *
 * 1。新建libannotation 是java Library 工程 这里定义的注解，
 *
 * 2。新建libcompiler 是java Library 工程 这里放注解解析器，
 *
 *   新建注解解析器,添加支持的注解
 *   @AutoService(Processor.class)
     public class AnnotationProcessor extends AbstractProcessor {

        @Override
        public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());

        }


        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
          //根据注解做处理，这里测试生成一个Person类
          //生成的文件在app/build/generated/source/debug/下生成
        createPersonClass();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return false;
        }
    }


 *
 *   build.gradle 配置
 *
 *   apply plugin: 'java'
     dependencies {
     compile fileTree(include: ['*.jar'], dir: 'libs')
     compile 'com.google.auto:auto-common:0.6'
     compile 'com.google.auto.service:auto-service:1.0-rc2'
     compile 'com.squareup:javapoet:1.7.0'
     compile project(':libannotation')

     sourceCompatibility = 1.7
     targetCompatibility = 1.7
     }
 *
 * 3。主项目根目录build.gradle配置
 *   classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
 *
 *   app下build.gradle 配置
 *   头部添加
 *   apply plugin: 'com.neenbedankt.android-apt'
 *   底部依赖添加
 *    apt project(':libcompiler')
      compile project(path: ':libannotation')

      在项目中使用注解，不使用注解解析器不调用
   4。make 项目
   5。在app/build/generated/source/debug/下生成Person类表示成功
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
