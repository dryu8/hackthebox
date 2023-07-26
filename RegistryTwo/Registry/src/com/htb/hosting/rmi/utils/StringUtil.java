package com.htb.hosting.rmi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
  public static String substr(String input, String start, String end) {
    return substr(input, start, end, 32);
  }
  
  public static String substr(String input, String start, String end, int flags) {
    String reg = start + "(.*?)" + end;
    if (input != null && start != null && end != null) {
      Pattern pattern = Pattern.compile(reg, flags);
      Matcher matcher = pattern.matcher(input);
      if (matcher.find())
        return matcher.group(1); 
    } 
    return null;
  }
}
