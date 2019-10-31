package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportDao {
	public static ArrayList<String> textToArrayList(String pathName) throws IOException {
		// 1.ファイルのパスを指定する
		File file = new File(pathName);

		// 2.ファイルが存在しない場合
		if (!file.exists()) {
			return null;
		}
		// 1-2.1文字ずつ読み込んで返却
		FileReader fr = new FileReader(pathName);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> strArray = new ArrayList<>();
		String str = "";
		while ((str = br.readLine()) != null) {
			strArray.add(str + "\n");
		}
		fr.close();
		return strArray;
	}
}
