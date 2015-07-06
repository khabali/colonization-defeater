package com.colonidefeater.game.utils;

import static com.colonidefeater.game.utils.Constants.PPM;

import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class MapBodyBuilder {

	public static PolygonShape createRectangle(
			RectangleMapObject rectangleObject) {
		final Rectangle rectangle = rectangleObject.getRectangle();
		final PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(rectangle.width * 0.5f / PPM, rectangle.height * 0.5f
				/ PPM);
		return polygon;
	}

	/**
	 * 
	 * @param circleObject
	 * @return
	 */
	public static CircleShape createEllipse(EllipseMapObject circleObject) {
		final Ellipse circle = circleObject.getEllipse();
		final CircleShape circleShape = new CircleShape();
		circleShape.setRadius(circle.width * 0.5f / PPM);
		return circleShape;
	}

	public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
		final PolygonShape polygon = new PolygonShape();
		final float[] vertices = polygonObject.getPolygon()
				.getTransformedVertices();

		final float[] worldVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; ++i) {
			worldVertices[i] = vertices[i] / PPM;
		}

		polygon.set(worldVertices);
		return polygon;
	}

	public static ChainShape createPolyline(PolylineMapObject polylineObject) {
		final float[] vertices = polylineObject.getPolyline()
				.getTransformedVertices();
		final Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] / PPM;
			worldVertices[i].y = vertices[i * 2 + 1] / PPM;
		}

		final ChainShape chain = new ChainShape();
		chain.createChain(worldVertices);
		return chain;
	}
}
