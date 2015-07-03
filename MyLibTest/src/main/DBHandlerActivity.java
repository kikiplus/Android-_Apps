package main;

import android.app.Activity;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Managers.DBManager;
import Managers.FileMgr;

/***
 * @Class Name :
 * @Description :
 * @since 2015. 6. 12.
 * @version 1.0
 * @author grapegirl
 */
public class DBHandlerActivity extends Activity {

	private SQLiteDatabase	mDB;
	private DBManager		mDBManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );

		Toast.makeText( getApplicationContext(), "DB 생성 시작", Toast.LENGTH_LONG ).show();

		mDBManager = new DBManager( this, "metro.db", null, 1 );

		mDBManager.executeTable( "DELETE FROM [STATION_LIST] ;" );
		mDBManager
				.executeTable( "CREATE TABLE IF NOT EXISTS [STATION_LIST] (  [IDX] TEXT,  [STATION_NM] TEXT,   [PREV_IDX] TEXT,  [NEXT_IDX] TEXT,  [IMG_X] TEXT, [IMG_Y] TEXT,  [STATION_CD] TEXT,  [LINE] TEXT,"
						+ "[FR_CODE] TEXT,   [CLICK_X] TEXT,  [CLICK_Y] TEXT,  [WPS_X] TEXT,  [WPS_Y] TEXT);" );

		FileMgr fileMgr = new FileMgr();
		try {

			// 1. csv 파일 읽어온다.
			AssetManager assetMgr = getAssets();
			InputStream is = assetMgr.open( "test.csv" );
			fileMgr.readStationListFile( is );
			ArrayList<String> fileList = fileMgr.getFileList();
			// 2. 질의문 생성하여 테이블에 insert한다.
			for ( int i = 0, n = fileList.size(); i < n; i++ ) {
				String data = fileList.get( i );
				String[] temps = data.split( "," );
				String query = "INSERT INTO [STATION_LIST] VALUES (" + temps[0] + ",'" + temps[1] + "'," + temps[2] + "," + temps[3] + "," + temps[4] + ","
						+ temps[5] + "," + temps[6] + ",'" + temps[7] + "','" + temps[8] + "'," + temps[9] + "," + temps[10] + "," + temps[11] + ","
						+ temps[12] + ");";
				mDBManager.executeTable( query );
			}
			
			mDBManager.close();
			Toast.makeText( getApplicationContext(), "DB 생성 끝", Toast.LENGTH_LONG ).show();
			System.out.println( "질의문 실행 완료==" );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

	}
}
