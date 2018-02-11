package com.tamil.thamizhpesi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.lang.reflect.Field;
import java.util.List;

import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.blogspot.vayalumvazhvum.activity.SplashScreenActivity;
import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;
import com.tamil.thamizhpesi.audio.processor.WaveFile;
import com.tamil.thamizhpesi.constants.TamilTTSConstants;
import com.tamil.thamizhpesi.model.ResultModel;

public class SynthesizeSpeech
{
	static int counter = 0;
	static int storeCounter = 0;


	static boolean pathIdentified = false;
	public static void speak(List<ResultModel> modelList, String audioFileName)
	{
		pathIdentified = true;
		boolean append = false;
		SoundInputStream clip1 = null;
		ResultModel model = null;

		for(int i = 0; i < modelList.size(); i++)
		{
			counter++;
			try
			{
				model = modelList.get(i);
				String fileName = model.getFileName()+".wav";
				String fullPath = model.getAudioPath()+fileName;
				storeCounter++;
				if (clip1 == null)
				{
					if(fileName.equalsIgnoreCase("SPACE.wav"))
					{
						clip1 = introduceSpace("SPACE");
					}
					else if(fileName.equalsIgnoreCase("FULL_STOP.wav"))
					{
						clip1 = introduceSpace("FULL_STOP");
					}
					else
					{
						clip1 = getSoundInputStream(fileName);
						clip1 = optimalTrim(clip1, model.getStartTime(), model.getEndTime());

					}
					continue;
				}
				SoundInputStream clip2 = null;

				if(fileName.equalsIgnoreCase("SPACE.wav"))
				{
					clip2 = introduceSpace("SPACE");
				}
				else if(fileName.equalsIgnoreCase("FULL_STOP.wav"))
				{
					clip2 = introduceSpace("FULL_STOP");
				}
				else
				{
					clip2 = getSoundInputStream(fileName);
					clip2 = optimalTrim(clip2, model.getStartTime(), model.getEndTime());
				}
				SoundInputStream appendedFiles = null;
				if(i == modelList.size() - 1)
				{
					appendedFiles = new SoundInputStream(new SequenceInputStream(clip1, clip2),
							clip1.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
				}
				else {
					appendedFiles = new SoundInputStream(new SequenceInputStream(clip1, clip2),
							clip1.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
				}
				clip1 = appendedFiles;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
		}

		FileOutputStream fileOutput = null;
		try
		{
			File file = new File(SplashScreenActivity.GENERATED_VOICE_PATH+"/"+audioFileName);
			fileOutput = new FileOutputStream(file, false);
			SoundSystem.write(clip1, SoundFileFormat.Type.WAVE, fileOutput);
			clip1 = null;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally {
			if(fileOutput != null)
			{
				try {
					fileOutput.close();
					fileOutput = null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}


	public static SoundInputStream getSoundInputStream(String fileName)
	{
		try
		{
			InputStream fileStream = SplashScreenActivity.expansionFile.getInputStream(fileName);
			return SoundSystem.getAudioInputStream(fileStream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}




	public static int getMilliSecond(long frameLength, SoundInputStream clip) {
		float milliSecond = (frameLength * 1000) / clip.getFormat().getFrameRate();

		return Math.round(milliSecond);
	}

	public static SoundInputStream introduceSpace(String pauseType)
	{
		long pause = 0;
		if(pauseType.equalsIgnoreCase("SPACE"))
		{
			pause = TamilTTSConstants.SPACE;
		}
		else if(pauseType.equalsIgnoreCase("FULL_STOP"))
		{
			pause = TamilTTSConstants.FULL_STOP;
		}

		String fileName = TamilTTSConstants.SILENT;
		SoundInputStream clip2 = null;
		long frameLength = 0;
		try
		{
			clip2 = getSoundInputStream(fileName);
			frameLength = getFrameLength(pause, clip2);

			Field f = clip2.getClass().getDeclaredField("frameLength"); // NoSuchFieldException
			f.setAccessible(true);
			f.setLong(clip2, frameLength);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clip2;
	}

	public static long getFrameLength(long milliSecond, SoundInputStream clip) {
		long frameLength = (long) ((milliSecond * clip.getFormat().getFrameRate()) / 1000);
		return frameLength;
	}

	public static long calculateByte(SoundInputStream clip, long milliSecond)
	{
		long bitsPerSecond = (long) (clip.getFormat().getFrameRate() * 16 * clip.getFormat().getChannels());
		long bytesPerSecond = bitsPerSecond / 8;
		return ((bytesPerSecond / 1000) * milliSecond);
	}

	public static SoundInputStream forwardTrim(SoundInputStream clip, long startMilliSecond) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		clip.skip(calculateByte(clip, startMilliSecond));
		return clip;
	}

	public static SoundInputStream optimalTrim(SoundInputStream clip, long startMilliSecond, long endMilliSecond) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		clip.skip(calculateByte(clip, startMilliSecond));
		clip.frameLength = getFrameLength(endMilliSecond, clip);
		return clip;
	}

}
