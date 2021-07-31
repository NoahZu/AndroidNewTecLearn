package io.github.noahzu.annotation_processor;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class ClassInfoBean {
    private TypeElement typeElement;
    private String className;
    private String fullClassName;
    private String packageName;

    public ClassInfoBean(TypeElement typeElement,String packageName) {
        this.className = typeElement.getSimpleName().toString();
        this.fullClassName = typeElement.getQualifiedName().toString();
        this.packageName = packageName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
