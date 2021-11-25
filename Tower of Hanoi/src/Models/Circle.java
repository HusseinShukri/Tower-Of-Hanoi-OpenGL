package Models;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;

import Utility.AlgoUtility;
import Utility.DrawingUtility;

public class Circle {
	private int centerX;
	private int centerY;
	private int radius;
	private List<List<int[][]>> points;
	private float[] color;
	private int layers;

	public Circle(float[] color, int centerX, int centerY, int radius, int layers) {
		super();
		this.color = color;
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.layers = layers;
		this.points = new LinkedList<List<int[][]>>();
		new Thread(() -> {
			this.points = initPoints(this.layers);
		}).start();
	}

	public Circle(float[] color, int centerX, int centerY, int radius) {
		super();
		this.color = color;
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.layers = 1;
		new Thread(() -> {
			this.points = initPoints(this.layers);
		}).start();
	}

	private List<List<int[][]>> initPoints(int layers) {
		List<List<int[][]>> points = new LinkedList<List<int[][]>>();
		for (int i = 0; i < layers; i++) {
			points.add(AlgoUtility.midPointCircle(this.centerX, this.centerY, radius - i));
		}
		return points;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getRadius() {
		return radius;
	}

	public List<List<int[][]>> getPoints() {
		return points;
	}

	public void draw(GL2 gl) {
		for (List<int[][]> list : this.points) {
			for (int[][] is : list) {
				for (int[] is2 : is) {
					DrawingUtility.setPixelColor(gl, this.color, is2[0], is2[1]);
				}
			}
		}
	}

	public void draw(GL2 gl, float[] color) {
		for (List<int[][]> list : this.points) {
			for (int[][] is : list) {
				for (int[] is2 : is) {
					DrawingUtility.setPixelColor(gl, color, is2[0], is2[1]);
				}
			}
		}
	}
}
