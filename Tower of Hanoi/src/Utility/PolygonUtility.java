package Utility;

public class PolygonUtility {
	// first initial test is to check
		// Whiter the coordination is lay
		// inside the rang where the given point
		// could be inside the polygon or not
		public static boolean checkRang(int x, int y, int maxX, int maxY, int minX, int minY) {
			if (x < minX) {
				return false;
			}
			if (x > maxX) {
				return false;
			}
			if (y < minY) {
				return false;
			}
			if (y > maxY) {
				return false;
			}
			return true;
		}

		public static boolean isInside(int[][] Matrix, int x, int y) {
			return (numberOfIntersect(Matrix, x, y) % 2 == 0 ? false : true);
		}

		private static int numberOfIntersect(int[][] matrix, int x, int y) {//casting ray
			int intersectCount = 0;
			for (int i = 0; i < matrix.length; i++) {
				// x1 x2 y1 y2 from polygon
				int X1, Y1, X2, Y2;
				if (matrix.length - 1 == i) {
					X1 = matrix[i][0];
					Y1 = matrix[i][1];
					X2 = matrix[0][0];
					Y2 = matrix[0][1];
				} else {
					X1 = matrix[i][0];
					Y1 = matrix[i][1];
					X2 = matrix[i + 1][0];
					Y2 = matrix[i + 1][1];
				}
				// x3 y3 y4 from x,y x4 =0
				int X3 = x;
				int Y3 = y;
				int X4 = 0;
				int Y4 = y;
				if (isTwoLineIntersect(X1, X2, X3, X4, Y1, Y2, Y3, Y4)) {
					intersectCount++;
				} else {
				}
			}
			return intersectCount;
		}

		private static boolean isTwoLineIntersect(int X1, int X2, int X3, int X4, int Y1, int Y2, int Y3, int Y4) {
			float denominater = (X1 - X2) * (Y3 - Y4) - (Y1 - Y2) * (X3 - X4);
			if (denominater == 0) {
				// no cross both parallel
				return false;
			}
			float T = ((X1 - X3) * (Y3 - Y4) - (Y1 - Y3) * (X3 - X4)) / denominater;
			float U = -((X1 - X2) * (Y1 - Y3) - (Y1 - Y2) * (X1 - X3)) / denominater;
			// cross when 0 < t < 1 , u > 0
			if (0 < T && T < 1 && U > 0) {
				// CROSS
				return true;
			}
			return false;
		}
}
