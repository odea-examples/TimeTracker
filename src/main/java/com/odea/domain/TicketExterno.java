package com.odea.domain;

public class TicketExterno {
	
	private int tipo;
	private int ticketID;
	
	
	public TicketExterno(int tipo, int ticketID) {
		this.tipo = tipo;
		this.ticketID = ticketID;
	}
	
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	
	
}
