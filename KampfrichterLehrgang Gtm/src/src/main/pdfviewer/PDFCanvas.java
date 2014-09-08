/*
 * Copyright (c) 2008, intarsys consulting GmbH
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Public License as published by the 
 * Free Software Foundation; either version 3 of the License, 
 * or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 */
package src.main.pdfviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.JPanel;

import de.intarsys.cwt.awt.environment.CwtAwtGraphicsContext;
import de.intarsys.cwt.environment.IGraphicsContext;
import de.intarsys.pdf.content.CSContent;
import de.intarsys.pdf.parser.COSLoadException;
import de.intarsys.pdf.pd.PDDocument;
import de.intarsys.pdf.pd.PDPage;
import de.intarsys.pdf.pd.PDPageTree;
import de.intarsys.pdf.platform.cwt.rendering.CSPlatformRenderer;
import de.intarsys.tools.locator.ILocator;

/**
 * A very simple PDF rendering panel.
 * 
 */
public class PDFCanvas extends JPanel {

	private PDDocument doc;

	private PDPage page;

	private Rectangle2D pageRect;

	private PDPageTree pageTree;

	private double scale = 1;

	public PDDocument getDoc() {
		return doc;
	}

	public PDPage getPage() {
		return page;
	}

	public int getPageCount() {
		return pageTree.getCount();
	}

	public int getPageIndex() {
		return getPage().getNodeIndex();
	}

	public PDPageTree getPageTree() {
		return pageTree;
	}

	public double getScale() {
		return scale;
	}

	@Override
	public void paint(Graphics g) {
		if (page == null) {
			return;
		}
		IGraphicsContext graphics = new CwtAwtGraphicsContext((Graphics2D) g);
		// setup user space
		AffineTransform imgTransform = graphics.getTransform();
		imgTransform.scale(getScale(), -getScale());
		imgTransform.translate(-pageRect.getMinX(), -pageRect.getMaxY());
		graphics.setTransform(imgTransform);
		graphics.setBackgroundColor(Color.WHITE);
		graphics.fill(pageRect);
		CSContent content = page.getContentStream();
		if (content != null) {
			CSPlatformRenderer renderer = new CSPlatformRenderer(null, graphics);
			renderer.process(content, page.getResources());
		}
	}

	public void selectFirstPage() {
		setPage(pageTree.getFirstPage());
	}

	public void selectLastPage() {
		setPage(pageTree.getLastPage());
	}

	public void selectNextPage() {
		PDPage newPage = getPage().getNextPage();
		if (newPage == null) {
			return;
		}
		setPage(newPage);
	}

	public void selectPage(int index) {
		PDPage page = pageTree.getPageAt(index);
		if (page != null) {
			setPage(page);
		}
	}

	public void selectPreviousPage() {
		PDPage newPage = getPage().getPreviousPage();
		if (newPage == null) {
			return;
		}
		setPage(newPage);
	}

	public void setDoc(PDDocument doc) {
		this.doc = doc;
		setPageTree(getDoc().getPageTree());
		setPage(getPageTree().getFirstPage());
	}

	public void setLocator(ILocator locator) throws IOException,
			COSLoadException {
		PDDocument tempDoc = PDDocument.createFromLocator(locator);
		setDoc(tempDoc);
	}

	protected void setPage(PDPage page) {
		this.page = page;
		pageRect = page.getCropBox().toNormalizedRectangle();
		updateModel();
	}

	protected void setPageTree(PDPageTree pageTree) {
		this.pageTree = pageTree;
	}

	public void setScale(double pScale) {
		this.scale = (float) pScale;
		updateModel();
	}

	protected void updateModel() {
		int width = (int) (pageRect.getWidth() * getScale());
		int height = (int) (pageRect.getHeight() * getScale());
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		repaint();
	}

}
