package system;

import java.util.*;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

public class Config {
	MidiDevice.Info[] midiDeviceInfo_;

	private void GetMidiDeviceInfo() {
		midiDeviceInfo_ = MidiSystem.getMidiDeviceInfo();		
	}
	
	public List<String> GetMidiDeviceNameList() {
		List<String> names = new ArrayList<String>();
		GetMidiDeviceInfo();
		for (MidiDevice.Info info: midiDeviceInfo_) {
			names.add(info.getName());
		}
		return names;
	}

}
