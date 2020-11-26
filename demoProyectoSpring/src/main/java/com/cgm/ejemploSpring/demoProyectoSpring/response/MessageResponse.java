package com.cgm.ejemploSpring.demoProyectoSpring.response;

public class MessageResponse {
	private String message;
	private int status;

	public MessageResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
