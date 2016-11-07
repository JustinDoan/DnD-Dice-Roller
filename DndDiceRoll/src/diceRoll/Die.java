package diceRoll;

public class Die {
	private int number;
	private String dieName;
	private String dieLabel;
	private int rolls;
	
	public Die(int number, String dieName, String dieLabel, int rolls){
		this.number = number;
		this.setDieName(dieName);
		this.dieLabel = dieLabel;
		this.setRolls(rolls);
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getDieName() {
		return dieName;
	}


	public void setDieName(String dieName) {
		this.dieName = dieName;
	}


	public String getDieLabel() {
		return dieLabel;
	}


	public void setDieLabel(String dieLabel) {
		this.dieLabel = dieLabel;
	}


	public int getRolls() {
		return rolls;
	}


	public void setRolls(int rolls) {
		this.rolls = rolls;
	}





	
	
	
	
	
	
	
	
}
