package GameObjects;

public class ScreenParameter
{
	public static ScreenParameter screenParameter;
	public ScreenParameter()
	{
		screenParameter = this;
	}
	// Screen Size -> change to automatic
	private double ScreenMinX = 0;
	private double ScreenMaxX = 0;
	private double ScreenMinY = 0;
	private double ScreenMaxY = 0;
	
	public ScreenParameter getScreenParameter()
	{
		return screenParameter;
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
