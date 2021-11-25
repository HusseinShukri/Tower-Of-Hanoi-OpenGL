package Models;

public class sloutionStep {
	private int diskNumber;
	private Tower from;
	private Tower to;
	
	public sloutionStep(int diskNumber, Tower from, Tower to) {
		super();
		this.diskNumber = diskNumber;
		this.from = from;
		this.to = to;
	}

	public int getDiskNumber() {
		return diskNumber;
	}

	public Tower getFrom() {
		return from;
	}

	public Tower getTo() {
		return to;
	}
}
