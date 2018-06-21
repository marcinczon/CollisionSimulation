package Calculations;

import GameObjects.Ball;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import static FX_Controllers.ControllerFXML_Base.controllerFXML_Base;

public class CollisionsCalculations
{
	public static void CollisionTwoBall(Ball Ball_1, Ball Ball_2)
	{
		// *********************************************** //
		// Calculation of velocity two balls based on energy and momentum
		// mV^/2=E i p=mv
		// *********************************************** //
		double Ux1 = Ball_1.getVxActual();
		double Ux2 = Ball_2.getVxActual();

		double Uy1 = Ball_1.getVyActual();
		double Uy2 = Ball_2.getVyActual();

		double m1 = Ball_1.getSize();
		double m2 = Ball_2.getSize();

		double Vx1 = 0, Vx2 = 0, Vy1 = 0, Vy2 = 0;

		Vx1 = (Ux1 * (m1 - m2) + 2 * m2 * Ux2) / (m1 + m2);
		Vy1 = (Uy1 * (m1 - m2) + 2 * m2 * Uy2) / (m1 + m2);

		Vx2 = (Ux2 * (m2 - m1) + 2 * m1 * Ux1) / (m2 + m1);
		Vy2 = (Uy2 * (m2 - m1) + 2 * m1 * Uy1) / (m2 + m1);

		Ball_1.setVxActual(Vx1);
		Ball_2.setVxActual(Vx2);

		Ball_1.setVyActual(Vy1);
		Ball_2.setVyActual(Vy2);
	}

	public static void CollisionTwoBallSimple(Ball Ball_1, Ball Ball_2)
	{
		// *********************************************** //
		// Calculation of velocity two balls based on energy and momentum
		// mV^/2=E i p=mv
		// *********************************************** //
		double Ux1 = Ball_1.getVxActual();
		double Ux2 = Ball_2.getVxActual();

		double Uy1 = Ball_1.getVyActual();
		double Uy2 = Ball_2.getVyActual();

		double m1 = Ball_1.getSize();
		double m2 = Ball_2.getSize();

		Ball_1.setVxActual(-Ux1);
		Ball_2.setVxActual(-Ux2);

		Ball_1.setVyActual(-Uy1);
		Ball_2.setVyActual(-Uy2);
	}

	public static void CalculateAngelAndPointOfCollision(Ball Ball_1, Ball Ball_2, Circle collisionPoint)
	{
		// *********************************************** //
		// Calculating angle and position where ball collision
		// used to safe and back position - avoid continues intersection
		// *********************************************** //

		// Obliczenia trygonometryczne
		
		double posX_Ball_1 = Ball_1.getPositionXActual();
		double posX_Ball_2 = Ball_2.getPositionXActual();
		double posY_Ball_1 = Ball_1.getPositionYActual();
		double posY_Ball_2 = Ball_2.getPositionYActual();
		double radius_Ball_1 = Ball_1.getShape().getRadius();
		double radius_Ball_2 = Ball_2.getShape().getRadius();

		double sumX = 0;
		double sumY = 0;
		double sumR = 0;
		double sinAlfa = 0;
		double cosAlfa = 0;
		
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		double xColision = 0;
		double yColision = 0;


		//Checking position
		
		if (posX_Ball_1 > posX_Ball_2)
		{
			sumX = posX_Ball_1 - posX_Ball_2;
		} else if (posX_Ball_1 < posX_Ball_2)
		{
			sumX = posX_Ball_2 - posX_Ball_1;
		} else
		{
			sumX = 0;
		}

		if (posY_Ball_1 > posY_Ball_2)
		{
			sumY = posY_Ball_1 - posY_Ball_2;
		} else if (posY_Ball_1 < posY_Ball_2)
		{
			sumY = posY_Ball_2 - posY_Ball_1;
		} else
		{
			sumY = 0;
		}

		sumR = radius_Ball_1 + radius_Ball_2;

		// Calculating trygonometry
		
		sinAlfa = sumY / sumR;
		cosAlfa = sumX / sumR;
	
		x1 = radius_Ball_1 * cosAlfa;
		x2 = radius_Ball_2 * cosAlfa;

		y1 = radius_Ball_1 * sinAlfa;
		y2 = radius_Ball_2 * sinAlfa;

		
		//Checking position
		
		if (posX_Ball_1 >= posX_Ball_2)
		{
			xColision = posX_Ball_2 + x2;

		} else if (posX_Ball_1 < posX_Ball_2)
		{
			xColision = posX_Ball_2 - x2;

		} else
		{
			xColision = 0;
		}

		if (posY_Ball_1 >= posY_Ball_2)
		{
			yColision = posY_Ball_2 + y2;

		} else if (posY_Ball_1 < posY_Ball_2)
		{
			yColision = posY_Ball_2 - y2;

		} else
		{
			yColision = 0;
		}

		// Safe position to ball
		
		collisionPoint.setCenterX(xColision);
		collisionPoint.setCenterY(yColision);
		collisionPoint.setRadius(3);
		collisionPoint.setFill(Color.BLUE);
	}

}
