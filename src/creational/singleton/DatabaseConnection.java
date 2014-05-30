package creational.singleton;

public class DatabaseConnection
{
	private static volatile DatabaseConnection instance = null;
	
	private DatabaseConnection()
	{
	}
	
	public static synchronized DatabaseConnection getInstance()
	{
		if (DatabaseConnection.instance == null)
		{
			synchronized (DatabaseConnection.class)
			{
				if (DatabaseConnection.instance == null)
				{
					DatabaseConnection.instance = new DatabaseConnection();
				}
			}
		}
		
		return DatabaseConnection.instance;
	}
	
	public void executeQuery(String query)
	{
		System.out.println(query);
	}
}