# muplat

HCI Lab Music Platform

### 備考
sandboxフォルダはgit/githubの練習用です。

---

### DAWエンジン インタフェース

> package jp.hci_lab.muplat;  
> DAW daw = new DAW();
  

#### daw
DAWエンジン全体
- SetBPM(int bpm)
- NewProject()
- OpenProject(String filename)  
現状は.MIDファイルが読み込める。（いずれ付加的情報も読むようにするかも）
- SaveProject(String filename)  
現状は.MIDファイルが書き込める。
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
即時発音。シーケンスとして配置する場合はdaw.score.AddNote()を使う。
- void NoteOff(int note_no)  
即時発音停止。
- void SetInstrument(int n)

#### daw.score
楽曲データ関係。MIDIデータを少し抽象化してアクセスしやすくする（予定）
- SetTrack(int track_no)
- AddNote(int position, int note_no, int duration)  
シーケンスの指定位置に発音イベントを配置する

#### daw.config
楽曲に依存しないDAW全体の設定関係
-  List<String> GetMidiDeviceNameList()  
MIDIデバイス名の一覧表示

