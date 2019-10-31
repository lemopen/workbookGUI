package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DisplayController {
	private String filePath = "";
	private String splitWord = "";
	private String choice = "";
	private ArrayList<String> questions = new ArrayList<>();
	private ArrayList<String> answers = new ArrayList<>();

	//アクセサ
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}

	public ArrayList<String> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<String> questions) {
		this.questions = questions;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}


	//FXML要素
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private RadioButton btn1;
	@FXML
	private RadioButton btn2;
	@FXML
	private RadioButton btn3;
	@FXML
	private RadioButton btn4;
	@FXML
	private TextField userInput;
	@FXML
	Button submit;
	@FXML
	private TextArea exText;
	@FXML
	private Label importLabel;
	@FXML
	private Label submitLabel;
	@FXML
	private Label choiceLabel;
	@FXML
	private ImageView imageView;
	@FXML
	private AnchorPane anchor;


	//FXMLアクセサ
	public String getSubmitLabel() {
		return submitLabel.getText();
	}

	public void setSubmitLabel(String text) {
		this.submitLabel.setText(text);
	}

	public String getChoiceLabel() {
		return choiceLabel.getText();
	}

	public void setChoiceLabel(String text) {
		this.choiceLabel.setText(text);
	}

	/*Exerciseタブのボタン
	 *クリック毎にユーザーの回答をchoiceに代入
	 */
	public void onClickBtn1() {
		setChoice("1");
		choiceLabel.setStyle("-fx-text-fill: black;");
		choiceLabel.setText("選んでいる回答は\"" + getChoice() + "\"です");
	}
	@FXML
	public void onClickBtn2() {
		setChoice("2");
		choiceLabel.setStyle("-fx-text-fill: black;");
		choiceLabel.setText("選んでいる回答は\"" + getChoice() + "\"です");
	}
	@FXML
	public void onClickBtn3() {
		setChoice("3");
		choiceLabel.setStyle("-fx-text-fill: black;");
		choiceLabel.setText("選んでいる回答は\"" + getChoice() + "\"です");
	}
	@FXML
	public void onClickBtn4() {
		setChoice("4");
		choiceLabel.setStyle("-fx-text-fill: black;");
		choiceLabel.setText("選んでいる回答は\"" + getChoice() + "\"です");
	}

	/*Importタブ
	 * 入力された相対パスでプロジェクト直下のファイルを確認し、リストに入れる
	 * submitflagでメソッドを切り替える
	 */
	int submitflag = 0;
	ArrayList<String> tempList = getQuestions();
	public void submit() {
		switch(submitflag) {
		case 0:
			//読み込み、リスト化
			this.filePath = userInput.getText();
			if (filePath.equals("")) {
				submitLabel.setText(Sentence.noFilePath);
			}else {
				submitLabel.setText("");
				Import imp = new Import();
				boolean flag = imp.isFilePresent(filePath);
				//ファイルがあればフラグを1に変更し、以下を実行
				if(flag) {
					submitflag = 1;
					setQuestions(imp.importFile(getFilePath()));
					userInput.setText("");
					userInput.setPromptText(Sentence.secondPromptText);
					importLabel.setText("\t" + Sentence.importComp);
					submitLabel.setText(Sentence.whichWord);
				}else {
					userInput.setText("");
					submitLabel.setText(Sentence.fileNotFound);
				}
			}
			break;
		case 1:
			String str = userInput.getText();
			//単語の入力がなければ要求、あれば問題作成
			if(str.equals("")) {
				submitLabel.setStyle("-fx-text-fill: red;");
				submitLabel.setText(Sentence.putAnyWord + "\n" + Sentence.whichWord);
			}else {
				submitflag = 2;//3回目以降クリックしても何もしない
				submitLabel.setStyle("-fx-text-fill: black;");
				importLabel.setText("\t" + Sentence.makeQuestionsComp);
				ArrayList<String> list = getQuestions();
				setQuestions(buildQuestions(list, str));
				submitLabel.setText(Sentence.goToExercise);
			}
		}
	}



	/*Exerciseタブ
	 *
	 *①問題を成形して表示
	 *②回答ボタンを押すごとに正誤を表示
	 */

	int count = 0;
	//①
	@FXML
	public void startExercise() {
		//解答を読み込み、成形してリストに代入
		getAnsAndRemoveSpaces();
		//第1問を表示する（第2問以降はsendChoiceメソッドで出題）
		showExercise(count);
	}
	//
	//	//②
	//	//回答と答えを比較し、ラベルに正誤、ボックスに次の問題を表示
	//	@FXML
	public void sendChoice() throws InterruptedException {
		if(count == getAnswers().size() -1) {
		}else {
			String ans = getAnswers().get(count);
			if (getChoice().equals("")) {
				choiceLabel.setText("先に回答を選んでください");
				choiceLabel.setStyle("-fx-text-fill: red;");
			} else {
				if (compareChoiceWithAnswer()) {
					choiceLabel.setText("正解！");
					choiceLabel.setStyle("-fx-text-fill: black;");
				} else {
					choiceLabel.setText("残念…正解は" + ans + "です");
					choiceLabel.setStyle("-fx-text-fill: red;");
				}
				//少し時間を置いて
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1500);
						} catch (Exception e) {
						}
						Platform.runLater(new Runnable() {//次の問題を表示する
							@Override
							public void run() {
								count++;
								choiceLabel.setText("");
								if(count == getAnswers().size() -1) {
									choiceLabel.setText("全問終了しました！");
									choiceLabel.setStyle("-fx-text-fill: black;");
								}else {
									unselect();
									setChoice("");
									choiceLabel.setStyle("-fx-text-fill: black;");
									showExercise(count);
								}
							}
						});
					}
				})
				.start();
			}
		}
	}


	/**リストを読み込み問題を正規表現で成形するメソッド
	 */
	public ArrayList<String> buildQuestions(ArrayList<String> list,String str) {
		Exercise ex = new Exercise();
		ArrayList<String> strArr = new ArrayList<>();

		//内部処理：問題に「第〇問」などの文言を付加・空白を除去
		strArr = ex.addString(list);

		//正規表現に従って分割
		//問\d*.*
		strArr = ex.makeQuestions(splitWord, strArr);
		return strArr;
		//成形した問題をリストにセット
	}
	/**答えを読み込み＋成形してリストにセットするメソッド
	 */
	public void getAnsAndRemoveSpaces() {
		Exercise ex = new Exercise();
		//解答（ファイル名+ans.拡張子）を読み込む
		String ansFilePath = ex.removeExtension(filePath) + "ans" + ex.getExtension(filePath);
		Import imp = new Import();
		ArrayList<String> list = imp.importFile(ansFilePath);
		//解答から文字を除去
		ArrayList<String> strArr = new ArrayList<>();
		String str = "";
		for(int i = 0; i < list.size(); i++) {
			str = list.get(i).trim().replace("　", "").replace("\n", "").replace("答え:", "");
			strArr.add(str);
		}
		setAnswers(strArr);
	}

	/**問題を表示するメソッド
	 */
	public void showExercise(int count) {
		ArrayList<String> list = getQuestions();
		String str = list.get(count);
		exText.setText(str);
	}
	/**ユーザーの回答と答えを比較するメソッド
	 */
	public boolean compareChoiceWithAnswer() {
		String str = getChoice();
		String ans = getAnswers().get(count);
		if(str.equals(ans)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * ラジオボタンの選択を解除するメソッド
	 */
	public void unselect() {
		String str = getChoice();
		switch(str) {
		case "1":
			btn1.setSelected(false);
			break;
		case "2":
			btn2.setSelected(false);
			break;
		case "3":
			btn3.setSelected(false);
			break;
		case "4":
			btn4.setSelected(false);
			break;
		}
	}
}
