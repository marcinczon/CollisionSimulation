package Calculations;

import GameObjects.Ball;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

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
		// Obliczenia konta i punktu zderzenia siê pi³ek
		// Do testow uzywane s¹ Ball_1, Ball_2.
		// *********************************************** //

		// Obliczenia trygonometryczne

		double sumX = 0;
		double sumY = 0;
		double sumR = 0;
		double sinAlfa = 0;
		double cosAlfa = 0;

		sumX = Ball_1.getPositionXActual() - Ball_2.getPositionXActual();

		if (Ball_1.getPositionXActual() > Ball_2.getPositionXActual())
		{
			sumX = Ball_1.getPositionXActual() - Ball_2.getPositionXActual();
		} else if (Ball_1.getPositionXActual() < Ball_2.getPositionXActual())
		{
			sumX = Ball_2.getPositionXActual() - Ball_1.getPositionXActual();
		} else
		{
			sumX = 0;
		}

		sumY = Ball_1.getPositionXActual() - Ball_2.getPositionXActual();
		if (Ball_1.getPositionXActual() > Ball_2.getPositionXActual())
		{
			sumY = Ball_1.getPositionXActual() - Ball_2.getPositionXActual();
		} else if (Ball_1.getPositionXActual() < Ball_2.getPositionXActual())
		{
			sumY = Ball_2.getPositionXActual() - Ball_1.getPositionXActual();
		} else
		{
			sumY = 0;
		}

		sumR = Ball_1.getShape().getRadius() + Ball_2.getShape().getRadius();

		sinAlfa = sumY / sumR;
		cosAlfa = sumX / sumR;

		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;

		x1 = Ball_1.getShape().getRadius() * cosAlfa;
		x2 = Ball_2.getShape().getRadius() * cosAlfa;

		y1 = Ball_1.getShape().getRadius() * sinAlfa;
		y2 = Ball_2.getShape().getRadius() * sinAlfa;

		if (Ball_1.getPositionXActual() > Ball_2.getPositionXActual())
		{
			collisionPoint.setCenterX(Ball_2.getPositionXActual() + x2);

		} else if (Ball_1.getPositionXActual() < Ball_2.getPositionXActual())
		{
			collisionPoint.setCenterX(Ball_2.getPositionXActual() - x2);

		} else
		{
			collisionPoint.setCenterX(0);
		}

		if (Ball_1.getPositionXActual() > Ball_2.getPositionXActual())
		{
			collisionPoint.setCenterY(Ball_2.getPositionXActual() + y2);

		} else if (Ball_1.getPositionXActual() < Ball_2.getPositionXActual())
		{
			collisionPoint.setCenterY(Ball_2.getPositionXActual() - y2);

		} else
		{
			collisionPoint.setCenterY(0);
		}

		collisionPoint.setRadius(3);
	//	collisionPoint.setFill(Color.RED);
	}

}
