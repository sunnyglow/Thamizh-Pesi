// Copyright 2013 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
// www.source-code.biz, www.inventec.ch/chdh
//
// This module is multi-licensed and may be used under the terms
// of any of the following licenses:
//
//  EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
//  LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.

package com.tamil.thamizhpesi.dsp.filter;

import com.tamil.thamizhpesi.audio.processor.SoundInputStream;

/**
* Wrapper for using the IIR filter class with the Java Sound API.
*
* <p>
* This class provides an {@link SoundInputStream} for
* filtering a sound stream.
*/
public class IirFilterAudioInputStream {

// Dummy constructor to suppress Javadoc.
private IirFilterAudioInputStream() {}

/**
* Returns an SoundInputStream that supplies the filtered audio signal.
*
* @param in
*    The input SoundInputStream.
* @param coeffs
*    The IIR filter coefficients.
*/
public static SoundInputStream getAudioInputStream (SoundInputStream in, IirFilterCoefficients coeffs) {
   int channels = in.getFormat().getChannels();
   IirFilter[] iirFilters = new IirFilter[channels];
   for (int channel = 0; channel < channels; channel++) {
      iirFilters[channel] = new IirFilter(coeffs); }
   return SignalFilterAudioInputStream.getAudioInputStream(in, iirFilters); }

}
