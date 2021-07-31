package io.github.noahzu.annotation_processor;

import com.google.auto.service.AutoService;
import com.google.common.reflect.ClassPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import io.github.noahzu.annotation.EventDispatchAnnotation;
import io.github.noahzu.annotation.EventReceiveAnnotation;


@AutoService(Processor.class)
public class EventProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private CodeWriter codeWriter;
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 规定需要处理的注解
        return Collections.singleton(EventDispatchAnnotation.class.getCanonicalName());
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(EventDispatchAnnotation.class);
        for (Element element : elements) {

            StringBuilder codeBuilder = new StringBuilder();

            TypeElement typeElement = (TypeElement) element;
            PackageElement packageElement = elementUtils.getPackageOf(typeElement);
            String packageName = packageElement.getQualifiedName().toString();
            ClassInfoBean classInfo = new ClassInfoBean(typeElement,packageName);

            addCodeClassStart(codeBuilder,classInfo);
            List<MethodInfoBean> methodList = getMethodFromTypeElement(typeElement);
            Map<Integer,List<MethodInfoBean>> sortedMethodMap = sortMethodList(methodList);

            StringBuilder defaultCase = new StringBuilder();
            for (int eventId : sortedMethodMap.keySet()) {
                if (eventId == -1) {
                    defaultCase.append("            default: {")
                            .append("\n");
                    for (MethodInfoBean methodInfoBean : sortedMethodMap.get(eventId)) {
                        defaultCase.append(String.format(Locale.CHINA,"                %s(packet);",methodInfoBean.getMethodName()))
                                .append("\n");
                    }
                    defaultCase.append("           }")
                            .append("\n");

                } else {
                    codeBuilder.append(String.format(Locale.CHINA,"            case %d: {",eventId))
                            .append("\n");
                    for (MethodInfoBean methodInfoBean : sortedMethodMap.get(eventId)) {
                        codeBuilder.append(String.format(Locale.CHINA,"                %s(packet);",methodInfoBean.getMethodName()))
                                .append("\n");
                    }
                    codeBuilder.append("           }")
                            .append("\n");
                }

            }
                codeBuilder
                    .append("\n")
                    .append(defaultCase)
                    .append("        }")
                    .append("\n")
                    .append("   }")
                    .append("\n")
                    .append("}")
                    .append("\n");

            codeWriter.writeCode(codeBuilder.toString(),classInfo.getFullClassName() + "Proxy",typeElement);
        }
        return true;
    }

    private void addCodeClassStart(StringBuilder codeBuilder,ClassInfoBean classInfo) {
        codeBuilder
                .append(String.format("package %s;",classInfo.getPackageName()))
                .append("\n")
                .append("\n")
                .append(String.format("import %s;",classInfo.getFullClassName()))
                .append("\n")
                .append("\n")
                .append(String.format(Locale.CHINA,"public final class %s extends %s {",classInfo.getClassName()+"Proxy",classInfo.getClassName()))
                .append("\n")
                .append("   public void dispatch(io.github.noahzu.androidnewteclearn.MessagePacket packet) {")
                .append("\n")
                .append("       int eventId = packet.getEventId();")
                .append("\n")
                .append("       switch(eventId) {")
                .append("\n");
    }

    private List<MethodInfoBean> getMethodFromTypeElement(TypeElement typeElement) {
        List<MethodInfoBean> methodList = new ArrayList<>();
        List<? extends Element> members = elementUtils.getAllMembers(typeElement);
        for (Element item : members) {
            if (!(item instanceof ExecutableElement)) {
                continue;
            }
            ExecutableElement executableElement = (ExecutableElement)item;
            EventReceiveAnnotation diView = item.getAnnotation(EventReceiveAnnotation.class);
            if (diView == null){
                continue;
            }
            int eventId = diView.eventId();
            String methodName = executableElement.getSimpleName().toString();

            methodList.add(new MethodInfoBean(methodName,eventId));
        }
        return methodList;
    }

    private Map<Integer,List<MethodInfoBean>> sortMethodList(List<MethodInfoBean> methodList) {
        if (methodList == null || methodList.size() == 0) {
            return null;
        }

        Map<Integer,List<MethodInfoBean>> sortedMethodMap = new HashMap<>();

        for (MethodInfoBean method : methodList) {
            int eventId = method.getAnnotationId();
            List<MethodInfoBean> eventList = sortedMethodMap.get(eventId);
            if (eventList == null) {
                eventList = new ArrayList<>();
                sortedMethodMap.put(eventId,eventList);
            }
            eventList.add(method);
        }

        return sortedMethodMap;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        codeWriter = new CodeWriter(processingEnv);
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

}