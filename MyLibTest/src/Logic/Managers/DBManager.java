/**
 * 
 */
package Logic.Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * *
 * 
 * @Class Name : DBManager
 * @Description : DB Acess 클래스
 * @since 2015. 6. 12.
 * @version 1.0
 * @author grapegirl
 */
public class DBManager extends SQLiteOpenHelper {

	/** 데이타베이스 이름 */
	private String			mDatabaseName		= null;
	/** 데이타베이스 버전 */
	private int				mDatabaseVersion	= 0;
	/** 데이타베이스 */
	private SQLiteDatabase	mDatabase			= null;

	/**
	 * @param context
	 *            컨텍스트
	 * @param name
	 *            이름
	 * @param factory
	 *            팩토리
	 * @param version
	 *            버전
	 */
	public DBManager(Context context, String name, CursorFactory factory, int version) {
		super( context, name, factory, version );
	}

	public DBManager(Context context, String name, CursorFactory factory, int version, String DatabaseName) {
		super( context, name, factory, version );
		mDatabaseName = DatabaseName;
		mDatabaseVersion = version;
		mDatabase = getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * 질의문 실행 메소드
	 * 
	 * @param sql
	 *            질의문
	 */
	public void executeTable(String sql) {
		try {
			mDatabase = getWritableDatabase();
			mDatabase.execSQL( sql );
		} catch ( Exception e ) {
			System.out.println( "질의문 실행 에러" );
		}
	}

	/**
	 * 닫기 메소드
	 */
	public void close() {
		mDatabase = getWritableDatabase();
		mDatabase.close();
	}

}
