package application;

import java.util.ArrayList;

public class Exercise {

	/**
	 * 受け取ったファイル名の拡張子を除去して返すメソッド
	 * */
	public String removeExtension(String filePath) {
		String str;
		int last = filePath.lastIndexOf(".");
		str = filePath.substring(0, last);
		return str;
	}

	public String getExtension(String filePath) {
		String str;
		int last = filePath.lastIndexOf(".");
		str = filePath.substring(last);
		return str;
	}

	/**
	 * 行によって文言を付与するメソッド
	 * @param list
	 * @return
	 */
	public ArrayList<String> addString(ArrayList<String> list) {
		ArrayList<String> strArr = new ArrayList<>();
		String str = "";
		int count = 0;

		for (int i = 0; i < list.size(); i++) {
			str = list.get(i).trim().replace("　", "").replace("\n", "");
			switch (i % 7) {
			case 0:
				str = "問" + (count + 1) + "　" + str;
				count++;
				break;
			case 1:
				str = "\t1:" + str;
				break;
			case 2:
				str = "\t2:" + str;
				break;
			case 3:
				str = "\t3:" + str;
				break;
			case 4:
				str = "\t4:" + str;
				break;
			}
			strArr.add(str);
		}
		return strArr;
	}

	/**
	 * 文章を正規表現に従って切り分けるメソッド
	 * @param searchString
	 * @param textForQuestions
	 * @return
	 */
	public ArrayList<String> makeQuestions(String input, ArrayList<String> list) {
		ArrayList<String> questions = new ArrayList<>();
		for (int i = 0; i < list.size();) {
			String exercise = "";
			String str = list.get(i);

			if(str.matches("")) {
				i++;
				continue;
			}

			//「指定した単語／正規表現で始まる」文章を見つけたら各問題の最初に書き加える
			if (str.matches(input)) {
				exercise += str + "\n";
				i++;
				str = list.get(i);
			}
			if (i == list.size() - 1) {
				break;
			}
			//上記に該当しない文章への処理
			//「指定した単語／正規表現」にぶつかるまで読み込んだ文章を書き加える
			//ぶつかったらexercise（１問）をquestionsリストにadd
			while (true) {
				if (!str.matches(input)) {
					exercise += str + "\n";
					i++;
					str = list.get(i);
				} else {
					questions.add(exercise);
					break;
				}
				if (i == list.size() - 1) {
					break;
				}
			}
			if (i == list.size() - 1) {
				break;
			}
		}
		return questions;
	}
}