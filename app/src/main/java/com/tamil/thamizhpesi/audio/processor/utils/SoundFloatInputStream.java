package com.tamil.thamizhpesi.audio.processor.utils;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.tamil.thamizhpesi.audio.processor.SoundFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;

/**
 * This class is used to create SoundFloatInputStream from SoundInputStream and
 * byte buffers.
 *
 * @author Karl Helgason
 */
public abstract class SoundFloatInputStream {

    private static class BytaArraySoundFloatInputStream
            extends SoundFloatInputStream {

        private int pos = 0;
        private int markpos = 0;
        private SoundFloatConverter converter;
        private SoundFormat format;
        private byte[] buffer;
        private int buffer_offset;
        private int buffer_len;
        private int framesize_pc;

        public BytaArraySoundFloatInputStream(SoundFloatConverter converter,
                                              byte[] buffer, int offset, int len) {
            this.converter = converter;
            this.format = converter.getFormat();
            this.buffer = buffer;
            this.buffer_offset = offset;
            framesize_pc = format.getFrameSize() / format.getChannels();
            this.buffer_len = len / framesize_pc;

        }

        public SoundFormat getFormat() {
            return format;
        }

        public long getFrameLength() {
            return buffer_len;// / format.getFrameSize();
        }

        public int read(float[] b, int off, int len) throws IOException {
            if (b == null)
                throw new NullPointerException();
            if (off < 0 || len < 0 || len > b.length - off)
                throw new IndexOutOfBoundsException();
            if (pos >= buffer_len)
                return -1;
            if (len == 0)
                return 0;
            if (pos + len > buffer_len)
                len = buffer_len - pos;
            converter.toFloatArray(buffer, buffer_offset + pos * framesize_pc,
                    b, off, len);
            pos += len;
            return len;
        }

        public long skip(long len) throws IOException {
            if (pos >= buffer_len)
                return -1;
            if (len <= 0)
                return 0;
            if (pos + len > buffer_len)
                len = buffer_len - pos;
            pos += len;
            return len;
        }

        public int available() throws IOException {
            return buffer_len - pos;
        }

        public void close() throws IOException {
        }

        public void mark(int readlimit) {
            markpos = pos;
        }

        public boolean markSupported() {
            return true;
        }

        public void reset() throws IOException {
            pos = markpos;
        }
    }

    private static class DirectSoundFloatInputStream
            extends SoundFloatInputStream {

        private SoundInputStream stream;
        private SoundFloatConverter converter;
        private int framesize_pc; // framesize / channels
        private byte[] buffer;

        public DirectSoundFloatInputStream(SoundInputStream stream) {
            converter = SoundFloatConverter.getConverter(stream.getFormat());
            if (converter == null) {
                SoundFormat format = stream.getFormat();
                SoundFormat newformat;

                SoundFormat[] formats = SoundSystem.getTargetFormats(
                        SoundFormat.Encoding.PCM_SIGNED, format);
                if (formats.length != 0) {
                    newformat = formats[0];
                } else {
                    float samplerate = format.getSampleRate();
                    int samplesizeinbits = format.getSampleSizeInBits();
                    int framesize = format.getFrameSize();
                    float framerate = format.getFrameRate();
                    samplesizeinbits = 16;
                    framesize = format.getChannels() * (samplesizeinbits / 8);
                    framerate = samplerate;

                    newformat = new SoundFormat(
                            SoundFormat.Encoding.PCM_SIGNED, samplerate,
                            samplesizeinbits, format.getChannels(), framesize,
                            framerate, false);
                }

                stream = SoundSystem.getAudioInputStream(newformat, stream);
                converter = SoundFloatConverter.getConverter(stream.getFormat());
            }
            framesize_pc = stream.getFormat().getFrameSize()
                    / stream.getFormat().getChannels();
            this.stream = stream;
        }

        public SoundFormat getFormat() {
            return stream.getFormat();
        }

        public long getFrameLength() {
            return stream.getFrameLength();
        }

        public int read(float[] b, int off, int len) throws IOException {
            int b_len = len * framesize_pc;
            if (buffer == null || buffer.length < b_len)
                buffer = new byte[b_len];
            int ret = stream.read(buffer, 0, b_len);
            if (ret == -1)
                return -1;
            converter.toFloatArray(buffer, b, off, ret / framesize_pc);
            return ret / framesize_pc;
        }

        public long skip(long len) throws IOException {
            long b_len = len * framesize_pc;
            long ret = stream.skip(b_len);
            if (ret == -1)
                return -1;
            return ret / framesize_pc;
        }

        public int available() throws IOException {
            return stream.available() / framesize_pc;
        }

        public void close() throws IOException {
            stream.close();
        }

        public void mark(int readlimit) {
            stream.mark(readlimit * framesize_pc);
        }

        public boolean markSupported() {
            return stream.markSupported();
        }

        public void reset() throws IOException {
            stream.reset();
        }
    }

   /* public static SoundFloatInputStream getInputStream(URL url)
            throws Exception, IOException {
        return new DirectSoundFloatInputStream(SoundSystem
                .getAudioInputStream(url));
    }*/

    public static SoundFloatInputStream getInputStream(File file)
            throws Exception, IOException {
        return new DirectSoundFloatInputStream(SoundSystem
                .getAudioInputStream(file));
    }

   /* public static SoundFloatInputStream getInputStream(InputStream stream)
            throws Exception, IOException {
        return new DirectSoundFloatInputStream(SoundSystem
                .getAudioInputStream(stream));
    }*/

    public static SoundFloatInputStream getInputStream(
            SoundInputStream stream) {
        return new DirectSoundFloatInputStream(stream);
    }

    public static SoundFloatInputStream getInputStream(SoundFormat format,
                                                       byte[] buffer, int offset, int len) {
        SoundFloatConverter converter = SoundFloatConverter
                .getConverter(format);
        if (converter != null)
            return new BytaArraySoundFloatInputStream(converter, buffer,
                    offset, len);

        InputStream stream = new ByteArrayInputStream(buffer, offset, len);
        long aLen = format.getFrameSize() == SoundSystem.NOT_SPECIFIED
                ? SoundSystem.NOT_SPECIFIED : len / format.getFrameSize();
        SoundInputStream astream = new SoundInputStream(stream, format, aLen);
        return getInputStream(astream);
    }

    public abstract SoundFormat getFormat();

    public abstract long getFrameLength();

    public abstract int read(float[] b, int off, int len) throws IOException;

    public int read(float[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public float read() throws IOException {
        float[] b = new float[1];
        int ret = read(b, 0, 1);
        if (ret == -1 || ret == 0)
            return 0;
        return b[0];
    }

    public abstract long skip(long len) throws IOException;

    public abstract int available() throws IOException;

    public abstract void close() throws IOException;

    public abstract void mark(int readlimit);

    public abstract boolean markSupported();

    public abstract void reset() throws IOException;
}
