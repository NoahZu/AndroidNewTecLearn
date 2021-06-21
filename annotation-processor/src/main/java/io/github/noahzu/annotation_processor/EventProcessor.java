package io.github.noahzu.annotation_processor;

import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
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

            // 判断是否Class
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getSimpleName().toString();
            String fullClassName = typeElement.getQualifiedName().toString();
            PackageElement packageElement = elementUtils.getPackageOf(typeElement);
            String packageName = packageElement.getQualifiedName().toString();

            codeBuilder
                    .append(String.format("package %s;",packageName))
                    .append("\n")
                    .append("\n")
                    .append(String.format("import %s;",fullClassName))
                    .append("\n")
                    .append("\n")
                    .append(String.format(Locale.CHINA,"public final class %s extends %s {",className+"Proxy",className))
                    .append("\n")
                    .append("   public void dispatch(io.github.noahzu.androidnewteclearn.MessagePacket packet) {")
                    .append("\n")
                    .append("       int eventId = packet.getEventId();")
                    .append("\n")
                    .append("       switch(eventId) {")
                    .append("\n");;

            List<? extends Element> members = elementUtils.getAllMembers(typeElement);


            StringBuilder defaultCase = new StringBuilder();


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

                if (eventId == -1) {
                    defaultCase
                            .append("            default: {")
                            .append("\n")
                            .append(String.format(Locale.CHINA,"                %s(packet);",methodName))
                            .append("\n")
                            .append("           }")
                            .append("\n");
                } else {
                    codeBuilder
                            .append(String.format(Locale.CHINA,"            case %d: {",eventId))
                            .append("\n")
                            .append(String.format(Locale.CHINA,"                %s(packet);",methodName))
                            .append("\n")
                            .append("           }")
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

            codeWriter.writeCode(codeBuilder.toString(),fullClassName + "Proxy",typeElement);
        }
        return true;
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