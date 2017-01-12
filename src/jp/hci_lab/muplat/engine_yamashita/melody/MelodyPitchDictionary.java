package engine_yamashita.melody;

import java.util.ArrayList;

public class MelodyPitchDictionary extends ArrayList<MelodyPitchDictionaryRecord> {
	public MelodyPitchDictionary() {
		super();
		makeDictionary();
	}

	public void makeDictionary() {
		MelodyPitch llll = new MelodyPitch("low", "low", "low", "low");
		MelodyPitch lllm = new MelodyPitch("low", "low", "low", "mid");
		MelodyPitch lllh = new MelodyPitch("low", "low", "low", "high");
		MelodyPitch llml = new MelodyPitch("low", "low", "mid", "low");
		MelodyPitch llmm = new MelodyPitch("low", "low", "mid", "mid");
		MelodyPitch llmh = new MelodyPitch("low", "low", "mid", "high");
		MelodyPitch llhl = new MelodyPitch("low", "low", "high", "low");
		MelodyPitch llhm = new MelodyPitch("low", "low", "high", "mid");
		MelodyPitch llhh = new MelodyPitch("low", "low", "high", "high");
		MelodyPitch lmll = new MelodyPitch("low", "mid", "low", "low");
		MelodyPitch lmlm = new MelodyPitch("low", "mid", "low", "mid");
		MelodyPitch lmlh = new MelodyPitch("low", "mid", "low", "high");
		MelodyPitch lmml = new MelodyPitch("low", "mid", "mid", "low");
		MelodyPitch lmmm = new MelodyPitch("low", "mid", "mid", "mid");
		MelodyPitch lmmh = new MelodyPitch("low", "mid", "mid", "high");
		MelodyPitch lmhl = new MelodyPitch("low", "mid", "high", "low");
		MelodyPitch lmhm = new MelodyPitch("low", "mid", "high", "mid");
		MelodyPitch lmhh = new MelodyPitch("low", "mid", "high", "high");
		MelodyPitch lhll = new MelodyPitch("low", "high", "low", "low");
		MelodyPitch lhlm = new MelodyPitch("low", "high", "low", "mid");
		MelodyPitch lhlh = new MelodyPitch("low", "high", "low", "high");
		MelodyPitch lhml = new MelodyPitch("low", "high", "mid", "low");
		MelodyPitch lhmm = new MelodyPitch("low", "high", "mid", "mid");
		MelodyPitch lhmh = new MelodyPitch("low", "high", "mid", "high");
		MelodyPitch lhhl = new MelodyPitch("low", "high", "high", "low");
		MelodyPitch lhhm = new MelodyPitch("low", "high", "high", "mid");
		MelodyPitch lhhh = new MelodyPitch("low", "high", "high", "high");
		MelodyPitch mlll = new MelodyPitch("mid", "low", "low", "low");
		MelodyPitch mllm = new MelodyPitch("mid", "low", "low", "mid");
		MelodyPitch mllh = new MelodyPitch("mid", "low", "low", "high");
		MelodyPitch mlml = new MelodyPitch("mid", "low", "mid", "low");
		MelodyPitch mlmm = new MelodyPitch("mid", "low", "mid", "mid");
		MelodyPitch mlmh = new MelodyPitch("mid", "low", "mid", "high");
		MelodyPitch mlhl = new MelodyPitch("mid", "low", "high", "low");
		MelodyPitch mlhm = new MelodyPitch("mid", "low", "high", "mid");
		MelodyPitch mlhh = new MelodyPitch("mid", "low", "high", "high");
		MelodyPitch mmll = new MelodyPitch("mid", "mid", "low", "low");
		MelodyPitch mmlm = new MelodyPitch("mid", "mid", "low", "mid");
		MelodyPitch mmlh = new MelodyPitch("mid", "mid", "low", "high");
		MelodyPitch mmml = new MelodyPitch("mid", "mid", "mid", "low");
		MelodyPitch mmmm = new MelodyPitch("mid", "mid", "mid", "mid");
		MelodyPitch mmmh = new MelodyPitch("mid", "mid", "mid", "high");
		MelodyPitch mmhl = new MelodyPitch("mid", "mid", "high", "low");
		MelodyPitch mmhm = new MelodyPitch("mid", "mid", "high", "mid");
		MelodyPitch mmhh = new MelodyPitch("mid", "mid", "high", "high");
		MelodyPitch mhll = new MelodyPitch("mid", "high", "low", "low");
		MelodyPitch mhlm = new MelodyPitch("mid", "high", "low", "mid");
		MelodyPitch mhlh = new MelodyPitch("mid", "high", "low", "high");
		MelodyPitch mhml = new MelodyPitch("mid", "high", "mid", "low");
		MelodyPitch mhmm = new MelodyPitch("mid", "high", "mid", "mid");
		MelodyPitch mhmh = new MelodyPitch("mid", "high", "mid", "high");
		MelodyPitch mhhl = new MelodyPitch("mid", "high", "high", "low");
		MelodyPitch mhhm = new MelodyPitch("mid", "high", "high", "mid");
		MelodyPitch mhhh = new MelodyPitch("mid", "high", "high", "high");
		MelodyPitch hlll = new MelodyPitch("high", "low", "low", "low");
		MelodyPitch hllm = new MelodyPitch("high", "low", "low", "mid");
		MelodyPitch hllh = new MelodyPitch("high", "low", "low", "high");
		MelodyPitch hlml = new MelodyPitch("high", "low", "mid", "low");
		MelodyPitch hlmm = new MelodyPitch("high", "low", "mid", "mid");
		MelodyPitch hlmh = new MelodyPitch("high", "low", "mid", "high");
		MelodyPitch hlhl = new MelodyPitch("high", "low", "high", "low");
		MelodyPitch hlhm = new MelodyPitch("high", "low", "high", "mid");
		MelodyPitch hlhh = new MelodyPitch("high", "low", "high", "high");
		MelodyPitch hmll = new MelodyPitch("high", "mid", "low", "low");
		MelodyPitch hmlm = new MelodyPitch("high", "mid", "low", "mid");
		MelodyPitch hmlh = new MelodyPitch("high", "mid", "low", "high");
		MelodyPitch hmml = new MelodyPitch("high", "mid", "mid", "low");
		MelodyPitch hmmm = new MelodyPitch("high", "mid", "mid", "mid");
		MelodyPitch hmmh = new MelodyPitch("high", "mid", "mid", "high");
		MelodyPitch hmhl = new MelodyPitch("high", "mid", "high", "low");
		MelodyPitch hmhm = new MelodyPitch("high", "mid", "high", "mid");
		MelodyPitch hmhh = new MelodyPitch("high", "mid", "high", "high");
		MelodyPitch hhll = new MelodyPitch("high", "high", "low", "low");
		MelodyPitch hhlm = new MelodyPitch("high", "high", "low", "mid");
		MelodyPitch hhlh = new MelodyPitch("high", "high", "low", "high");
		MelodyPitch hhml = new MelodyPitch("high", "high", "mid", "low");
		MelodyPitch hhmm = new MelodyPitch("high", "high", "mid", "mid");
		MelodyPitch hhmh = new MelodyPitch("high", "high", "mid", "high");
		MelodyPitch hhhl = new MelodyPitch("high", "high", "high", "low");
		MelodyPitch hhhm = new MelodyPitch("high", "high", "high", "mid");
		MelodyPitch hhhh = new MelodyPitch("high", "high", "high", "high");

		// よく出そうなパターンの順に登録 (とりあえず既存楽曲数曲を分析して出てきたパターンを登録しておく)
		/*
		add(new MelodyPitchDictionaryRecord("mmmm2mmmm", mmmm, mmmm, 0)); // 天体観測サビ1->2
		add(new MelodyPitchDictionaryRecord("mmmm2mmmm", mmmm, mmmm, 0)); // 天体観測サビ2->3
		add(new MelodyPitchDictionaryRecord("mmmm2mmmm", mmmm, mmmm, 0)); // 天体観測サビ3->4
		add(new MelodyPitchDictionaryRecord("mmmm2mlmm", mmmm, mlmm, 0)); // 天体観測サビ4->5
		add(new MelodyPitchDictionaryRecord("mlmm2lmlm", mlmm, lmlm, 0)); // 天体観測サビ5->6
		add(new MelodyPitchDictionaryRecord("lmlm2hhmm", lmlm, hhmm, 0)); // 天体観測サビ6->7
		add(new MelodyPitchDictionaryRecord("hhmm2mlll", hhmm, mlll, 0)); // 天体観測サビ7->8

		add(new MelodyPitchDictionaryRecord("hmml2hhmm", hmml, hhmm, 0)); // メルトサビ1->2
		add(new MelodyPitchDictionaryRecord("hhmm2hhhh", hhmm, hhhh, 0)); // メルトサビ2->3
		add(new MelodyPitchDictionaryRecord("hhhh2llmm", hhhh, llmm, 0)); // メルトサビ3->4
		add(new MelodyPitchDictionaryRecord("llmm2mlll", llmm, mlll, 0)); // メルトサビ4->5
		add(new MelodyPitchDictionaryRecord("mlll2hmml", mlll, hmml, 0)); // メルトサビ5->6
		add(new MelodyPitchDictionaryRecord("hmml2mmhh", hmml, mmhh, 0)); // メルトサビ6->7
		add(new MelodyPitchDictionaryRecord("mmhh2mmmh", mmhh, mmmh, 0)); // メルトサビ7->8

		add(new MelodyPitchDictionaryRecord("mlmh2hhhh", mlmh, hhhh, 0)); // シルエットサビ1->2
		add(new MelodyPitchDictionaryRecord("hhhh2hmhh", hhhh, hmhh, 0)); // シルエットサビ2->3
		add(new MelodyPitchDictionaryRecord("hmhh2hhhh", hmhh, hhhh, 0)); // シルエットサビ3->4
		add(new MelodyPitchDictionaryRecord("hhhh2mmhh", hhhh, mmhh, 0)); // シルエットサビ4->5
		add(new MelodyPitchDictionaryRecord("mmhh2mmhh", mmhh, mmhh, 0)); // シルエットサビ5->6
		add(new MelodyPitchDictionaryRecord("mmhh2mmmm", mmhh, mmmm, 0)); // シルエットサビ6->7
		add(new MelodyPitchDictionaryRecord("mmmm2mmhm", mmmm, mmhm, 0)); // シルエットサビ7->8
		*/
		add(new MelodyPitchDictionaryRecord("mmmm2mmmm", mmmm, mmmm, 0)); // 3
		add(new MelodyPitchDictionaryRecord("mmmm2mlmm", mmmm, mlmm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mlmm2lmlm", mlmm, lmlm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("lmlm2hhmm", lmlm, hhmm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hhmm2mlll", hhmm, mlll, 0)); // 1

		add(new MelodyPitchDictionaryRecord("hmml2hhmm", hmml, hhmm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hhmm2hhhh", hhmm, hhhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hhhh2llmm", hhhh, llmm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("llmm2mlll", llmm, mlll, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mlll2hmml", mlll, hmml, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hmml2mmhh", hmml, mmhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mmhh2mmmh", mmhh, mmmh, 0)); // 1

		add(new MelodyPitchDictionaryRecord("mlmh2hhhh", mlmh, hhhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hhhh2hmhh", hhhh, hmhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hmhh2hhhh", hmhh, hhhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("hhhh2mmhh", hhhh, mmhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mmhh2mmhh", mmhh, mmhh, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mmhh2mmmm", mmhh, mmmm, 0)); // 1
		add(new MelodyPitchDictionaryRecord("mmmm2mmhm", mmmm, mmhm, 0)); // 1
	}
}
