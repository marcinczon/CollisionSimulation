package Parameters;

public class GeneralParameters
{

	public static GeneralParameters generalParameters;

	public GeneralParameters()
	{
		generalParameters = this;
	}

	private double Timing = 0.05;
	private double Gravity = 9.8;
	private int newBalls = 4;

	public double getTiming()
	{
		return Timing;
	}

	public void setTiming(double timing)
	{
		Timing = timing;
	}

	public double getGravity()
	{
		return Gravity;
	}

	public void setGravity(double gravity)
	{
		Gravity = gravity;
	}

	public int getNewBalls()
	{
		return newBalls;
	}

	public void setNewBalls(int newBalls)
	{
		this.newBalls = newBalls;
	}
}
