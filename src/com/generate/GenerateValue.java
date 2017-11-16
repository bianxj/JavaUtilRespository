package com.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateValue {
	
	public List<Resolution> resolutions = null;
	public Resolution baseResolution;
	public GenerateValue(int baseX , int baseY) {
		super();
		baseResolution = new Resolution(baseX, baseY);
		generateResolution();
	}

	private void generateResolution() {
		resolutions = new ArrayList<>();
		resolutions.add(new Resolution(320, 480));
		resolutions.add(new Resolution(480, 800));
		resolutions.add(new Resolution(480, 854));
		resolutions.add(new Resolution(540, 854));
		resolutions.add(new Resolution(540, 960));
		resolutions.add(new Resolution(600, 800));
		resolutions.add(new Resolution(600, 1024));
		resolutions.add(new Resolution(640, 960));
		resolutions.add(new Resolution(720, 1184));
		resolutions.add(new Resolution(720, 1196));
		resolutions.add(new Resolution(720, 1208));
		resolutions.add(new Resolution(720, 1280));
		resolutions.add(new Resolution(750, 1334));
		resolutions.add(new Resolution(768, 1024));
		resolutions.add(new Resolution(800, 1280));
		resolutions.add(new Resolution(1080, 1700));
		resolutions.add(new Resolution(1080, 1776));
		resolutions.add(new Resolution(1080, 1800));
		resolutions.add(new Resolution(1080, 1812));
		resolutions.add(new Resolution(1800, 1920));
		resolutions.add(new Resolution(2560, 1440));
		resolutions.add(new Resolution(2560, 1600));
	}
	
	private void generate() throws IOException {
		for (Resolution resolution : resolutions) {
			generateFolder(resolution);
		}
	}

	public String folderName = "values-%dx%d";
	public String fileName = "resolution-dimen.xml";
	public void generateFolder(Resolution resolution) throws IOException {
		String fName = String.format(folderName, resolution.x_pixel,resolution.y_pixel);
		File dir = new File(fName);
		if ( !dir.exists() || !dir.isDirectory() ) {
			dir.mkdirs();
		}
		
		File dimenFile = new File(dir.getPath()+File.separator+fileName);
		if ( !dimenFile.exists() || !dimenFile.isFile() ) {
			dimenFile.createNewFile();
		}
		
		generateDimenFile(resolution, dimenFile);
	}

	public String dimen = "<dimen name=\"%s%dpx\">%.2fpx</dimen>\n";
	public void generateDimenFile(Resolution resolution , File file) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		builder.append("<resources>\n");

		int baseX = (int) baseResolution.getX_pixel();
		float cellX = ((float)resolution.getX_pixel())/baseResolution.getX_pixel();
		for (int i = 1; i <= baseX; i++) {
			builder.append(String.format(dimen,"x", i,cellX*i));
		}

		builder.append("\n");
		
		int baseY = (int) baseResolution.getY_pixel();
		float cellY = ((float)resolution.getY_pixel())/baseResolution.getY_pixel();
		for (int i = 1; i <= baseY; i++) {
			builder.append(String.format(dimen,"y", i,cellY*i));
		}
		
		builder.append("</resources>");
		
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(builder.toString().getBytes());
		outputStream.close();
	}
	
	private static int defBaseX = 720;
	private static int defBaseY = 1280;
	public static void main(String[] args) {
		if ( args.length >= 2 ) {
			defBaseX = Integer.parseInt(args[0]);
			defBaseY = Integer.parseInt(args[1]);
		}
		GenerateValue value = new GenerateValue(defBaseX, defBaseY);
		try {
			value.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Resolution{
		private final int x_pixel;
		private final int y_pixel;
		public Resolution(int x_pixel, int y_pixel) {
			super();
			this.x_pixel = x_pixel;
			this.y_pixel = y_pixel;
		}
		public float getX_pixel() {
			return x_pixel;
		}
		public float getY_pixel() {
			return y_pixel;
		}
	}
	
}
