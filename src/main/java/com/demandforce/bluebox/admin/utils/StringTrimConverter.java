package com.demandforce.bluebox.admin.utils;


import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringTrimConverter implements Converter<String,String> {
    public String convert(String s) {
    	System.out.print(StringUtils.trim(s));
        return StringUtils.trim(s);
    }
}
