package com.baisc.animatorprocessor.core;

import com.baisc.animatorprocessor.Annotation;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * generateCode proxy
 */

public class GenerateProxy implements GenerateCode {

    private GenerateCode mGenerator;

    public GenerateProxy(GenerateCode generator) {
        mGenerator = generator;
    }


    @Override
    public boolean generate(String clazz, Map<String, List<Annotation>> annotations, ProcessingEnvironment environment) {
        return mGenerator.generate(clazz, annotations, environment);
    }
}
