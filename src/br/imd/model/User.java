package br.imd.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {
	
	private String name;
	private String id;
	private String domain;
	private String email;
	private String role;
	private ArrayList<Histogram> histogram;
	
	/*
	 * Construtor
	 */
	public User(String name, String id, String domain, String email, String role) {
		this.name = name;
		this.id = id;
		this.domain = domain;
		this.email = email;
		this.role = role;
		this.histogram = new ArrayList<Histogram>();
	}
	
	public void addHistogram(String[] data) {
		Histogram h = new Histogram(data[0], data[1], data[3], data[4]);
		this.histogram.add(h);
	}
	
	/*
	 * Getters
	 */
	public String getName() {   return this.name;}
	public String getId() {     return this.id;}
	public String getDomain() { return this.domain;}
	public String getEmail() {  return this.email;}
	public String getRole() {   return this.role;}
	public String getHistogram(){
		String histogram = "";
		
		for(Histogram h : this.histogram){
			histogram += h.getHistogramStr() + "\n";
		}
		
		if(histogram.isEmpty()){
			histogram = "Não há registros para este usuário";
		}
		
		return histogram;
	}
	
	public String getHistogramByPeriod(String start, String end){
		Date startDate = null;
		Date endDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			startDate = format.parse(start);
			endDate = format.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String histogram = "";
		for(Histogram h : this.histogram){
			String current = h.getHistogramStrByPeriod(startDate, endDate);
			if(current != null) {
				histogram += current + "\n";
			}
		}
		
		if(histogram.isEmpty()){
			histogram = "Não há registros para o período";
		}
		
		return histogram;
	}
	
	public String[] getDevices() {
		ArrayList<String> devices = new ArrayList<String>();
		for(int i = 0; i < histogram.size(); i++) {
			if(!devices.contains(this.histogram.get(i).pc)){
				String temp = this.histogram.get(i).pc;
				devices.add(temp);
			}
		}
		String[] array = new String[devices.size()];
		array = devices.toArray(array);
		return array;
	}
	
	public String[] getWebPages() {
		ArrayList<String> webPage = new ArrayList<String>();
		for(int i = 0; i < histogram.size(); i++) {
			if(this.histogram.get(i).webPage != null) {
				if(!webPage.contains(this.histogram.get(i).webPage)) {
					String temp = this.histogram.get(i).webPage;
					webPage.add(temp);
				}
			}
		}
		String[] array = new String[webPage.size()];
		array = webPage.toArray(array);
		return array;
	}
}