package Utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jogamp.opengl.GL2;

import Utility.PolygonUtility;
import Models.Key;

public class AlgoUtility {

	public static void drowLines_STRIP(GL2 gl, float[] rgb, int[][] matrix) {
		for (int i = 0; i < matrix.length - 1; i++) {
			AlgoUtility.bressenham(gl, rgb, matrix[i][0], matrix[i][1], matrix[i + 1][0], matrix[i + 1][1]);
		}
	}

	public static void drowLines_LOOP(GL2 gl, float[] rgb, int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			if (i == (matrix.length - 1)) {
				AlgoUtility.bressenham(gl, rgb, matrix[i][0], matrix[i][1], matrix[0][0], matrix[0][1]);
			} else {
				AlgoUtility.bressenham(gl, rgb, matrix[i][0], matrix[i][1], matrix[i + 1][0], matrix[i + 1][1]);
			}
		}
	}

	public static void bressenham(GL2 gl, float[] color, int xa, int ya, int xb, int yb) {
		int dx = Math.abs(xa - xb);
		int dy = Math.abs(ya - yb);
		int p = 2 * dy - dx;
		int towDy = 2 * dy;
		int towDyDx = 2 * (dy - dx);
		int x, y;
		if (xa != xb) {
			int xEnd;
			if (xa > xb) {
				x = xb;
				y = yb;
				xEnd = xa;
			} else {
				x = xa;
				y = ya;
				xEnd = xb;
			}
			DrawingUtility.setPixelColor(gl, color, x, y);
			while (x < xEnd) {
				x += 1;
				if (p < 0) {
					p += towDy;
				} else {
					y += 1;
					p += towDyDx;
				}
				DrawingUtility.setPixelColor(gl, color, x, y);
			}
		} else {
			int yEnd;
			if (ya > yb) {
				x = xb;
				y = yb;
				yEnd = ya;
			} else {
				x = xa;
				y = ya;
				yEnd = yb;
			}
			DrawingUtility.setPixelColor(gl, color, x, y);
			while (y < yEnd) {
				y += 1;
				DrawingUtility.setPixelColor(gl, color, x, y);
			}
		}
	}

	public static List<int[][]> midPointCircle(int xCenter, int yCenter, int radius) {
		List<int[][]> points = new LinkedList<int[][]>();
		int x = 0;
		int y = radius;
		int p = 1 - radius;
		points.add(circlePlotePoints(xCenter, yCenter, x, y));
		while (x < y) {
			x++;
			if (p < 0) {
				p += 2 * x + 1;
			} else {
				y -= 1;
				p += 2 * (x - y) + 1;
			}
			points.add(circlePlotePoints(xCenter, yCenter, x, y));
		}
		return points;
	}

	public static int[][] circlePlotePoints(int xCenter, int yCenter, int x, int y) {
		return new int[][] { new int[] { xCenter + x, yCenter + y }, new int[] { xCenter - x, yCenter + y },
				new int[] { xCenter + x, yCenter - y }, new int[] { xCenter - x, yCenter - y },

				new int[] { xCenter + y, yCenter + x }, new int[] { xCenter - y, yCenter + x },
				new int[] { xCenter + y, yCenter - x }, new int[] { xCenter - y, yCenter - x }, };
	}

	public static HashMap<Key, float[]>  boundaryFill4EveryThreePoint_Queue(GL2 gl, float[] boundaryColor, float[] fillColor,
			int[][] matrix, int[] ranges) {
		HashMap<Key, float[]> pixelMap = new HashMap<Key, float[]>();
		int[][] tringle = null;
		for (int i = 0; i < matrix.length - 2; i++) {
			tringle = new int[][] { matrix[i], matrix[i + 1], matrix[i + 2] };
			int[] point = DrawingUtility.startingPoint(tringle);
			pixelMap.putAll(AlgoUtility.boundaryFill4_queq(gl, boundaryColor, fillColor, matrix, pixelMap, ranges, point[0], point[1]));
		}
		return pixelMap;
	}

	public static HashMap<Key, float[]> boundaryFill4_queq(GL2 gl, float[] boundaryColor, float[] fillColor,
			int[][] matrix, HashMap<Key, float[]> pixelMap, int[] ranges, int x, int y) {
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(new int[] { x, y });
		while (!q.isEmpty()) {
			var n = q.remove();
			if (PolygonUtility.checkRang(n[0], n[1], ranges[0], ranges[2], ranges[1], ranges[3])) {
				if (PolygonUtility.isInside(matrix, n[0], n[1])) {
					float[] pixelColor = DrawingUtility.getPixelColor(gl, n[0], n[1]);
					if (!MatricesUtility.isArraysEqual(pixelColor, boundaryColor)
							&& !MatricesUtility.isArraysEqual(pixelColor, fillColor)) {
						pixelMap.put(new Key(n[0], n[1]), fillColor);
						DrawingUtility.setPixelColor(gl, fillColor, n[0], n[1]);
						if (!pixelMap.containsKey(new Key(n[0] + 1, n[1])))
							q.add(new int[] { n[0] + 1, n[1] });
						if (!pixelMap.containsKey(new Key(n[0], n[1] + 1)))
							q.add(new int[] { n[0], n[1] + 1 });
						if (!pixelMap.containsKey(new Key(n[0] - 1, n[1])))
							q.add(new int[] { n[0] - 1, n[1] });
						if (!pixelMap.containsKey(new Key(n[0], n[1] - 1)))
							q.add(new int[] { n[0], n[1] - 1 });
						if (!pixelMap.containsKey(new Key(n[0] + 1, n[1] + 1)))
							q.add(new int[] { n[0] + 1, n[1] + 1 });
						if (!pixelMap.containsKey(new Key(n[0] - 1, n[1] + 1)))
							q.add(new int[] { n[0] - 1, n[1] + 1 });
						if (!pixelMap.containsKey(new Key(n[0] + 1, n[1] - 1)))
							q.add(new int[] { n[0] + 1, n[1] - 1 });
						if (!pixelMap.containsKey(new Key(n[0] - 1, n[1] - 1)))
							q.add(new int[] { n[0] - 1, n[1] - 1 });
					}
				}
			}
		}
		return pixelMap;
	}
}
