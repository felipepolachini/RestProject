package br.sp.fpiandoli.utils;

import static java.util.Calendar.*;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String getDate(Integer qtdeDias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, qtdeDias);
		
		return getDataFormat(cal.getTime());
	}
	
	public static String getDataFormat(Date data) {
		DateFormat format =new SimpleDateFormat("dd/MM/yyyy");
		return format.format(data);
	}
}
