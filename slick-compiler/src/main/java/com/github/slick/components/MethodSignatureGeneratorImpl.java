package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.github.slick.Arg;
import com.github.slick.SlickProcessor;
import com.github.slick.SlickProcessor.ViewType;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */
public class MethodSignatureGeneratorImpl implements MethodSignatureGenerator {

    @Override
    public MethodSpec.Builder generate(String name, AnnotatedPresenter ap, TypeName returns) {
        List<ClassName> bounds = new ArrayList<>(2);
        bounds.add(ap.getViewInterface());
        if (ViewType.VIEW.equals(ap.getViewType()) || ViewType.DAGGER_VIEW.equals(ap.getViewType())) {
            bounds.add(SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER);
        }
        final TypeVariableName typeVariableName = TypeVariableName.get("T",
                ap.getViewType().className()).withBounds(bounds);
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariableName)
                .addParameter(typeVariableName, ap.getViewVarName())
                .addParameters(addExtraParameters(ap))
                .returns(returns);
    }

    protected Iterable<ParameterSpec> addExtraParameters(AnnotatedPresenter ap) {
        final List<ParameterSpec> list = new ArrayList<>(ap.getArgs().size());
        for (Arg arg : ap.getArgs()) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            list.add(paramBuilder.build());
        }
        return list;
    }
}
