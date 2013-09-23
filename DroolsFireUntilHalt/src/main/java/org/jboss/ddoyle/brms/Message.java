package org.jboss.ddoyle.brms;

public class Message {
	
	private String name;
	
	private String text;
	
	public Message(String name, String text) {
		this.setName(name);
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
