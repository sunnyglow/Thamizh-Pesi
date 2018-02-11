
package com.tamil.thamizhpesi.audio.processor.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;

/**
 * Floating-point encoded (format 3) WAVE file loader.
 *
 * @author Karl Helgason
 */
public class WaveFloatFileReader extends SoundFileReader {

    public SoundFileFormat getAudioFileFormat(InputStream stream)
            throws Exception, IOException {

        stream.mark(200);
        SoundFileFormat format;
        try {
            format = internal_getAudioFileFormat(stream);
        } finally {
            stream.reset();
        }
        return format;
    }

    private SoundFileFormat internal_getAudioFileFormat(InputStream stream)
            throws Exception, IOException {

        RIFFReader riffiterator = new RIFFReader(stream);
        if (!riffiterator.getFormat().equals("RIFF"))
            throw new Exception();
        if (!riffiterator.getType().equals("WAVE"))
            throw new Exception();

        boolean fmt_found = false;
        boolean data_found = false;

        int channels = 1;
        long samplerate = 1;
        int framesize = 1;
        int bits = 1;

        while (riffiterator.hasNextChunk()) {
            RIFFReader chunk = riffiterator.nextChunk();

            if (chunk.getFormat().equals("fmt ")) {
                fmt_found = true;

                int format = chunk.readUnsignedShort();
                if (format != 3) // WAVE_FORMAT_IEEE_FLOAT only
                    throw new Exception();
                channels = chunk.readUnsignedShort();
                samplerate = chunk.readUnsignedInt();
                /* framerate = */chunk.readUnsignedInt();
                framesize = chunk.readUnsignedShort();
                bits = chunk.readUnsignedShort();
            }
            if (chunk.getFormat().equals("data")) {
                data_found = true;
                break;
            }
        }

        if (!fmt_found)
            throw new Exception();
        if (!data_found)
            throw new Exception();

        SoundFormat audioformat = new SoundFormat(
                SoundFloatConverter.PCM_FLOAT, samplerate, bits, channels,
                framesize, samplerate, false);
        SoundFileFormat fileformat = new SoundFileFormat(
                SoundFileFormat.Type.WAVE, audioformat,
                SoundSystem.NOT_SPECIFIED);
        return fileformat;
    }

    public SoundInputStream getAudioInputStream(InputStream stream)
            throws Exception, IOException {

        SoundFileFormat format = getAudioFileFormat(stream);
        RIFFReader riffiterator = new RIFFReader(stream);
        if (!riffiterator.getFormat().equals("RIFF"))
            throw new Exception();
        if (!riffiterator.getType().equals("WAVE"))
            throw new Exception();
        while (riffiterator.hasNextChunk()) {
            RIFFReader chunk = riffiterator.nextChunk();
            if (chunk.getFormat().equals("data")) {
                return new SoundInputStream(chunk, format.getFormat(),
                        chunk.getSize());
            }
        }
        throw new Exception();
    }

    public SoundFileFormat getAudioFileFormat(URL url)
            throws Exception, IOException {
        InputStream stream = url.openStream();
        SoundFileFormat format;
        try {
            format = getAudioFileFormat(new BufferedInputStream(stream));
        } finally {
            stream.close();
        }
        return format;
    }

    public SoundFileFormat getAudioFileFormat(File file)
            throws Exception, IOException {
        InputStream stream = new FileInputStream(file);
        SoundFileFormat format;
        try {
            format = getAudioFileFormat(new BufferedInputStream(stream));
        } finally {
            stream.close();
        }
        return format;
    }

    public SoundInputStream getAudioInputStream(URL url)
            throws Exception, IOException {
        return getAudioInputStream(new BufferedInputStream(url.openStream()));
    }

    public SoundInputStream getAudioInputStream(File file)
            throws Exception, IOException {
        return getAudioInputStream(new BufferedInputStream(new FileInputStream(
                file)));
    }
}
