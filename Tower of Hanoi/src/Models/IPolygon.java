package Models;

import com.jogamp.opengl.GL2;

public interface IPolygon {
	void draw(GL2 gl);
	void ShiftX(int value);
	void ShiftY(int value);
	int[][] getMatrix();
	void  setMatrix(int[][]matrix);
}
