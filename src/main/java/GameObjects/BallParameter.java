package GameObjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

public class BallParameter
{
	// Animating parameters
	private int ThreedTiming = 20;

	// General Parameters
	private static int baseCounter = 0;
	private int baseNumber = 0;

	// Manual movments
	private int manualDistance = 10;

	// Physics Parameters
	private double VelocityX = 0;
	private double VelocityY = 0;
	private double PositionX = 0;
	private double PositionY = 0;
	private int Weight = 0;

	// Parameters to calculate collision position
	private boolean lockPosition = false;
	private double lastPositionX = 0;
	private double lastPositionY = 0;

	// Parameters appearance
	private Color color;

	// ----------------------------------------
	// Property variables to connect with table
	// ----------------------------------------
	IntegerProperty P_BaseNumerProperty = new SimpleIntegerProperty();
	IntegerProperty P_PosXProperty = new SimpleIntegerProperty();
	IntegerProperty P_PosYProperty = new SimpleIntegerProperty();
	IntegerProperty P_VxActualProperty = new SimpleIntegerProperty();
	IntegerProperty P_VyActualProperty = new SimpleIntegerProperty();
	IntegerProperty P_WeightProperty = new SimpleIntegerProperty();

	public IntegerProperty P_BaseNumberProperty()
	{
		return P_BaseNumerProperty;
	}

	public IntegerProperty P_PosXProperty()
	{
		return P_PosXProperty;
	}

	public IntegerProperty P_PosYProperty()
	{
		return P_PosYProperty;
	}

	public IntegerProperty P_VxActualProperty()
	{
		return P_VxActualProperty;
	}

	public IntegerProperty P_VyActualProperty()
	{
		return P_VyActualProperty;
	}

	public IntegerProperty P_WeightProperty()
	{
		return P_BaseNumerProperty;
	}

	// ----------------------------------------
	// ----------------------------------------
	// ----------------------------------------

	public BallParameter()
	{
		baseNumber = baseCounter;
		baseCounter++;

		P_BaseNumerProperty.set(baseNumber);
		P_PosXProperty.set((int) PositionX);
		P_PosYProperty.set((int) PositionY);
		P_VxActualProperty.set((int) VelocityX);
		P_VyActualProperty.set((int) VelocityY);
		P_WeightProperty.set(Weight);
	}

	public int getThreedTiming()
	{
		return ThreedTiming;
	}

	public void setThreedTiming(int threedTiming)
	{
		ThreedTiming = threedTiming;
	}

	public int getBaseNumber()
	{
		return baseNumber;
	}

	public void setBaseNumber(int baseNumber)
	{
		this.baseNumber = baseNumber;
		P_BaseNumerProperty.set(baseNumber);
	}

	public double getVelocityX()
	{
		return VelocityX;
	}

	public void setVelocityX(double velocityX)
	{
		VelocityX = velocityX;
		P_VxActualProperty.set((int) velocityX);
	}

	public double getVelocityY()
	{
		return VelocityY;
	}

	public void setVelocityY(double velocityY)
	{
		VelocityY = velocityY;
		P_VyActualProperty.set((int) velocityY);
	}

	public double getPositionX()
	{
		return PositionX;
	}

	public void setPositionX(double positionX)
	{
		PositionX = positionX;
		P_PosXProperty.set((int) positionX);
	}

	public double getPositionY()
	{
		return PositionY;
	}

	public void setPositionY(double positionY)
	{
		PositionY = positionY;
		P_PosYProperty.set((int) positionY);
	}

	public int getWeight()
	{
		return Weight;
	}

	public void setWeight(int weight)
	{
		Weight = weight;
		P_WeightProperty.set(weight);
	}

	public boolean isLockPosition()
	{
		return lockPosition;
	}

	public void setLockPosition(boolean lockPosition)
	{
		this.lockPosition = lockPosition;
	}

	public double getLastPositionX()
	{
		return lastPositionX;
	}

	public void setLastPositionX(double lastPositionX)
	{
		this.lastPositionX = lastPositionX;
	}

	public double getLastPositionY()
	{
		return lastPositionY;
	}

	public void setLastPositionY(double lastPositionY)
	{
		this.lastPositionY = lastPositionY;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getManualDistance()
	{
		return manualDistance;
	}

	public void setManualDistance(int manualDistance)
	{
		this.manualDistance = manualDistance;
	}

}
