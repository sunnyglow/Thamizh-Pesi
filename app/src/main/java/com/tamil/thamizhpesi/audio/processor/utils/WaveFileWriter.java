package com.tamil.thamizhpesi.audio.processor.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.lang.IllegalArgumentException;

import com.tamil.thamizhpesi.audio.processor.SoundFileFormat;
import com.tamil.thamizhpesi.audio.processor.SoundFormat;
import com.tamil.thamizhpesi.audio.processor.SoundInputStream;
import com.tamil.thamizhpesi.audio.processor.SoundSystem;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;

//$$fb this class is buggy. Should be replaced in future.

/**
 * WAVE file writer.
 *
 * @author Jan Borgersen
 */
public class WaveFileWriter extends TamilTTSFileWriter {

    // magic numbers
    static  final int RIFF_MAGIC = 1380533830;
    static  final int WAVE_MAGIC = 1463899717;
    static  final int FMT_MAGIC  = 0x666d7420; // "fmt "
    static  final int DATA_MAGIC = 0x64617461; // "data"

    // encodings
    static final int WAVE_FORMAT_UNKNOWN   = 0x0000;
    static final int WAVE_FORMAT_PCM       = 0x0001;
    static final int WAVE_FORMAT_ADPCM     = 0x0002;
    static final int WAVE_FORMAT_ALAW      = 0x0006;
    static final int WAVE_FORMAT_MULAW     = 0x0007;
    static final int WAVE_FORMAT_OKI_ADPCM = 0x0010;
    static final int WAVE_FORMAT_DIGISTD   = 0x0015;
    static final int WAVE_FORMAT_DIGIFIX   = 0x0016;
    static final int WAVE_IBM_FORMAT_MULAW = 0x0101;
    static final int WAVE_IBM_FORMAT_ALAW  = 0x0102;
    static final int WAVE_IBM_FORMAT_ADPCM = 0x0103;
    static final int WAVE_FORMAT_DVI_ADPCM = 0x0011;
    static final int WAVE_FORMAT_SX7383    = 0x1C07;

    /**
     * WAVE type
     */
    private static final SoundFileFormat.Type waveTypes[] = {
            SoundFileFormat.Type.WAVE
    };


    /**
     * Constructs a new WaveFileWriter object.
     */
    public WaveFileWriter() {
        super(waveTypes);
    }


    // METHODS TO IMPLEMENT SoundFileWriter


    public SoundFileFormat.Type[] getAudioFileTypes(SoundInputStream stream) {

        SoundFileFormat.Type[] filetypes = new SoundFileFormat.Type[types.length];
        System.arraycopy(types, 0, filetypes, 0, types.length);

        // make sure we can write this stream
        SoundFormat format = stream.getFormat();
        SoundFormat.Encoding encoding = format.getEncoding();

        if( SoundFormat.Encoding.ALAW.equals(encoding) ||
                SoundFormat.Encoding.ULAW.equals(encoding) ||
                SoundFormat.Encoding.PCM_SIGNED.equals(encoding) ||
                SoundFormat.Encoding.PCM_UNSIGNED.equals(encoding) ) {

            return filetypes;
        }

        return new SoundFileFormat.Type[0];
    }


    public int write(SoundInputStream stream, SoundFileFormat.Type fileType, OutputStream out) throws IOException {

        //$$fb the following check must come first ! Otherwise
        // the next frame length check may throw an IOException and
        // interrupt iterating File Writers. (see bug 4351296)

        // throws IllegalArgumentException if not supported
        WaveFileFormat waveFileFormat = (WaveFileFormat)getAudioFileFormat(fileType, stream);

        //$$fb when we got this far, we are committed to write this file

        // we must know the total data length to calculate the file length
        if( stream.getFrameLength() == SoundSystem.NOT_SPECIFIED ) {
            throw new IOException("stream length not specified");
        }

        int bytesWritten = writeWaveFile(stream, waveFileFormat, out);
        return bytesWritten;
    }


    public int write(SoundInputStream stream, SoundFileFormat.Type fileType, File out) throws IOException {

        // throws IllegalArgumentException if not supported
        WaveFileFormat waveFileFormat = (WaveFileFormat)getAudioFileFormat(fileType, stream);

        // first write the file without worrying about length fields
        FileOutputStream fos = new FileOutputStream( out );     // throws IOException
        BufferedOutputStream bos = new BufferedOutputStream( fos, bisBufferSize );
        int bytesWritten = writeWaveFile(stream, waveFileFormat, bos );
        bos.close();

        // now, if length fields were not specified, calculate them,
        // open as a random access file, write the appropriate fields,
        // close again....
        if( waveFileFormat.getByteLength()== SoundSystem.NOT_SPECIFIED ) {

            int dataLength=bytesWritten-waveFileFormat.getHeaderSize();
            int riffLength=dataLength + waveFileFormat.getHeaderSize() - 8;

            RandomAccessFile raf=new RandomAccessFile(out, "rw");
            // skip RIFF magic
            raf.skipBytes(4);
            raf.writeInt(big2little( riffLength ));
            // skip WAVE magic, fmt_ magic, fmt_ length, fmt_ chunk, data magic
            raf.skipBytes(4+4+4+WaveFileFormat.getFmtChunkSize(waveFileFormat.getWaveType())+4);
            raf.writeInt(big2little( dataLength ));
            // that's all
            raf.close();
        }

        return bytesWritten;
    }

    //--------------------------------------------------------------------

    /**
     * Returns the SoundFileFormat describing the file that will be written from this SoundInputStream.
     * Throws IllegalArgumentException if not supported.
     */
    private SoundFileFormat getAudioFileFormat(SoundFileFormat.Type type, SoundInputStream stream) {
        SoundFormat format = null;
        WaveFileFormat fileFormat = null;
        SoundFormat.Encoding encoding = SoundFormat.Encoding.PCM_SIGNED;

        SoundFormat streamFormat = stream.getFormat();
        SoundFormat.Encoding streamEncoding = streamFormat.getEncoding();

        float sampleRate;
        int sampleSizeInBits;
        int channels;
        int frameSize;
        float frameRate;
        int fileSize;

        if (!types[0].equals(type)) {
            throw new IllegalArgumentException("File type " + type + " not supported.");
        }
        int waveType = WaveFileFormat.WAVE_FORMAT_PCM;

        if( SoundFormat.Encoding.ALAW.equals(streamEncoding) ||
                SoundFormat.Encoding.ULAW.equals(streamEncoding) ) {

            encoding = streamEncoding;
            sampleSizeInBits = streamFormat.getSampleSizeInBits();
            if (streamEncoding.equals(SoundFormat.Encoding.ALAW)) {
                waveType = WAVE_FORMAT_ALAW;
            } else {
                waveType = WAVE_FORMAT_MULAW;
            }
        } else if ( streamFormat.getSampleSizeInBits()==8 ) {
            encoding = SoundFormat.Encoding.PCM_UNSIGNED;
            sampleSizeInBits=8;
        } else {
            encoding = SoundFormat.Encoding.PCM_SIGNED;
            sampleSizeInBits=streamFormat.getSampleSizeInBits();
        }


        format = new SoundFormat( encoding,
                streamFormat.getSampleRate(),
                sampleSizeInBits,
                streamFormat.getChannels(),
                streamFormat.getFrameSize(),
                streamFormat.getFrameRate(),
                false);       // WAVE is little endian

        if( stream.getFrameLength()!= SoundSystem.NOT_SPECIFIED ) {
            fileSize = (int)stream.getFrameLength()*streamFormat.getFrameSize()
                    + WaveFileFormat.getHeaderSize(waveType);
        } else {
            fileSize = SoundSystem.NOT_SPECIFIED;
        }

        fileFormat = new WaveFileFormat( SoundFileFormat.Type.WAVE,
                fileSize,
                format,
                (int)stream.getFrameLength() );

        return fileFormat;
    }


    private int writeWaveFile(InputStream in, WaveFileFormat waveFileFormat, OutputStream out) throws IOException {

        int bytesRead = 0;
        int bytesWritten = 0;
        InputStream fileStream = getFileStream(waveFileFormat, in);
        byte buffer[] = new byte[bisBufferSize];
        int maxLength = waveFileFormat.getByteLength();

        while( (bytesRead = fileStream.read( buffer )) >= 0 ) {

            if (maxLength>0) {
                if( bytesRead < maxLength ) {
                    out.write( buffer, 0, (int)bytesRead );
                    bytesWritten += bytesRead;
                    maxLength -= bytesRead;
                } else {
                    out.write( buffer, 0, (int)maxLength );
                    bytesWritten += maxLength;
                    maxLength = 0;
                    break;
                }
            } else {
                out.write( buffer, 0, (int)bytesRead );
                bytesWritten += bytesRead;
            }
        }

        return bytesWritten;
    }

    private InputStream getFileStream(WaveFileFormat waveFileFormat, InputStream audioStream) throws IOException {
        // private method ... assumes audioFileFormat is a supported file type

        // WAVE header fields
        SoundFormat soundFormat = waveFileFormat.getFormat();
        int headerLength       = waveFileFormat.getHeaderSize();
        int riffMagic          = WaveFileFormat.RIFF_MAGIC;
        int waveMagic          = WaveFileFormat.WAVE_MAGIC;
        int fmtMagic           = WaveFileFormat.FMT_MAGIC;
        int fmtLength          = WaveFileFormat.getFmtChunkSize(waveFileFormat.getWaveType());
        short wav_type         = (short) waveFileFormat.getWaveType();
        short channels         = (short) soundFormat.getChannels();
        short sampleSizeInBits = (short) soundFormat.getSampleSizeInBits();
        int sampleRate         = (int) soundFormat.getSampleRate();
        int frameSizeInBytes   = (int) soundFormat.getFrameSize();
        int frameRate              = (int) soundFormat.getFrameRate();
        int avgBytesPerSec     = channels * sampleSizeInBits * sampleRate / 8;;
        short blockAlign       = (short) ((sampleSizeInBits / 8) * channels);
        int dataMagic              = WaveFileFormat.DATA_MAGIC;
        int dataLength             = waveFileFormat.getFrameLength() * frameSizeInBytes;
        int length                         = waveFileFormat.getByteLength();
        int riffLength = dataLength + headerLength - 8;

        byte header[] = null;
        ByteArrayInputStream headerStream = null;
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        SequenceInputStream waveStream = null;

        SoundFormat audioStreamFormat = null;
        SoundFormat.Encoding encoding = null;
        InputStream codedAudioStream = audioStream;

        // if audioStream is an SoundInputStream and we need to convert, do it here...
        if(audioStream instanceof SoundInputStream) {
            audioStreamFormat = ((SoundInputStream)audioStream).getFormat();

            encoding = audioStreamFormat.getEncoding();

            if(SoundFormat.Encoding.PCM_SIGNED.equals(encoding)) {
                if( sampleSizeInBits==8 ) {
                    wav_type = WaveFileFormat.WAVE_FORMAT_PCM;
                    // plug in the transcoder to convert from PCM_SIGNED to PCM_UNSIGNED
                    codedAudioStream = SoundSystem.getAudioInputStream( new SoundFormat(
                                    SoundFormat.Encoding.PCM_UNSIGNED,
                                    audioStreamFormat.getSampleRate(),
                                    audioStreamFormat.getSampleSizeInBits(),
                                    audioStreamFormat.getChannels(),
                                    audioStreamFormat.getFrameSize(),
                                    audioStreamFormat.getFrameRate(),
                                    false),
                            (SoundInputStream)audioStream);
                }
            }
            if( (SoundFormat.Encoding.PCM_SIGNED.equals(encoding) && audioStreamFormat.isBigEndian()) ||
                    (SoundFormat.Encoding.PCM_UNSIGNED.equals(encoding) && !audioStreamFormat.isBigEndian()) ||
                    (SoundFormat.Encoding.PCM_UNSIGNED.equals(encoding) && audioStreamFormat.isBigEndian()) ) {
                if( sampleSizeInBits!=8) {
                    wav_type = WaveFileFormat.WAVE_FORMAT_PCM;
                    // plug in the transcoder to convert to PCM_SIGNED_LITTLE_ENDIAN
                    codedAudioStream = SoundSystem.getAudioInputStream( new SoundFormat(
                                    SoundFormat.Encoding.PCM_SIGNED,
                                    audioStreamFormat.getSampleRate(),
                                    audioStreamFormat.getSampleSizeInBits(),
                                    audioStreamFormat.getChannels(),
                                    audioStreamFormat.getFrameSize(),
                                    audioStreamFormat.getFrameRate(),
                                    false),
                            (SoundInputStream)audioStream);
                }
            }
        }


        // Now push the header into a stream, concat, and return the new SequenceInputStream

        baos = new ByteArrayOutputStream();
        dos = new DataOutputStream(baos);

        // we write in littleendian...
        dos.writeInt(riffMagic);
        dos.writeInt(big2little( riffLength ));
        dos.writeInt(waveMagic);
        dos.writeInt(fmtMagic);
        dos.writeInt(big2little(fmtLength));
        dos.writeShort(big2littleShort(wav_type));
        dos.writeShort(big2littleShort(channels));
        dos.writeInt(big2little(sampleRate));
        dos.writeInt(big2little(avgBytesPerSec));
        dos.writeShort(big2littleShort(blockAlign));
        dos.writeShort(big2littleShort(sampleSizeInBits));
        //$$fb 2002-04-16: Fix for 4636355: RIFF audio headers could be _more_ spec compliant
        if (wav_type != WaveFileFormat.WAVE_FORMAT_PCM) {
            // add length 0 for "codec specific data length"
            dos.writeShort(0);
        }

        dos.writeInt(dataMagic);
        dos.writeInt(big2little(dataLength));

        dos.close();
        header = baos.toByteArray();
        headerStream = new ByteArrayInputStream( header );
        waveStream = new SequenceInputStream(headerStream,codedAudioStream);

        return (InputStream)waveStream;
    }
}
