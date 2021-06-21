package io.github.noahzu.annotation_processor;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class CodeWriter {
    ProcessingEnvironment processingEnv;

    public CodeWriter(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }


    public void writeCode(String code, String className, TypeElement typeElement) {
        JavaFileObject jfo;
        try {
            jfo = processingEnv.getFiler().createSourceFile(
                    className,
                    typeElement);
            Writer writer = jfo.openWriter();
            writer.write(code);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
