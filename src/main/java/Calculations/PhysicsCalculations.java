package Calculations;

import GameObjects.BallParameter;

import static Parameters.GeneralParameters.generalParameters;
import static Parameters.ScreenParameter.screenParameter;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;


public class PhysicsCalculations
{
	BallParameter ReferenceBallParameter;

	// Screen Size
	private double ScreenMinX = 0;
	private double ScreenMaxX = 0;
	private double ScreenMinY = 0;
	private double ScreenMaxY = 0;

	// Calculation variable
	private double Timing = 0.05;
	private double Gravity = 9.8;
	private double VxDecline = 10;
	private double VyDecline = 10;

	// For axes X
	private double PositionX = 0;
	private double deltaSx = 0;
	private double VxActual = 0;
	private double Vx0 = 0;

	// For axes Y
	private double PositionY = 0;
	private double deltaSy = 0;
	private double VyActual = 0;
	private double Vy0 = 0;
	private double Vy1 = 0;

	public PhysicsCalculations(BallParameter ballParameter)
	{
		this.ReferenceBallParameter = ballParameter;

		ScreenMaxX = screenParameter.getScreenMaxX();
		ScreenMaxY = screenParameter.getScreenMaxY();

	}

	public void startCalculatePhysics()
	{
		// Reading data from parameters

		Gravity = generalParameters.getGravity();
		Timing = generalParameters.getTiming();

		VxActual = ReferenceBallParameter.getVelocityX();
		PositionX = ReferenceBallParameter.getPositionX();

		VyActual = ReferenceBallParameter.getVelocityY();
		PositionY = ReferenceBallParameter.getPositionY();

		calculationPhisicsX();
		calculationPhisicsY();

		// Safe data to parameters

		ReferenceBallParameter.setVelocityX(VxActual);
		ReferenceBallParameter.setPositionX(PositionX);

		ReferenceBallParameter.setVelocityY(VyActual);
		ReferenceBallParameter.setPositionY(PositionY);

	}

	public void calculationPhisicsX()
	{
		// Calculating

		Vx0 = VxActual;
		deltaSx = Vx0 * Timing;
		PositionX = PositionX + deltaSx;

		if (PositionX >= ScreenMaxX)
		{
			PositionX = ScreenMaxX;
			Vx0 = -(Vx0 - VxDecline);
		}
		if (PositionX <= ScreenMinX)
		{
			Vx0 = -(Vx0 + VxDecline);
			PositionX = 0;
		}
		VxActual = Vx0;

	}

	public void calculationPhisicsY()
	{
		// Calculating

		Vy0 = VyActual;
		Vy1 = Vy0 + Gravity * Timing;
		Vy0 = Vy1;
		deltaSy = (Vy0 * Timing + (Gravity * Timing * Timing) / 2);
		PositionY = PositionY + deltaSy;

		if (PositionY >= ScreenMaxY)
		{
			PositionY = ScreenMaxY;
			Vy0 = -(Vy0 - VyDecline);
		}
		if (PositionY <= ScreenMinY)
		{
			Vy0 = -Vy0;
			PositionY = 0;
		}
		VyActual = Vy0;

	}

	public double getGravity()
	{
		return Gravity;
	}

	public void setGravity(double gravity)
	{
		Gravity = gravity;
	}

	public double getScreenMinX()
	{
		return ScreenMinX;
	}

	public void setScreenMinX(double screenMinX)
	{
		ScreenMinX = screenMinX;
	}

	public double getScreenMaxX()
	{
		return ScreenMaxX;
	}

	public void setScreenMaxX(double screenMaxX)
	{
		ScreenMaxX = screenMaxX;
	}

	public double getScreenMinY()
	{
		return ScreenMinY;
	}

	public void setScreenMinY(double screenMinY)
	{
		ScreenMinY = screenMinY;
	}

	public double getScreenMaxY()
	{
		return ScreenMaxY;
	}

	public void setScreenMaxY(double screenMaxY)
	{
		ScreenMaxY = screenMaxY;
	}
}
