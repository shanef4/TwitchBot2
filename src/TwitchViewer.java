public class TwitchViewer
{
	private String vName;
	private int vPoints;
	private int lPoints;
	private int rV;
	private int cd;

	public TwitchViewer(String name, int points, int lpoints, int r, int cd)
	{
		this.vName = name;
		this.vPoints = points;
		this.lPoints = lpoints;
		this.rV = r;
		this.cd = cd;
	}

	public String getName()
	{
		return this.vName;
	}
	
	public void setName(String name)
	{
		this.vName = name;
	}

	public int getPoints()
	{
		return this.vPoints;
	}

	public void setPoints(int p)
	{
		this.vPoints = p;
	}
	
	public int getCD()
	{
		return this.cd;
	}

	public void setCD(int cd)
	{
		this.cd = cd;
	}
	
	public int getLPoints()
	{
		return this.lPoints;
	}
	
	public void setRV(int r)
	{
		this.rV = r;
	}
	
	public int getRV()
	{
		return this.rV;
	}

	public void setLPoints(int p)
	{
		this.lPoints = p;
	}

	public void addPoints(int newP)
	{
		this.vPoints += newP;
	}

	public void subPoints(int newP)
	{
		this.vPoints -= newP;
		if (this.vPoints < 0) {
			this.vPoints = 0;
		}
	}
}
