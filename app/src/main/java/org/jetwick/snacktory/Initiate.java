package org.jetwick.snacktory;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

public class Initiate {

	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		HtmlFetcher fetcher = new HtmlFetcher();
		// set cache. e.g. take the map implementation from google collections:
		// fetcher.setCache(new MapMaker().concurrencyLevel(20).maximumSize(count).
//		    expireAfterWrite(minutes, TimeUnit.MINUTES).makeMap();
		String articleUrl = "https://www.vikatan.com/juniorvikatan/2017-nov-29/kazhugar/136525-mrkazhugu-politics-current-affairs.html?artfrm=magazine_hits";
		JResult res = fetcher.fetchAndExtract(articleUrl, 10*1000, true);
		String text = res.getText(); 
		String title = res.getTitle(); 
		String imageUrl = res.getImageUrl();
		//System.out.println(text);
		//System.out.println(title);
		//System.out.println(imageUrl);
		System.out.println(ReFormatOutput.formateData(title, imageUrl, text));
		
		/*String line[] = text.split("\\.");
		JResult result = new JResult();
		ArticleTextExtractor extractor = new ArticleTextExtractor();
		HtmlToPlainText formatter = new HtmlToPlainText();
		//html = formatter.getPlainText(Jsoup.parse(html));
		result  = extractor.extractContent(html);
		String text = result.getText(); 
		String title = result.getTitle(); 
		String imageUrl = result.getImageUrl();
		//System.out.println(result);
		System.out.println(ReFormatOutput.formateData(title, imageUrl, text));*/
	}
	
	static String html = "";
}
