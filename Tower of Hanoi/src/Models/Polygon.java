package Models;

import java.util.HashMap;

import com.jogamp.opengl.GL2;

import Utility.AlgoUtility;
import Utility.DrawingUtility;
import Utility.MatricesUtility;

public class Polygon implements IPolygon {
	private int[][] matrix;
	private float[] boundaryColor;
	private float[] fillColor;
	private int[] ranges;
	private HashMap<Key, float[]> pixelMap;

	public Polygon(int[][] matrix, float[] boundaryColor, float[] fillColor) {
		super();
		this.matrix = matrix;
		this.boundaryColor = boundaryColor;
		this.fillColor = fillColor;
		this.pixelMap = new HashMap<Key, float[]>();
		this.ranges = initValues();
	}

	private int[] initValues() {
		int[] ranges = new int[4];
		for (int[] point : matrix) {
			if (point[0] > ranges[0]) {
				ranges[0] = point[0];
			}
			if (point[0] < ranges[1]) {
				ranges[1] = point[0];
			}
			if (point[1] > ranges[2]) {
				ranges[2] = point[1];
			}
			if (point[1] < ranges[3]) {
				ranges[3] = point[1];
			}
		}
		return ranges;
	}

	@Override
	public void draw(GL2 gl) {
		AlgoUtility.drowLines_LOOP(gl, this.boundaryColor, this.matrix);
		if (this.pixelMap.size() == 0) {
			if (this.matrix.length > 3) {
				this.pixelMap=AlgoUtility.boundaryFill4EveryThreePoint_Queue(gl, boundaryColor, fillColor, matrix, ranges);
			}
		} else {
			DrawingUtility.pixelsDrow(gl, this.pixelMap);
		}
	}

	@Override
	public void ShiftX(int value) {
		matrix = MatricesUtility.shiftX(this.matrix, value);
		if (this.pixelMap.size() != 0) {
			this.pixelMap = MatricesUtility.pixelsShifte(this.pixelMap, value, 0);
		}
	}

	@Override
	public void ShiftY(int value) {
		matrix = MatricesUtility.shiftY(this.matrix, value);
		if (this.pixelMap.size() != 0) {
			this.pixelMap = MatricesUtility.pixelsShifte(this.pixelMap, 0, value);
		}
	}

	@Override
	public int[][] getMatrix() {
		return this.matrix;
	}

	@Override
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
}
