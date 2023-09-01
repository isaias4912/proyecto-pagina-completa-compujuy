/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.reportes.impl;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JEditorPane;

/**
 *
 * @author rafa
 */
public class HtmlImageGeneratorNS {

	private JEditorPane editorPane;
	static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	public HtmlImageGeneratorNS() {
		editorPane = createJEditorPane();
	}

	public ComponentOrientation getOrientation() {
		return editorPane.getComponentOrientation();
	}

	public void setOrientation(ComponentOrientation orientation) {
		editorPane.setComponentOrientation(orientation);
	}

	public Dimension getSize() {
		return editorPane.getSize();
	}

	public void setSize(Dimension dimension) {
		editorPane.setSize(dimension);
	}

//	public void loadUrl(URL url) {
//		try {
//			editorPane.setPage(url);
//		} catch (IOException e) {
//			throw new RuntimeException(String.format("Exception while loading %s", url), e);
//		}
//	}
//
//	public void loadUrl(String url) {
//		try {
//			editorPane.setPage(url);
//		} catch (IOException e) {
//			throw new RuntimeException(String.format("Exception while loading %s", url), e);
//		}
//	}

	public void loadHtml(String html) {
		editorPane.setText(html);
		onDocumentLoad();
	}

	public void saveAsImage(String file) {
		saveAsImage(new File(file));
	}

	public void saveAsImage(File file) {

		BufferedImage img = getBufferedImage();
		try {
			final String formatName = FormatNameUtil.formatForFilename(file.getName());
			ImageIO.write(img, formatName, file);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
		}
	}

	protected void onDocumentLoad() {
	}

	public Dimension getDefaultSize() {
		return DEFAULT_SIZE;
	}

	public BufferedImage getBufferedImage() {
		Dimension prefSize = editorPane.getPreferredSize();
		BufferedImage img = new BufferedImage(prefSize.width, editorPane.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = img.getGraphics();
		editorPane.setSize(prefSize);
		editorPane.paint(graphics);
		return img;
	}

	protected JEditorPane createJEditorPane() {
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setSize(getDefaultSize());
		editorPane.setEditable(false);
		CustomToolKit kit = new CustomToolKit();
		editorPane.setEditorKit(kit);
		editorPane.setContentType("text/html; charset=UTF-8");
		editorPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("page")) {
					onDocumentLoad();
				}
			}
		});
		return editorPane;
	}
}
