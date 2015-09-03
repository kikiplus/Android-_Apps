package com.kiki.View.Bean;

/***
 * @Class Name : PostData
 * @Description : 게시글 클래스
 * @since 2015. 6. 23.
 * @author grapegirl
 * @version 1.0
 */
public class PostData {

	/** 타이틀*/
	private String	m_title		= null;

	/** 등록 날짜*/
	private String	m_date		= null;

	/** 내용*/
	private String	m_contents	= null;

	/** 순번 */
	private int mNo = 0;

	/**
	 * 생성자
	 */
	public PostData(String title, String contents, String date, int no) {
		m_title = title;
		m_date = date;
		m_contents = contents;
		mNo = no;
	}

	/**
	 * @Method : 타이틀 반환 메소드
	 * @return 타이틀
	 */
	public String getTitle() {
		return m_title;
	}

	/**
	 * @Method : 등록 날짜 반환 메소드
	 * @return 등록 날짜
	 */
	public String getDate() {
		return m_date;
	}

	/***
	 * @Description : 내용 반환 메소드
	 * @Return 내용
	 */
	public String getContents() {
		return m_contents;
	}

	/**
	 * @Description : 타이틀 설정 메소드
	 * @param title 타이틀
	 */
	public void setTitle(String title) {
		m_title = title;
	}

	/**
	 * @Description : 내용 설정 메소드
	 * @Return contents 내용
	 */
	public void setContents(String contents) {
		m_contents = contents;
	}

	/**
	 * @Description : 등록날짜 설정 메소드
	 * @Return date 등록날짜
	 */
	public void setDate(String date) {
		m_date = date;
	}


	/***
	 * @Description : 순번 반환 메소드
	 * @Return 순번
	 */
	public int getmNo() {
		return mNo;
	}

	/**
	 * @Description : 순번 설정 메소드
	 * @Return mNo 순번
	 */
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
}
