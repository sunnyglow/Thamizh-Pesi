package com.tamil.thamizhpesi.builder;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogspot.vayalumvazhvum.activity.R;
import com.blogspot.vayalumvazhvum.activity.SplashScreenActivity;
import com.tamil.thamizhpesi.constants.TamilTTSConstants;
import com.tamil.thamizhpesi.model.TimeDataModel;
import com.tamil.thamizhpesi.search.trie.MapTrie;
import com.tamil.thamizhpesi.search.trie.SortedMapTrie;

public class InitializeObjects {

	public static List<TimeDataModel> clusterModelList = new ArrayList<TimeDataModel>();
	public static List<TimeDataModel> twoThreeLetterModelList = new ArrayList<TimeDataModel>();
	public static final MapTrie<String> mapTrie = new SortedMapTrie<>();
	public static Map<String, TimeDataModel> clusterMap = new HashMap<String, TimeDataModel>();

	public static void main(String[] args) {
		AssetManager assetManager = SplashScreenActivity.applicationContext.getAssets();

		try
		{
			String [] list = assetManager.list("raw");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		//initializeTimeDataObjects(clusterModelList, TamilTTSConstants.TIME_DATA_PATH, TamilTTSConstants.AUDIO_PATH);
		//initializeTimeDataObjects(twoThreeLetterModelList, TamilTTSConstants.TWO_THREE_LETTER_PATH, TamilTTSConstants.TWO_THREE_LETTER_AUDIO_PATH);
	}

	public static void initializeTimeDataObjects()
	{
		Field[] fields=R.raw.class.getFields();
		initializeTimeDataObjectsAndroid(clusterModelList, fields, SplashScreenActivity.applicationContext,"");
		//initializeTimeDataObjects(clusterModelList, TamilTTSConstants.TIME_DATA_PATH, TamilTTSConstants.AUDIO_PATH);
		//initializeTimeDataObjects(twoThreeLetterModelList, TamilTTSConstants.TWO_THREE_LETTER_PATH, TamilTTSConstants.TWO_THREE_LETTER_AUDIO_PATH);
	}

	public static void initializeTimeDataObjectsAndroid(List<TimeDataModel> modelList, Field[] fields, Context context, String audioPath)
	{
		TimeDataModel model = null;


		try
		{
			BufferedReader reader = null;


			for(int i = 0; i < fields.length; i++)
			{
				String fileName = fields[i].getName();

				int id = SplashScreenActivity.applicationContext.getResources().getIdentifier(fileName, "raw", SplashScreenActivity.applicationContext.getPackageName());
				InputStream inputStream = SplashScreenActivity.applicationContext.getResources().openRawResource(id);
				reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

				String line = null;
				model = new TimeDataModel();
				model.setFileName(fileName);
				model.setAudioPath(audioPath);
				boolean firstLine = true;
				while((line = reader.readLine()) != null)
				{
					line = line.trim();

					if(firstLine)
					{
						String[] wordArray = line.split(" ");

						for(int x = 0 ; x < wordArray.length; x++)
						{
							if(wordArray[x].trim().equalsIgnoreCase(""))
							{
								continue;
							}
							mapTrie.insert(wordArray[x], fileName);
						}
						model.setSentence(line);
						firstLine = false;
						continue;
					}
					String[] buffer = line.split(",");
					model.getLetterList().add(buffer[0]);
					model.getStartTime().add(Integer.parseInt(buffer[1]));
					model.getEndTime().add(Integer.parseInt(buffer[2]));
				}
				modelList.add(model);
				clusterMap.put(fileName, model);
				model = null;
				reader.close();
				reader = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void initializeTimeDataObjects(List<TimeDataModel> modelList, String path, String audioPath)
	{
		TimeDataModel model = null;


		try
		{
			BufferedReader reader = null;

			File files = new File(path);
			File file[] = files.listFiles();
			for(File actualFile:file)
			{
				if(actualFile.isDirectory())
				{
					continue;
				}
				String str= actualFile.getAbsolutePath();
				str = str.substring(str.lastIndexOf('/')+1);
				String fileName = str.substring(0, str.lastIndexOf('.'));
				reader = new BufferedReader(new FileReader(actualFile));

				String line = null;
				model = new TimeDataModel();
				model.setFileName(fileName);
				model.setAudioPath(audioPath);
				boolean firstLine = true;
				while((line = reader.readLine()) != null)
				{
					line = line.trim();

					if(firstLine)
					{
						String[] wordArray = line.split(" ");

						for(int x = 0 ; x < wordArray.length; x++)
						{
							if(wordArray[x].trim().equalsIgnoreCase(""))
							{
								continue;
							}
							mapTrie.insert(wordArray[x], fileName);
						}
						model.setSentence(line);
						firstLine = false;
						continue;
					}
					String[] buffer = line.split(",");
					model.getLetterList().add(buffer[0]);
					model.getStartTime().add(Integer.parseInt(buffer[1]));
					model.getEndTime().add(Integer.parseInt(buffer[2]));
				}
				modelList.add(model);
				clusterMap.put(fileName, model);
				model = null;
				reader.close();
				reader = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
