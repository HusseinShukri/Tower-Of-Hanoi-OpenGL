package JOGL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import Models.Circle;
import Models.Polygon;
import Models.IPolygon;
import Models.Tower;
import Models.sloutionStep;
import Utility.ColorsUtility;

public class hanoiSimulation implements GLEventListener {

	private GL2 gl;
	private LinkedList<IPolygon> polygonList = new LinkedList<IPolygon>();
	private float[] backgroundColor = new float[] { 1, 1, 1 };
	private int[] panelSize = new int[2];
	private Tower[] towers = new Tower[3];
	private HashMap<Integer, Polygon> disks = new HashMap<Integer, Polygon>();
	private int disksNumber = 0;
	private boolean isfirstLoop = true;
	private Queue<sloutionStep> sloutionQueue = new LinkedList<>();
	private List<Circle> circles = new LinkedList<Circle>();
	private JLabel currentStepsNumberLabel;
	private int currentStepCounter = 0;
	private FPSAnimator animator;

	public hanoiSimulation(JLabel currentStepsNumberLabel) {
		this.currentStepsNumberLabel = currentStepsNumberLabel;
	}

	public void setAnimater(FPSAnimator animator) {
		this.animator = animator;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		panelSize[0] = drawable.getSurfaceWidth();
		panelSize[1] = drawable.getSurfaceHeight();
		gl = drawable.getGL().getGL2();
		gl.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		int[][] tower3 = new int[][] { new int[] { 800, 50 }, new int[] { 800, 70 }, new int[] { 760, 70 },
				new int[] { 760, 300 }, new int[] { 740, 300 }, new int[] { 740, 70 }, new int[] { 700, 70 },
				new int[] { 700, 50 } };

		int[][] tower2 = new int[][] { new int[] { 600, 50 }, new int[] { 600, 70 }, new int[] { 560, 70 },
				new int[] { 560, 300 }, new int[] { 540, 300 }, new int[] { 540, 70 }, new int[] { 500, 70 },
				new int[] { 500, 50 } };

		int[][] tower1 = new int[][] { new int[] { 400, 50 }, new int[] { 400, 70 }, new int[] { 360, 70 },
				new int[] { 360, 300 }, new int[] { 340, 300 }, new int[] { 340, 70 }, new int[] { 300, 70 },
				new int[] { 300, 50 } };

		towers[0] = new Tower("tower1", tower1, (tower1[2][1]), new int[] { tower1[1][0], tower1[6][0] },
				new int[] { tower1[2][1], tower1[3][1] }, new int[] { tower1[2][0], tower1[5][0] });

		towers[1] = new Tower("tower2", tower2, (tower2[2][1]), new int[] { tower2[1][0], tower2[6][0] },
				new int[] { tower2[2][1], tower2[3][1] }, new int[] { tower2[2][0], tower2[5][0] });

		towers[2] = new Tower("tower3", tower3, (tower3[2][1]), new int[] { tower3[1][0], tower3[6][0] },
				new int[] { tower3[2][1], tower3[3][1] }, new int[] { tower3[2][0], tower3[5][0] });

		polygonList.add(new Polygon(tower1, ColorsUtility.RED, ColorsUtility.RED));
		polygonList.add(new Polygon(tower2, ColorsUtility.RED, ColorsUtility.RED));
		polygonList.add(new Polygon(tower3, ColorsUtility.RED, ColorsUtility.RED));

		circles.add(new Circle(ColorsUtility.RandonColor(), 150, 320, 30, 20));
		circles.add(new Circle(ColorsUtility.RandonColor(), 120, 290, 30, 20));
		circles.add(new Circle(ColorsUtility.RandonColor(), 150, 270, 30, 20));
		circles.add(new Circle(ColorsUtility.RandonColor(), 120, 250, 30, 20));

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		gl.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION_MATRIX);
		gl.glLoadIdentity();
		gl.glOrtho(0, panelSize[0], 0, panelSize[1], -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

		for (IPolygon polygon : polygonList) {
			polygon.draw(gl);
		}
		if (isfirstLoop) {
			isfirstLoop = false;
		} else {
			currentMove();
			currentStepsNumberLabel.setText(this.currentStepCounter + "");
		}
		if (disks.size() != 0) {
			for (Integer key : disks.keySet()) {
				disks.get(key).draw(gl);
			}
		}
		for (Circle circle : circles) {
			circle.draw(gl, ColorsUtility.RandonColor());
		}
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void hanoi(int diskNumber, Tower source, Tower destination, Tower auxiliary) {
		if (diskNumber == 1) {
			sloutionQueue.add(new sloutionStep(diskNumberInList(diskNumber), source, destination));
		} else {
			hanoi(diskNumber - 1, source, auxiliary, destination);
			sloutionQueue.add(new sloutionStep(diskNumberInList(diskNumber), source, destination));
			hanoi(diskNumber - 1, auxiliary, destination, source);
		}
	}

	private void initGenerateDisks(int n) {
		// set height
		int[] hightPoints = towers[0].getTowerHightYPoint();
		int height = Math.abs(hightPoints[0] - hightPoints[1]) / n;
		if (height > 10) {
			height = 10;
		}
		int startingHight = towers[0].getCurrentHight();

		// set width
		int[] widthPoints = towers[0].getTowerWidthXPoint();
		int width = Math.abs(widthPoints[0] - widthPoints[1]);
		for (int i = 0; i < n; i++, startingHight += height, width += -2) {
			disks.put(i, new Polygon(generateDisks(widthPoints, width, startingHight, height), ColorsUtility.BLACK,
					ColorsUtility.RandonColorFromBasickColors()));
		}
		towers[0].setCurrentHight(startingHight);
	}

	private int[][] generateDisks(int[] widthPoints, int width, int startingHight, int hight) {
		int[][] matrix = new int[][] { new int[] { widthPoints[0] - width, startingHight },
				new int[] { widthPoints[0] - width, startingHight + hight },
				new int[] { widthPoints[1] + width, startingHight + hight },
				new int[] { widthPoints[1] + width, startingHight } };
		return matrix;
	}

	private void moveDisk(int diskNumber, Tower from, Tower to) {
		// x shifting
		int[] toXPoints = to.getTowerWidthXPoint();
		int[] fromXPoints = from.getTowerWidthXPoint();
		var disk = disks.get(diskNumber);
		disk.ShiftX(toXPoints[0] - fromXPoints[0]);

		// y shifting
		int[][] diskMatrix = disk.getMatrix();
		int[] diskYPoints = new int[] { diskMatrix[0][1], diskMatrix[1][1] };
		int hightShift = to.getCurrentHight() - diskYPoints[0];
		disk.ShiftY(hightShift);

		// stands current Height
		int height = Math.abs(diskMatrix[0][1] - diskMatrix[1][1]);
		from.setCurrentHight(from.getCurrentHight() - height);
		to.setCurrentHight(to.getCurrentHight() + height);
	}

	public void setBackGroundColor(float[] color) {
		this.backgroundColor = color;
	}

	private void currentMove() {
		if (!this.sloutionQueue.isEmpty()) {
			currentStepCounter++;
			var step = sloutionQueue.remove();
			moveDisk(step.getDiskNumber(), step.getFrom(), step.getTo());
		} else {
			animator.stop();
		}
	}

	private int diskNumberInList(int n) {
		return this.disksNumber - (n);
	}

	public int setNumberOfDisks(int n) {
		reset(n);
		new Thread(() -> {
			initGenerateDisks(this.disksNumber);
		}).start();
		hanoi(this.disksNumber, this.towers[0], this.towers[1], this.towers[2]);
		return this.sloutionQueue.size();
	}

	private void reset(int numberOfDisks) {
		for (Tower tower : towers) {
			tower.resetCurrentHight();
		}
		this.disks.clear();
		this.disksNumber = numberOfDisks;
		this.isfirstLoop = true;
		this.sloutionQueue.clear();
		this.currentStepCounter = 0;
	}
}
