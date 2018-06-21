package Calculations;

import GameObjects.Ball;
import javafx.scene.paint.Color;
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
}
