package com.partner4java.p4jtools.image;

import java.io.Serializable;

/**
 * 图片大小
 * 
 *
 */
public class ImgSize implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 图片宽度 */
	private int width;

	/** 图片高度 */
	private int height;

	/** 中图宽度 */
	private int smallWidth;

	/** 中图高度 */
	private int smallHeight;

	/** 小图宽度 */
	private int minWidth;

	/** 小图高度 */
	private int minHeight;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void setSmallSize(int w, int h) {
		this.smallWidth = w;
		this.smallHeight = h;
	}

	public void setMinSize(int w, int h) {
		this.minWidth = w;
		this.minHeight = h;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSmallWidth() {
		return smallWidth;
	}

	public void setSmallWidth(int smallWidth) {
		this.smallWidth = smallWidth;
	}

	public int getSmallHeight() {
		return smallHeight;
	}

	public void setSmallHeight(int smallHeight) {
		this.smallHeight = smallHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	@Override
	public String toString() {
		return "ImgSize [width=" + width + ", height=" + height + ", smallWidth=" + smallWidth + ", smallHeight=" + smallHeight + ", minWidth=" + minWidth
				+ ", minHeight=" + minHeight + "]";
	}

}
