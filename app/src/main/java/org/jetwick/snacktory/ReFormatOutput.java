package org.jetwick.snacktory;

public class ReFormatOutput 
{
	public static String formateData(String title, String imageURI, String content)
	{
		String style = "<style>img {\r\n" + 
				"    max-width: 100%;\r\n" + 
				"    height: auto;\r\n" + 
				"    width: auto\\9; /* ie8 */\r\n" + 
				"}"+
				"body {\r\n" + 
				"    background-color: #fef9e7;\r\n" + 
				"}"+
				"</style>";
		
		String header = "<!DOCTYPE html> <html> <head>  <meta charset=\"UTF-8\">"+style+"</head> <body> <article>";
		String footer = "</article> </body> </html>";
		
		String html = header + "\n"+getTitle(title) +"\n"+ getImage(imageURI) +"\n"+ getParagraph(content) + footer;
		return html;
	}
	
	public static String getTitle(String title)
	{
		String htmlTitle = "<h1>"+title+"</h1>";
		return htmlTitle;
	}
	
	public static String getParagraph(String paragraph)
	{
		String htmlParagraph = " <p>"+paragraph+"</p>";
		return paragraph;
	}
	
	public static String getImage(String imageURI)
	{
		String htmlImage = "<img src=\""+imageURI+"\"\"> </br>";
		return htmlImage;
	}
}
