package com.exfantasy.test.util;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

public class ImageUtil {

	public static Image createGotImage() {
    	Circle circle = new Circle();
    	circle.setRadius(5);
    	circle.setFill(Color.WHITE);
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(3);
        circle.setStrokeType(StrokeType.OUTSIDE);
        return circle.snapshot(null, null);
    }
	
	public static Image createNotGotImage() {
		Group group = new Group();
		
		Line line1 = new Line(0, 0, 10, 10);
		line1.setStroke(Color.RED);
		line1.setStrokeWidth(3);

		Line line2 = new Line(0, 10, 10, 0);
		line2.setStroke(Color.RED);
		line2.setStrokeWidth(3);

		group.getChildren().addAll(line1, line2);

		return group.snapshot(null, null);
	}
}
