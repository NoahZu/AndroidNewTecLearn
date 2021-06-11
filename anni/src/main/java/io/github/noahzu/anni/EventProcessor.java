package io.github.noahzu.anni;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class EventProcessor extends AbstractProcessor {
    /**
     * 用来处理TypeMirror的工具类
     */
    private Types mTypeUtils;
    /**
     * 用于创建文件
     */
    private Filer mFiler;
    /**
     * 用于打印信息
     */
    private Messager mMessager;
    /**
     * 用来处理Element的工具类
     */
    private Elements mElementUtils;

    private List<EventAnnotatedClass> eventAnnotatedMethodList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(EventReceive.class.getCanonicalName());
        return annotations;
    }

    /**
     * 这个函数一般不用动，返回SourceVersion.latestSupported();就好
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mElementUtils = processingEnv.getElementUtils();

        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(EventReceive.class)) {
            if (annotatedElement.getKind() != ElementKind.METHOD) {
                continue;
            }
            ExecutableElement methodElement = (ExecutableElement) annotatedElement;
            TypeElement classElement = (TypeElement) methodElement.getEnclosingElement();
            PackageElement packageElement = mElementUtils.getPackageOf(classElement);

            String fullClassName = classElement.getQualifiedName().toString();
            String className = classElement.getSimpleName().toString();
            String packageName = packageElement.getQualifiedName().toString();
            String methodName = methodElement.getSimpleName().toString();
            int eventId = methodElement.getAnnotation(EventReceive.class).eventId();
            System.out.println("#EventProcessor:fullClassName="+fullClassName+",className="+className+",packageName="+packageName+",methodName="+methodName+",eventId="+eventId);
        }
        writeCode();
        return true;
    }

    private boolean isValidClass(EventAnnotatedClass annotatedClass) {

        return true;
    }


    private void writeCode() {

    }


}
