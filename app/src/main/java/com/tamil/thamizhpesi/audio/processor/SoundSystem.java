package com.tamil.thamizhpesi.audio.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import com.tamil.thamizhpesi.audio.processor.utils.FormatConversionProvider;
import com.tamil.thamizhpesi.audio.processor.utils.SoundFileReader;
import com.tamil.thamizhpesi.audio.processor.utils.SoundFileWriter;
import com.tamil.thamizhpesi.audio.processor.utils.SoundFloatFormatConverter;
import com.tamil.thamizhpesi.audio.processor.utils.WaveFileReader;
import com.tamil.thamizhpesi.audio.processor.utils.WaveFileWriter;


/**
 * Created by Sureshkumar on 11/16/2017.
 */

public class SoundSystem
{
    public static final int NOT_SPECIFIED = -1;
    public static SoundInputStream getAudioInputStream(File file)
            throws Exception {

        SoundInputStream audioStream = null;
        SoundFileReader reader = new WaveFileReader();
        try {
            audioStream = reader.getAudioInputStream( file ); // throws IOException

        } catch (Exception e) {
            e.printStackTrace();
        }


        if( audioStream==null ) {
            throw new Exception("could not get audio input stream from input file");
        } else {
            return audioStream;
        }
    }

    public static SoundInputStream getAudioInputStream(InputStream stream) throws Exception
    {

        SoundInputStream audioStream = null;

        SoundFileReader reader = new WaveFileReader();
        try
        {
            audioStream = reader.getAudioInputStream( stream ); // throws IOException

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return audioStream;
    }
    public static int write(SoundInputStream stream, SoundFileFormat.Type fileType,
                            OutputStream out) throws IOException {


        int bytesWritten = 0;
        boolean flag = false;


            SoundFileWriter writer = new WaveFileWriter();
            try {
                bytesWritten = writer.write( stream, fileType, out ); // throws IOException
                flag = true;

            } catch (IllegalArgumentException e) {
                // thrown if this provider cannot write the sequence, try the next
                e.printStackTrace();
            }

        if(!flag) {
            throw new IllegalArgumentException("could not write audio file: file type not supported: " + fileType);
        } else {
            return bytesWritten;
        }
    }

    public static SoundInputStream getAudioInputStream(SoundFormat targetFormat,
                                                       SoundInputStream sourceStream) {

        if (sourceStream.getFormat().matches(targetFormat)) {
            return sourceStream;
        }




            FormatConversionProvider codec = new SoundFloatFormatConverter();
            if(codec.isConversionSupported(targetFormat,sourceStream.getFormat()) ) {
                return codec.getAudioInputStream(targetFormat,sourceStream);
            }


        // we ran out of options...
        throw new IllegalArgumentException("Unsupported conversion: " + targetFormat + " from " + sourceStream.getFormat());
    }

    public static SoundFormat[] getTargetFormats(SoundFormat.Encoding targetEncoding, SoundFormat sourceFormat) {


        Vector formats = new Vector();

        int size = 0;
        int index = 0;
        SoundFormat fmts[] = null;

        // gather from all the codecs


            FormatConversionProvider codec = new SoundFloatFormatConverter();
            fmts = codec.getTargetFormats(targetEncoding, sourceFormat);
            size += fmts.length;
            formats.addElement( fmts );


        // now build a new array

        SoundFormat fmts2[] = new SoundFormat[size];
        for(int i=0; i<formats.size(); i++ ) {
            fmts = (SoundFormat[])(formats.get(i));
            for(int j=0; j<fmts.length; j++ ) {
                fmts2[index++] = fmts[j];
            }
        }
        return fmts2;
    }
}
