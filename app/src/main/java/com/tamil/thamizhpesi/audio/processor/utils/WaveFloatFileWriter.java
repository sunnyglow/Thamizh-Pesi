package com.tamil.thamizhpesi.audio.processor.utils;

import java.io.File;
        import java.io.IOException;
        import java.io.OutputStream;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;
import com.tamil.thamizhpesi.audio.processor.SoundFileFormat.Type;

/**
 * Floating-point encoded (format 3) WAVE file writer.
 *
 * @author Karl Helgason
 */
public class WaveFloatFileWriter extends SoundFileWriter {

    public SoundFileFormat.Type[] getAudioFileTypes() {
        return new Type[] { Type.WAVE };
    }

    public Type[] getAudioFileTypes(SoundInputStream stream) {

        if (!stream.getFormat().getEncoding().equals(
                SoundFloatConverter.PCM_FLOAT))
            return new Type[0];
        return new Type[] { Type.WAVE };
    }

    private void checkFormat(SoundFileFormat.Type type, SoundInputStream stream) {
        if (!Type.WAVE.equals(type))
            throw new IllegalArgumentException("File type " + type
                    + " not supported.");
        if (!stream.getFormat().getEncoding().equals(
                SoundFloatConverter.PCM_FLOAT))
            throw new IllegalArgumentException("File format "
                    + stream.getFormat() + " not supported.");
    }

    public void write(SoundInputStream stream, RIFFWriter writer)
            throws IOException {

        RIFFWriter fmt_chunk = writer.writeChunk("fmt ");

        SoundFormat format = stream.getFormat();
        fmt_chunk.writeUnsignedShort(3); // WAVE_FORMAT_IEEE_FLOAT
        fmt_chunk.writeUnsignedShort(format.getChannels());
        fmt_chunk.writeUnsignedInt((int) format.getSampleRate());
        fmt_chunk.writeUnsignedInt(((int) format.getFrameRate())
                * format.getFrameSize());
        fmt_chunk.writeUnsignedShort(format.getFrameSize());
        fmt_chunk.writeUnsignedShort(format.getSampleSizeInBits());
        fmt_chunk.close();
        RIFFWriter data_chunk = writer.writeChunk("data");
        byte[] buff = new byte[1024];
        int len;
        while ((len = stream.read(buff, 0, buff.length)) != -1)
            data_chunk.write(buff, 0, len);
        data_chunk.close();
    }

    private static class NoCloseOutputStream extends OutputStream {
        OutputStream out;

        public NoCloseOutputStream(OutputStream out) {
            this.out = out;
        }

        public void write(int b) throws IOException {
            out.write(b);
        }

        public void flush() throws IOException {
            out.flush();
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
        }

        public void write(byte[] b) throws IOException {
            out.write(b);
        }
    }

    private SoundInputStream toLittleEndian(SoundInputStream ais) {
        SoundFormat format = ais.getFormat();
        SoundFormat targetFormat = new SoundFormat(format.getEncoding(), format
                .getSampleRate(), format.getSampleSizeInBits(), format
                .getChannels(), format.getFrameSize(), format.getFrameRate(),
                false);
        return SoundSystem.getAudioInputStream(targetFormat, ais);
    }

    public int write(SoundInputStream stream, Type fileType, OutputStream out)
            throws IOException {

        checkFormat(fileType, stream);
        if (stream.getFormat().isBigEndian())
            stream = toLittleEndian(stream);
        RIFFWriter writer = new RIFFWriter(new NoCloseOutputStream(out), "WAVE");
        write(stream, writer);
        int fpointer = (int) writer.getFilePointer();
        writer.close();
        return fpointer;
    }

    public int write(SoundInputStream stream, Type fileType, File out)
            throws IOException {
        checkFormat(fileType, stream);
        if (stream.getFormat().isBigEndian())
            stream = toLittleEndian(stream);
        RIFFWriter writer = new RIFFWriter(out, "WAVE");
        write(stream, writer);
        int fpointer = (int) writer.getFilePointer();
        writer.close();
        return fpointer;
    }

}

