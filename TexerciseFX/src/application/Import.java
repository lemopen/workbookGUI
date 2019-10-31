package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Import {
	/**
	 * ファイルの有無を真偽値で返すメソッド
	 * @param str
	 * @return
	 */
	public boolean isFilePresent(String str) {
		//インスタンス取得
		List<String> list = null;
		String filePath = str;

		//ファイルを確認
		try {
			list = importFile(filePath);
		} catch (NullPointerException e) {
			return false;
		}
		if (list != null) {//ファイルがあった場合
			return true;
		}
		return false;
	}

	/**
	 * 問題取得メソッド
	 * @param str
	 * @return
	 */
	public ArrayList<String> importFile(String str){
		//問題を一覧を取得して返却
		try {

			return ImportDao.textToArrayList(str);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}