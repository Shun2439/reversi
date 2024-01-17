package reversi;

import java.util.Scanner;
import java.util.Random;

public class Main {

	/*
	 * 全5回中1回目
	 * 
	 * 20分 ・main関数で2次元配列の宣言 ・盤面の初期化 20分 ・盤面の表示 20分
	 * ・石が置けるかのチェック（左上を参考に全方向対応する、さらにできる人はループでまとめる） 20分 ・盤面全体でチェック結果の表示
	 */

	// 石がない状態を0と定義
	final static int EMPTY = 0;

	// 黒石がある状態を1と定義
	final static int BLACK = 1;

	// 白石がある状態を-1と定義
	final static int WHITE = -1;

	// 盤面の大きさを定義（周囲を番兵で囲むため+2する）
	final static int SIZE = (8 + 2);

	static void InitBoardRandom(int[][] board) {
		Random rand = new Random();

		// 行番号を格納する変数 row を宣言する
		// 列番号を格納する変数 col を宣言する
		int row, col;

		// いったんすべてEMPTYに設定（外側をEMPTYの壁にしておく）
		for (row = 0; row < SIZE - 2; row++) {
			for (col = 0; col < SIZE - 2; col++) {
				board[row][col] = 0;
			}
		}

		// 使用する盤面のみをランダムに設定
		for (row = 1; row < SIZE - 1; row++) {
			for (col = 1; col < SIZE - 1; col++) {
				board[row][col] = rand.nextInt(3) - 1;
			}
		}
	}

	// 盤面を表示する関数
	// 戻り値 : なし
	// 引数 : 盤面 board
	static void DispBoard(int[][] board) {
		// 行番号を格納する変数 row を宣言する
		// 列番号を格納する変数 col を宣言する
		int row, col;

		// 横軸のラベルを表示（全角）
		System.out.println(" ａｂｃｄｅｆｇｈ");

		for (row = 1; row < SIZE - 1; row++) {
			// 縦軸のラベルを表示（半角）
			System.out.print(row);

			for (col = 1; col < SIZE - 1; col++) {
				switch (board[row][col]) {
				// 黒石の場合は文字を黒、背景を緑で●（全角の丸）を表示し、背景と文字の色をリセット
				case BLACK:
					System.out.print("\u001b[30;42m〇\u001b[0m");
					break;

				// 白石の場合は文字を白（指定なし）、背景を緑で●（全角の丸）を表示し、背景と文字の色をリセット
				case WHITE:
					System.out.print("\u001b[42m〇\u001b[0m");
					break;

				// 石がない場合は背景を緑で （全角のスペース）を表示し、背景と文字の色をリセット
				case EMPTY:
					System.out.print("\u001b[42m　\u001b[0m");
					break;
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	// CheckBoard関数の変更
	// 石がある場合の処理までを貼り付ける
	static int CheckBoard(int put_row, int put_col, int color, int[][] board) {
		// 行番号を格納する変数 row を宣言する
		// 列番号を格納する変数 col を宣言する
		int row, col;

		// 各方向で挟んでいる相手の石の数を格納する変数 enemy_count を宣言する
		int enemy_count;

		// すでに石がある場合は置けないので0を返す
		if (board[put_row][put_col] != EMPTY) {
			return 0;
		}

		// チェックする方向（行）を格納する変数 vec_row を宣言する
		// チェックする方向（列）を格納する変数 vec_col を宣言する
		int vec_row, vec_col;

		// チェックする方向（行）を-1（上の行）, 0（同じ行）, 1（下の行）で繰り返す
		for (vec_row = -1; vec_row <= 1; vec_row++) {
			// チェックする方向（列）を-1（左の列）, 0（同じ列）, 1（右の列）で繰り返す
			for (vec_col = -1; vec_col <= 1; vec_col++) {
				// 行と列両方とも0の場合は何もしない
				// その他の場合は裏返せるかのチェックを行う（前回作成した部分をベースに作成する）
				if (vec_row == 0 && vec_col == 0)
					continue;
			}

			// 挟んでいる相手の石の数を0に初期化する
			enemy_count = 0;
			// 石を置く場所の1つ隣の位置からスタート（行）
			row = put_row + vec_row;
			// 石を置く場所の1つ隣の位置からスタート（列）
			col = put_col + vec_col;

			// 相手の石の間繰り返す
			while (board[row][col] == -color) {
				// 挟んでいる相手の石の数を1増やす
				enemy_count++;
				// 1つ隣へ移動（行）
				row += vec_row;
				// 1つ隣へ移動（列）
				col += vec_col;
			}

			// 挟んでいる相手の石が1つ以上あり、その先に自分の石がある場合は裏返せる
			if (enemy_count > 0 && board[row][col] == color) {
				// 裏返せる場合は1を返して関数終了
				return 1;
			}
		}

		return 0;
	}

	// CheckBoardTest関数の貼り付け
	static void CheckBoardTest(int[][] board) {
		// 行
		// 列
		int row, col;

		for (row = 1; row < SIZE - 1; row++) { // 外側を除いた行
			for (col = 1; col < SIZE - 1; col++) {
				// 外側を除いた列
				// 行,列の数値を表示用の文字へ変換（行：1～8を'1'(49）～'8'(56)へ、 列：1～8を'a'(97)～'h'(104)へ）
				System.out.print((row + '1' - 1) + (col + 'a' - 1));
				// 黒が置けるかをチェック
				System.out.println("黒:" + CheckBoard(row, col, BLACK, board));
				// 白が置けるかをチェック
				System.out.println("白:" + CheckBoard(row, col, WHITE, board));
			}
		}
	}

	// 裏返し処理関数
	// 戻り値 : 裏返した石の数
	// 引数1 : 石を置く位置（行）put_row
	// 引数2 : 石を置く位置（列）put_col
	// 引数3 : 置く石の色 color（相手の石は -color になる）
	// 引数4 : 盤面 board
	static int TurnOver(int put_row, int put_col, int color, int[][] board) {

		// 行番号を格納する変数 row を宣言する
		// 列番号を格納する変数 col を宣言する
		int row, col;

		// 各方向で挟んでいる相手の石の数を格納する変数 enemy_count を宣言する
		int enemy_count;

		// すでに石がある場合は置けないので0を返す
		if (board[put_row][put_col] != EMPTY) {
			return (0);
		}

		// チェックする方向（行）を格納する変数 vec_row を宣言する
		int vec_row, vec_col;

		// 裏返した相手の石の数の合計を格納する変数 enemy_count_total を宣言する（追加）
		int enemy_count_total = 0;

		// 全方向処理の部分貼り付け
		// チェックする方向（行）を-1（上の行）, 0（同じ行）, 1（下の行）で繰り返す
		for (vec_row = -1; vec_row <= 1; vec_row++) {
			// チェックする方向（列）を-1（左の列）, 0（同じ列）, 1（右の列）で繰り返す
			for (vec_col = -1; vec_col <= 1; vec_col++) {
				// 行と列両方とも0の場合は何もしない
				// その他の場合は裏返せるかのチェックを行う（前回作成した部分をベースに作成する）
				if (vec_row == 0 && vec_col == 0)
					continue;

				enemy_count = 0; // 挟んでいる相手の石の数を0に初期化する
				row = put_row + vec_row; // 石を置く場所の1つ隣の位置からスタート（行）
				col = put_col + vec_col; // 石を置く場所の1つ隣の位置からスタート（列）
				while (board[row][col] == -color) { // 相手の石の間繰り返す
					enemy_count++; // 挟んでいる相手の石の数を1増やす
					row += vec_row; // 1つ隣へ移動（行）
					col += vec_col; // 1つ隣へ移動（列）
				}

				// 挟んでいる相手の石が1つ以上あり、その先に自分の石がある場合は裏返せる
				// 裏返し処理の追加・変更(if文の中)
				if (enemy_count > 0 && board[row][col] == color) {
					// 裏返した個数を更新
					enemy_count_total = enemy_count;
					// 相手の石の位置まで1つ戻る（行）
					row -= vec_row;
					// 相手の石の位置まで1つ戻る（列）
					col -= vec_col;
					// 相手の石の間を繰り返す
					while (board[row][col] == -color) {
						// 石を裏返す
						board[row][col] = color;
						// さらに1つ戻る（行）
						row -= vec_row;
						// さらに1つ戻る（列）
						col -= vec_col;
					}
					// 自分の石を置く
					board[put_row][put_col] = color;
				}
			}
		}
		// 裏返した石の数の合計を返して関数終了
		return enemy_count_total;
	}

	class Move {
		public int row;
		public int col;
		public int value;

		public Move() {
			row = 0;
			col = 0;
			value = 0;
		}
	}

	static int SearchMove(int color, Move[] move, int[][] board) {
		int move_count = 0;

		for (int row = 1; row < SIZE - 1; row++) {
			for (int col = 1; col < SIZE - 1; col++) {
				if (CheckBoard(row, col, color, board) == 1) {
					move[move_count].row = row;
					move[move_count].col = col;
					move[move_count].value = 0;

					move_count++;
				}
			}
		}

		return move_count;
	}

	static void DispMove(int color, Move move[], int move_count, int[][] board) {
		if (color == BLACK) {
			System.out.println("黒");
		} else if (color == WHITE) {
			System.out.println("白");
		}

		for (int i = 0; i < move_count; i++) {
			System.out.print("[" + (move[i].row + '1' - 1) + (move[i].col + 'a' - 1) + ":" + move[i].value + "]");
		}

		System.out.println();
	}

	// 人が石を置く関数（パスはまだ）
	// 戻り値 : 0固定
	// 引数1 : 石の色 color
	// 引数2 : 盤面 board
	static int HumanPhase(int color, int[][] board) {
		Move[] move = new Move[(SIZE - 2) * (SIZE - 2)];

		int move_count = SearchMove(color, move, board);

		Scanner obj = new Scanner(System.in);

		if (move_count == 0) {
			System.out.println("パスしました");
			return 1;
		}

		DispMove(color, move, move_count, board);

		// 入力した文字列を格納する配列 str （要素数3以上）を宣言する
		char[] Input_Str = new char[3];

		// 石を置く位置（行番号）を格納する変数 put_row を宣言する
		// 石を置く位置（列番号）を格納する変数 put_col を宣言する
		int put_row, put_col;

		// 無限ループ開始
		while (true) {
			// "石を置く場所を入力してください（例:1c）：" と表示する
			System.out.print("石を置く場所を入力してください（例:1c）：");

			// 文字をキーボードから入力してもらい、配列に取り込む
			Input_Str = obj.nextLine().toCharArray();

			// 入力された'1'(49)～'8'(56)の文字を1～8の行番号へ変換
			put_row = Input_Str[0] - 48;

			// 入力された'a'(97)～'h'(104)の文字を1～8の列番号へ変換
			put_col = Input_Str[1] - 96;

			// 石が置ける場所ならばループを抜ける
			if (0 < CheckBoard(put_row, put_col, color, board))
				break;
			// 石が置けない場所ならば "その場所には置けません" と表示する
			else
				System.out.print("その場所には置けません");
			// 無限ループここまで
		}

		// 裏返し処理関数を呼び出す
		TurnOver(put_row, put_col, color, board);

		// 0を返す
		return 0;
	}

	public static void main(String[] args) {
		int[][] board = new int[SIZE][SIZE];

		InitBoardRandom(board);

		DispBoard(board);

		// 人が石を置く関数を呼び出す（色は黒）
		HumanPhase(BLACK, board);

		DispBoard(board);
	}
}
