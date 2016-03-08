package com.conordevilly.ocr.imageprocessing;

import java.util.ArrayList;

public class Character {
	
	private ArrayList<Pixel> pixels;
	private int width, height;
	private int minX, minY, maxX, maxY;
	
	public Character(ArrayList<Pixel> pixels){
		this.pixels = pixels;
		setProperties();
	}
	
	private void setProperties(){
		minX = minY = Integer.MAX_VALUE;
		maxX = maxY = 0;

		for(Pixel p : pixels){
			minX = (p.getX() < minX) ? p.getX() : minX;
			minY = (p.getY() < minY) ? p.getY() : minY;
			maxX = (p.getX() > maxX) ? p.getX() : maxX;
			maxY = (p.getY() > maxY) ? p.getY() : maxY;
		}

		height = maxY - minY;
		width = maxX - minX;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getMinX(){
		return minX;
	}
	public int getMinY(){
		return minY;
	}
	public int getMaxX(){
		return maxX;
	}
	public int getMaxY(){
		return maxY;
	}
}
