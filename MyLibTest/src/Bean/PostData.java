/**
 * 
 */
package Bean;

/***
 * @Class Name : PostData
 * @Description : �� ������ ���� Ŭ����
 * @since 2015. 1. 5.
 * @author grapegirl
 * @version 1.0
 */
public class PostData {

	/** ���� */
	private String	m_title		= null;

	/** ������� */
	private String	m_date		= null;

	/** ���� */
	private String	m_contents	= null;

	/**
	 * ������
	 */
	public PostData(String title, String contents, String date) {
		m_title = title;
		m_date = date;
		m_contents = contents;
	}

	/**
	 * @Method : Ÿ��Ʋ�� ��ȯ�ϴ� �޼ҵ�
	 * @return Ÿ��Ʋ
	 */
	public String getTitle() {
		return m_title;
	}

	/**
	 * @Method : ��¥�� ��ȯ�ϴ� �޼ҵ�
	 * @return Ÿ��Ʋ
	 */
	public String getDate() {
		return m_date;
	}

	/***
	 * @Description : ������ ��ȯ�ϴ� �޼ҵ�
	 * @Return ����
	 */
	public String getContents() {
		return m_contents;
	}

	/**
	 * @Description : Ÿ��Ʋ�� �����ϴ� �޼ҵ�
	 * @Return Ÿ��Ʋ
	 */
	public void setTitle(String title) {
		m_title = title;
	}

	/**
	 * @Description : ������ �����ϴ� �޼ҵ�
	 * @Return ����
	 */
	public void setContents(String contents) {
		m_contents = contents;
	}

	/**
	 * @Description : ������ڸ� �����ϴ� �޼ҵ�
	 * @Return �������
	 */
	public void setDate(String date) {
		m_date = date;
	}
}
