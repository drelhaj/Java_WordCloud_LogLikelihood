/*
 * Copyright 2016 (C) Dr Mahmoud El-Haj
 * 
 * Created on : Feb-2016
 * Author     : drelhaj (https://github.com/drelhaj/)
 */

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
/**
 * create java word clouds maker using two arrays (first column containing keywords, second containing size (freq or LLH).
 * The method uses JAMESLIC method to rotate java labels, which I reused to give the word cloud a better lookd and feel
 * @author elhaj
 *
 */
public class WordCloudMaker {
	static String[] words;
	static double[] size;
	static String jFrameTitle;
/**
 * creates a word cloud using random colours and renders the size which I advise to tune down a bit if you expect
 * larger values in the double array.
 * @throws IOException
 */
	protected void initUI() throws IOException {


		final JFrame frame = new JFrame(jFrameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		Cloud cloud = new Cloud();
			for (int i = 0; i < words.length; i++) {
				for (double x = size[i]; x > 0; x--) {
					cloud.addTag(words[i]);
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
			label.setToolTipText("<html><p>Value = " + f.format(size[Arrays.asList(words).indexOf(tag.getName())])
					+ "</p></html>");
			label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10 + 10));
			float hue = (float) Math.random();
			int rgb = Color.HSBtoRGB(hue, 0.9f, 0.6f);
			Color color = new Color(rgb);
			label.setForeground(color);

			label.setTransferHandler(new TransferHandler("foreground"));
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
		 * optional JButton to refresh the frame to recolour the words.
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
/**
 * main method needed to run and create word clouds, a simple method that takes an array of word strings and another array
 * for those words weight (size, frequency or LLH) values, which gives a better representation than the random font size
 * word clouds online. The word cloud does not repeat words which makes it very efficient.
 * @param wordsArray
 * @param sizeArray
 * @param frameTitle
 */
	public static void main(final String[] wordsArray, final double[] sizeArray, final String frameTitle) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					words = wordsArray;
					size = sizeArray;
					jFrameTitle = frameTitle;
					new WordCloudMaker().initUI();
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