package com.komoot.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class MasterBean {
	
	public static String[] getHeaders(){
		List<String> fieldNames = getFieldNames();
		return fieldNames.stream().toArray(String[]::new);
	}

	private static List<String> getFieldNames() {
		Field[] fields = LanguageBean.class.getDeclaredFields();
		List<String> fieldNames = new ArrayList<>();
		for (Field field : fields)
			fieldNames.add(field.getName());
		return fieldNames;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
