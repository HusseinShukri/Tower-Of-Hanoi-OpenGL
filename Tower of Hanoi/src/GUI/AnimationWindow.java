package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import JOGL.hanoiSimulation;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnimationWindow {

	private JFrame frmTowerOfHanoi;
	private JTextField disksNumberField;
	private JPanel AnimationPanel;
	private JLabel StepsNumberLabel;
	private JLabel integerErrorLabel;
	private hanoiSimulation hanoiSimulation;
	private FPSAnimator animator;
	private JButton ResumeSimualationButton;
	private JButton PauseSimualationButton;
	private JLabel currentStepLabel;
	private JLabel currentStepsNumberLabel;
	private JButton stopButton;

	public JFrame getFrmTowerOfHanoi() {
		return frmTowerOfHanoi;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnimationWindow window = new AnimationWindow();
					window.frmTowerOfHanoi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AnimationWindow() {
		initialize();
	}

	private void initialize() {
		frmTowerOfHanoi = new JFrame();
		frmTowerOfHanoi.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		frmTowerOfHanoi.setTitle("Tower Of Hanoi");
		frmTowerOfHanoi.setBackground(Color.LIGHT_GRAY);
		frmTowerOfHanoi.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmTowerOfHanoi.setResizable(false);
		frmTowerOfHanoi.setBounds(100, 100, 934, 671);
		frmTowerOfHanoi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTowerOfHanoi.getContentPane().setLayout(null);

		AnimationPanel = new JPanel();
		AnimationPanel.setBackground(Color.WHITE);
		AnimationPanel.setBounds(10, 10, 900, 400);
		frmTowerOfHanoi.getContentPane().add(AnimationPanel);

		disksNumberField = new JTextField();
		disksNumberField.setBackground(SystemColor.menu);
		disksNumberField.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		disksNumberField.setToolTipText("");
		disksNumberField.setBounds(331, 458, 77, 40);
		frmTowerOfHanoi.getContentPane().add(disksNumberField);
		disksNumberField.setColumns(10);

		JLabel disksNumberLabel = new JLabel("Number of disks :");
		disksNumberLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		disksNumberLabel.setBounds(22, 455, 282, 40);
		frmTowerOfHanoi.getContentPane().add(disksNumberLabel);

		JButton StartButton = new JButton("Start");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = textToInt(disksNumberField.getText());
				animator.stop();
				if (num < 0 || num > 25) {
					integerErrorLabel.setText("Valed Range [3,25]");
					animator.stop();
				} else if (num >= 0) {
					integerErrorLabel.setText("");
					num = hanoiSimulation.setNumberOfDisks(num);
					StepsNumberLabel.setText(num + "");
					animator.start();
				}
			}
		});
		StartButton.setForeground(Color.DARK_GRAY);
		StartButton.setFont(new Font("Dialog", Font.BOLD, 30));
		StartButton.setBackground(Color.GREEN);
		StartButton.setBounds(22, 498, 271, 40);
		frmTowerOfHanoi.getContentPane().add(StartButton);

		JLabel lblNewLabel = new JLabel("Number of steps :");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		lblNewLabel.setBounds(474, 455, 282, 40);
		frmTowerOfHanoi.getContentPane().add(lblNewLabel);

		StepsNumberLabel = new JLabel("0");
		StepsNumberLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		StepsNumberLabel.setBounds(751, 458, 159, 40);
		frmTowerOfHanoi.getContentPane().add(StepsNumberLabel);

		integerErrorLabel = new JLabel("");
		integerErrorLabel.setBackground(Color.WHITE);
		integerErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		integerErrorLabel.setForeground(Color.RED);
		integerErrorLabel.setBounds(331, 508, 136, 28);
		frmTowerOfHanoi.getContentPane().add(integerErrorLabel);

		ResumeSimualationButton = new JButton("Resume");
		ResumeSimualationButton.setForeground(Color.DARK_GRAY);
		ResumeSimualationButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		ResumeSimualationButton.setBackground(Color.ORANGE);
		ResumeSimualationButton.setBounds(21, 548, 126, 28);
		ResumeSimualationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.resume();
			}
		});
		frmTowerOfHanoi.getContentPane().add(ResumeSimualationButton);
		
		PauseSimualationButton = new JButton("Pause");
		PauseSimualationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.pause();
			}
		});
		PauseSimualationButton.setForeground(Color.DARK_GRAY);
		PauseSimualationButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		PauseSimualationButton.setBackground(Color.ORANGE);
		PauseSimualationButton.setBounds(167, 548, 126, 28);
		frmTowerOfHanoi.getContentPane().add(PauseSimualationButton);
		
		currentStepLabel = new JLabel("Current step :");
		currentStepLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		currentStepLabel.setBounds(474, 518, 213, 40);
		frmTowerOfHanoi.getContentPane().add(currentStepLabel);
		
		currentStepsNumberLabel = new JLabel("0");
		currentStepsNumberLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		currentStepsNumberLabel.setBounds(697, 518, 213, 40);
		frmTowerOfHanoi.getContentPane().add(currentStepsNumberLabel);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.stop();
			}
		});
		stopButton.setForeground(Color.DARK_GRAY);
		stopButton.setFont(new Font("Dialog", Font.BOLD, 30));
		stopButton.setBackground(Color.RED);
		stopButton.setBounds(22, 586, 271, 40);
		frmTowerOfHanoi.getContentPane().add(stopButton);
		new Thread(() -> {
			JOGLSetup();
		}).start();
	}

	private void JOGLSetup() {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		final GLCapabilities capabilities = new GLCapabilities(profile);
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		AnimationPanel.add(glcanvas);
		animator = new FPSAnimator(glcanvas, 1, true);
		hanoiSimulation = new hanoiSimulation(currentStepsNumberLabel);
		hanoiSimulation.setAnimater(animator);
		glcanvas.addGLEventListener(hanoiSimulation);
		glcanvas.setSize(AnimationPanel.getWidth(), AnimationPanel.getHeight());
	}

	private int textToInt(String str) {
		if (isNumeric(str)) {
			return Integer.parseInt(str);
		} else {
			return 0;
		}
	}

	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
