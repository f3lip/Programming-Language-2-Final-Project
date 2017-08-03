package br.imd.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Histogram {
	
	public String id;
	public Date date;
	public String pc;
	public String action;
	public String webPage;
	
	public Histogram(String id, String date, String pc, String action) {
		this.id = id;
		this.pc = pc;
		if(action.substring(0,4).equals("http")) {
			this.webPage = action;
			this.action = null;
		} else {
			this.action = action;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			this.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getHistogramStr(){
		/* Formato que a data será printada */
		SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
		
		if(this.action != null){
			return dateFormat.format(this.date) + " - " + this.id + " - " + this.pc + " - " + this.action;
		} else {
			return dateFormat.format(this.date) + " - " + this.id + " - " + this.pc + " - " + this.webPage;
		}
	}
		
	public String getHistogramStrByPeriod(Date startDate, Date endDate){
		/* Formato que a data será printada */
		SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
		if(startDate.before(this.date) && endDate.after(this.date)){
			if(this.action != null){
				return dateFormat.format(this.date) + " - " + this.id + " - " + this.pc + " - " + this.action;
			} else {
				return dateFormat.format(this.date) + " - " + this.id + " - " + this.pc + " - " + this.webPage;
			}
		}
		
		return null;
	}
}