package com.stats;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;
import com.stats.RotatedLabel.Direction;

public class LLH_Interface {
	String[][] array;

	protected void initUI() throws IOException {
		int type = WordCloudCreator.cloudType;

		array = ReadCSV.readCSV("llhCloud/data.csv");
		String[] words = new String[array.length];
		double[] size = new double[array.length];
		int[] freq = new int[array.length];
		for (int row = 0; row < array.length; row++) {
			words[row] = array[row][0];
			size[row] = Double.parseDouble(array[row][1].trim().substring(0, array[row][1].trim().indexOf("_") - 1));
			freq[row] = Integer.parseInt(array[row][1].trim().substring(array[row][1].trim().indexOf("_") + 1));

		}

		final JFrame frame = new JFrame("LL Word Cloud");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		Cloud cloud = new Cloud();
		if(type == 1){
		for (int i = 0; i < words.length; i++) {
			for (double x = size[i]; x > 0; x--) {
				cloud.addTag(words[i]);
			}
		}
		}
		else{
			for (int i = 0; i < words.length; i++) {
				for (double x = freq[i]; x > 0; x--) {
					cloud.addTag(words[i]);
				}
			}	
		}
		for (Tag tag : cloud.tags()) {
			int rand = randomWithRange(1, 75);
			final RotatedLabel label = new RotatedLabel(tag.getName());
			label.setOpaque(false);
			if (rand > -1 && rand <= 25)
				label.setDirection(Direction.VERTICAL_DOWN);
			if (rand > 25 && rand <= 50)
				label.setDirection(Direction.VERTICAL_UP);

			DecimalFormat f = new DecimalFormat("##.0000");
			label.setToolTipText("<html><p>LL = " + f.format(size[Arrays.asList(words).indexOf(tag.getName())]) + "</p> <p>Freq = "
					+ freq[Arrays.asList(words).indexOf(tag.getName())] + "</p></html>");
			label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10 + 10));
			float hue = (float) Math.random();
			int rgb = Color.HSBtoRGB(hue, 0.9f, 0.6f);
			Color color = new Color(rgb);
			label.setForeground(color);

			label.setTransferHandler(new TransferHandler("text"));
			MouseListener listener = new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					JComponent comp = (JComponent) me.getSource();
					TransferHandler handler = comp.getTransferHandler();
					handler.exportAsDrag(comp, me, TransferHandler.COPY);
				}
			};
			label.addMouseListener(listener);
			panel.add(label);
		}

		/*
		 * JButton reload = new JButton("Reload"); reload.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * frame.dispose(); String[] args = null; LLH_Interface.main(args); ; }
		 * });
		 */

		panel.setBackground(Color.WHITE);
		frame.add(panel);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

	public static void main(int type) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new LLH_Interface().initUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// random number between two values
	int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

}