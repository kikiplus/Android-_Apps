package Bean;

/***
 * @Class Name :
 * @Description :
 * @since 2015. 6. 10.
 * @version 1.0
 * @author mh kim
 */
public class StationData {

	/** 노드 번호 **/
	private String	node;
	/** 역이름 **/
	private String	stationNm;
	/** 이전역 노드 **/
	private String	prevNode;
	/** 다음역 노드 **/
	private String	nextNode;
	/** 이미지 좌표 X좌표 **/
	private int		imgX;
	/** 이미지 좌표 Y좌표 **/
	private int		imgY;
	/** 서울시 역코드 **/
	private String	stationCd;
	/** 호선 **/
	private String	line;
	/** 외부 코드 **/
	private String	frCd;
	/** 터치 X좌표 **/
	private int		clickX;
	/** 터치 Y좌표 **/
	private int		clickY;
	/** WPS 좌표(위도) **/
	private float	wpsX;
	/** WPS 좌표(경도) **/
	private float	wpsY;

	/**
	 * 생성자
	 */
	public StationData() {
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setStationNm(String stationNm) {
		this.stationNm = stationNm;
	}

	public void setPrevNode(String prevNode) {
		this.prevNode = prevNode;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public void setImgX(int imgX) {
		this.imgX = imgX;
	}

	public void setImgY(int imgY) {
		this.imgY = imgY;
	}

	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setFrCd(String frCd) {
		this.frCd = frCd;
	}

	public void setClickX(int clickX) {
		this.clickX = clickX;
	}

	public void setClickY(int clickY) {
		this.clickY = clickY;
	}

	public void setWpsX(float wpsX) {
		this.wpsX = wpsX;
	}

	public void setWpsY(float wpsY) {
		this.wpsY = wpsY;
	}

	public String getNode() {
		return node;
	}

	public String getStationNm() {
		return stationNm;
	}

	public String getPrevNode() {
		return prevNode;
	}

	public String getNextNode() {
		return nextNode;
	}

	public int getImgX() {
		return imgX;
	}

	public int getImgY() {
		return imgY;
	}

	public String getStationCd() {
		return stationCd;
	}

	public String getLine() {
		return line;
	}

	public String getFrCd() {
		return frCd;
	}

	public int getClickX() {
		return clickX;
	}

	public int getClickY() {
		return clickY;
	}

	public float getWpsX() {
		return wpsX;
	}

	public float getWpsY() {
		return wpsY;
	}

	@Override
	public String toString() {
		return node + "," + stationNm + "," + prevNode + "," + nextNode + "," + imgX + "," + imgY + "," + stationCd + "," + line + "," + frCd + "," + clickX
				+ "," + clickY + "," + wpsX + "," + wpsY;
	}

}
