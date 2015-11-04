package com.kiki.android.Logic.Managers;

import com.kiki.android.Utils.KLog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/***
 * @Class Name : 파일 관련 클래스
 * @since 2015. 6. 11.
 * @version 1.0
 * @author grapegirl
 */
public class FileMgr {

	/** 파일로 부터 읽어온 데이타 */
	private ArrayList<String>	mFileList	= null;

	/**
	 * 생성자
	 */
	public FileMgr() {
		mFileList = new ArrayList<String>();
	}

	/***
	 * 파일로 부터 읽기 메소드
	 * 
	 * @param filePath
	 *            파일 경로
	 * @throws IOException
	 */
	public void readStationListFile(String filePath) throws IOException {
		FileInputStream fis;
		try {

			fis = new FileInputStream( filePath );
			InputStreamReader isr = new InputStreamReader( fis );
			BufferedReader br = new BufferedReader( isr );
			String line = null;
			try {
				while ( ( line = br.readLine() ) != null ) {
					mFileList.add( line );
					KLog.d(this.getClass().getSimpleName(), "## line Data : " + line);
				}
			} catch ( IOException e ) {
				e.printStackTrace();
			} finally {
				br.close();
				KLog.d(this.getClass().getSimpleName(), "## file read end");
			}
		} catch ( FileNotFoundException e1 ) {
			e1.printStackTrace();
		}

	}

	/***
	 * 파일로 부터 읽기 메소드
	 * 
	 * @param is
	 *            파일 인풋 스트림
	 * @throws IOException
	 */
	public void readStationListFile(InputStream is) throws IOException {
		KLog.d(this.getClass().getSimpleName(), "## file read start");

		FileInputStream fis;
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );

			String line = null;
			try {
				while ( ( line = br.readLine() ) != null ) {
					mFileList.add( line );
				}
			} catch ( IOException e ) {
				e.printStackTrace();
			} finally {
				br.close();
				KLog.d(this.getClass().getSimpleName(), "## file read end");
			}
		} catch ( FileNotFoundException e1 ) {
			e1.printStackTrace();
		}

	}

	/***
	 * 파일 만들기 메소드
	 * 
	 * @param fileName
	 *            파일이름
	 * @throws IOException
	 */
	public void writeStationListFile(String fileName) throws IOException {
		FileWriter fw = new FileWriter( fileName );
		for ( int j = 0, m = mFileList.size(); j < m; j++ ) {
			fw.write( mFileList.get( j ).toString() + "\n" );
		}
		fw.close();
	}

	/**
	 * 파일정보를 반환하는 메소드
	 * 
	 * @return 파일에서 읽은 리스트 목록
	 */
	public ArrayList<String> getFileList() {
		return mFileList;
	}
}
