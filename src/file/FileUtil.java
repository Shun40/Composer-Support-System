package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gui.component.pianoroll.note.NoteModel;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * ファイルに関するユーティリティクラス
 * @author Shun Yamashita
 */
public class FileUtil {
	/**
	 * ファイルを読み込む
	 * @param file ファイルオブジェクト
	 * @return 読み込んだファイルの行リスト
	 */
	public static List<String> readFile(File file) {
		List<String> lines = new ArrayList<String>();
		if(file != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				while(line != null) {
					lines.add(line);
					line = br.readLine();
				}
				br.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return lines;
	}

	/**
	 * MUP形式のファイルを読み込む
	 * @return MUP形式で読み込んだデータが入ったオブジェクト
	 */
	public static MupFileData readMupFile() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("MUPファイルを読み込む");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Muplat Files", "*.mup"), new ExtensionFilter("All Files", "+.+"));
		File readFile = fc.showOpenDialog(null);
		MupFileData mupFileData = null;
		if(readFile != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(readFile));
				int bpm = Integer.parseInt(br.readLine());
				List<NoteModel> noteModels = new ArrayList<NoteModel>();
				String line = br.readLine();
				while(line != null) {
					int track = Integer.parseInt(line.split(":", -1)[0]);
					int pitch = Integer.parseInt(line.split(":", -1)[1]);
					int position = Integer.parseInt(line.split(":", -1)[2]);
					int duration = Integer.parseInt(line.split(":", -1)[3]);
					noteModels.add(new NoteModel(track, 0, pitch, position, duration, 0));
					line = br.readLine();
				}
				br.close();
				mupFileData = new MupFileData(bpm, noteModels);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return mupFileData;
	}

	/**
	 * MUP形式のファイルを書き込む
	 * @param mupFileData MUP形式で書き込むデータが入ったオブジェクト
	 */
	public static void writeMupFile(MupFileData mupFileData) {
		final FileChooser fc = new FileChooser();
		fc.setTitle("MUPファイルに保存");
		fc.setInitialFileName((new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())) + ".mup");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Muplat Files", "*.mup"), new ExtensionFilter("All Files", "+.+"));
		File saveFile = fc.showSaveDialog(null);
		if(saveFile != null) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(saveFile)));
				pw.println(mupFileData.getBpm());
				for(NoteModel noteModel : mupFileData.getNoteModels()) {
					pw.println(noteModel.getTrack() + ":" + noteModel.getPitch() + ":" + noteModel.getPosition() + ":"+ noteModel.getDuration());
				}
				pw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
