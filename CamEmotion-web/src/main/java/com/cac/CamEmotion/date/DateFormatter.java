package com.cac.CamEmotion.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date>{

	@Override
	public String print(Date arg0, Locale arg1) {
		return null;
	}

	@Override
	public Date parse(String text, Locale arg1) throws ParseException{
		SimpleDateFormat format;
		Date date = null;
		
		// 19位长度的日期型
		if(Pattern.matches("\\d{4}-\\d{2}-\\d{2}( |T)\\d{2}:\\d{2}:\\d{2}", text)){
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = format.parse(text);
		}else if(Pattern.matches("\\d{4}-\\d{2}-\\d{2}", text)){// 短日期型
			format = new SimpleDateFormat("yyyy-MM-dd");  
			date = format.parse(text);
		}
		
		return date;
	}
}
