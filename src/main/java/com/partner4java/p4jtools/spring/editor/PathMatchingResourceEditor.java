package com.partner4java.p4jtools.spring.editor;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PathMatchingResourceEditor extends ResourceEditor {
	public PathMatchingResourceEditor() {
		super(new PathMatchingResourcePatternResolver(), new StandardEnvironment());
	}
}
