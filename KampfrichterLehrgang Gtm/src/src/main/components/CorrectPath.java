package src.main.components;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

public class CorrectPath
{
	private String path;

	public CorrectPath(String p)
	{
		this.path=p;
	}

	public String getPath(String path)
	{
		if (path == null || path.isEmpty())
		{
			System.err.println("No path provided!");
			return null;
		}
		URL urlOfFile = getClass().getResource(path);
		if (urlOfFile == null)
		{
			System.err.println(path + " not found!");
			return null;
		}
		URI uriOfFile;
		try
		{
			uriOfFile=urlOfFile.toURI();
		}
		catch (URISyntaxException e)
		{
			System.err.println("URL of " + path + " couldn't be converted to URI!");
			return null;
		}
		File pathFileObject;
		try
		{
			pathFileObject=new File(uriOfFile);
		}
		catch (Exception e)
		{
			System.err.println("URI of " + path + " couldn't be converted to File!");
			return null;
		}

		return pathFileObject.getPath();
	}
}


