package Models;

public class Tower {
	private String name;
	private int[][] matrix;
	private int[] towerWidthXPoint;
	private int[] towerHightYPoint;
	private int[] towerColumnWidthPoint;
	private int currentHight;
	
	public Tower(String name,int[][] matrex,int currentHight,int[] towerWidthXPoint, int[] towerHightYPoint,int[] towerColumnWidthPoint) {
		super();
		this.name=name;
		this.matrix=matrex;
		this.currentHight=currentHight;
		this.towerWidthXPoint = towerWidthXPoint;
		this.towerHightYPoint = towerHightYPoint;
		this.towerColumnWidthPoint = towerColumnWidthPoint;
	}
	
	public String getName() {
		return name;
	}
	public int[][] getMatrix() {
		return matrix;
	}

	public int[] getTowerWidthXPoint() {
		return towerWidthXPoint;
	}

	public int[] getTowerHightYPoint() {
		return towerHightYPoint;
	}

	public int[] getTowerColumnWidthPoint() {
		return towerColumnWidthPoint;
	}
	
	public int getCurrentHight() {
		return currentHight;
	}
	
	public void setCurrentHight(int currentHight) {
		this.currentHight = currentHight;
	}
	
	public void resetCurrentHight() {
		this.currentHight=this.towerHightYPoint[0];
	}

}
