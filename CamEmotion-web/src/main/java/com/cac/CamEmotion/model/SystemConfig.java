package com.cac.CamEmotion.model;

public class SystemConfig {

	private Integer id;

	private String webip;

	private Integer memcachedport;

	private String sharename;

	private String facecontrastkey;

	private String faceidentificationkey;

	private String analysismainkey;

	private String affdexkey;

	private String recordvideokey;

	private String framedatakey;

	private String camerareadykey;

	private String camerapixelkey;

	private String sharepathconnkey;

	private String startoffkey;

	private String hosttimekey;

	private String comparetimekey;

	private String algorithmtimekey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWebip() {
		return webip;
	}

	public void setWebip(String webip) {
		this.webip = webip == null ? null : webip.trim();
	}

	public Integer getMemcachedport() {
		return memcachedport;
	}

	public void setMemcachedport(Integer memcachedport) {
		this.memcachedport = memcachedport;
	}

	public String getSharename() {
		return sharename;
	}

	public void setSharename(String sharename) {
		this.sharename = sharename == null ? null : sharename.trim();
	}

	public String getFacecontrastkey() {
		return facecontrastkey;
	}

	public void setFacecontrastkey(String facecontrastkey) {
		this.facecontrastkey = facecontrastkey == null ? null : facecontrastkey.trim();
	}

	public String getFaceidentificationkey() {
		return faceidentificationkey;
	}

	public void setFaceidentificationkey(String faceidentificationkey) {
		this.faceidentificationkey = faceidentificationkey == null ? null : faceidentificationkey.trim();
	}

	public String getAnalysismainkey() {
		return analysismainkey;
	}

	public void setAnalysismainkey(String analysismainkey) {
		this.analysismainkey = analysismainkey == null ? null : analysismainkey.trim();
	}

	public String getAffdexkey() {
		return affdexkey;
	}

	public void setAffdexkey(String affdexkey) {
		this.affdexkey = affdexkey == null ? null : affdexkey.trim();
	}

	public String getRecordvideokey() {
		return recordvideokey;
	}

	public void setRecordvideokey(String recordvideokey) {
		this.recordvideokey = recordvideokey == null ? null : recordvideokey.trim();
	}

	public String getFramedatakey() {
		return framedatakey;
	}

	public void setFramedatakey(String framedatakey) {
		this.framedatakey = framedatakey == null ? null : framedatakey.trim();
	}

	public String getCamerareadykey() {
		return camerareadykey;
	}

	public void setCamerareadykey(String camerareadykey) {
		this.camerareadykey = camerareadykey == null ? null : camerareadykey.trim();
	}

	public String getCamerapixelkey() {
		return camerapixelkey;
	}

	public void setCamerapixelkey(String camerapixelkey) {
		this.camerapixelkey = camerapixelkey == null ? null : camerapixelkey.trim();
	}

	public String getSharepathconnkey() {
		return sharepathconnkey;
	}

	public void setSharepathconnkey(String sharepathconnkey) {
		this.sharepathconnkey = sharepathconnkey == null ? null : sharepathconnkey.trim();
	}

	public String getStartoffkey() {
		return startoffkey;
	}

	public void setStartoffkey(String startoffkey) {
		this.startoffkey = startoffkey == null ? null : startoffkey.trim();
	}

	public String getHosttimekey() {
		return hosttimekey;
	}

	public void setHosttimekey(String hosttimekey) {
		this.hosttimekey = hosttimekey == null ? null : hosttimekey.trim();
	}

	public String getComparetimekey() {
		return comparetimekey;
	}

	public void setComparetimekey(String comparetimekey) {
		this.comparetimekey = comparetimekey == null ? null : comparetimekey.trim();
	}

	public String getAlgorithmtimekey() {
		return algorithmtimekey;
	}

	public void setAlgorithmtimekey(String algorithmtimekey) {
		this.algorithmtimekey = algorithmtimekey == null ? null : algorithmtimekey.trim();
	}
}