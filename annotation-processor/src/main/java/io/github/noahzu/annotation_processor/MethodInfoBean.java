package io.github.noahzu.annotation_processor;

public class MethodInfoBean {
    String mMethodName;
    int mAnnotationId;

    public MethodInfoBean(String methodName, int annotationId) {
        this.mMethodName = methodName;
        this.mAnnotationId = annotationId;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String mMethodName) {
        this.mMethodName = mMethodName;
    }

    public int getAnnotationId() {
        return mAnnotationId;
    }

    public void setAnnotationId(int mAnnotationId) {
        this.mAnnotationId = mAnnotationId;
    }
}
