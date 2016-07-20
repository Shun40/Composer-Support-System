# muplat

HCI Lab Music Platform

### 備考
sandboxフォルダはgit/githubの練習用です。

---

### DAWエンジン インタフェース

 package jp.hci_lab.muplat;  
 DAW daw = new DAW();
  

#### daw
DAWエンジン全体
- SetBPM(int bpm)
- NewProject()
- OpenProject(String filename)
- SaveProject(String filename)
- CloseProject()
  

#### daw.controller
再生、録音操作関係
- Start()
- Stop()
- Pause()
- Restart()
  

#### daw.track[]
DAWのトラックに相当するクラス(デフォルトで16トラック)
- void NoteOn(int note_no, int velocity)
- void NoteOff(int note_no)
- void SetInstrument(int n)
  

#### daw.score
楽曲データ関係。MIDIデータを少し抽象化してアクセスしやすくする（予定）
- SetTrack(int track_no)
- AddNote(int position, int note_no, int duration)
  
  
#### daw.config
楽曲に依存しないDAW全体の設定関係
-  List<String> GetMidiDeviceNameList()  
MIDIデバイス名の一覧表示

