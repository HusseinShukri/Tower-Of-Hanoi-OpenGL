package Utility;

import java.util.Arrays;
import java.util.HashMap;

import Models.Key;

public class MatricesUtility {

	public static boolean isArraysEqual(float[] first, float[] secound) {
		boolean result = false;
		for (int i = 0; i < first.length; i++) {
			if (first[i] != secound[i]) {
				break;
			}
			if (i + 1 == first.length) {
				result = true;
			}
		}
		return result;
	}

	public static boolean isArraysEqual2(float[] first, float[] secound) {
		return Arrays.equals(first, secound);
	}

	public static int[][] shiftX(int[][] matrix, int value) {
		for (int[] is : matrix) {
			is[0] += value;
		}
		return matrix;
	}

	public static int[][] shiftY(int[][] matrix, int value) {
		for (int[] is : matrix) {
			is[1] += value;
		}
		return matrix;
	}

	public static HashMap<Key, float[]> pixelsShifte(HashMap<Key, float[]> pixelMap, int xShift, int yShift) {
		var newPixelMap = new HashMap<Key, float[]>();
		for (Key key : pixelMap.keySet()) {
			int x = key.getX() + xShift;
			int y = (key.getY() + yShift);
			newPixelMap.put(new Key(x, y), pixelMap.get(key));
		}
		return newPixelMap;
	}
	
}
