package com.baisc.animatorprocessor.core;

import com.baisc.animatorprocessor.Annotation;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * 代码生成.
 */

public interface GenerateCode {

    /**
     * 构建代码.
     *
     * @param clazz 添加注解的外部类全名
     * @param annotations 注解数据
     * @param environment
     * @return 生成类文件成功
     */
    boolean generate(String clazz,  Map<String, List<Annotation>> annotations,
            ProcessingEnvironment environment);
}
