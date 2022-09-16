package ru.deelter.dialogparser.utils;

public class VkMessage {

	private String authorName;
	private String content;
	private String timeRaw;
	private String attachmentUrl;

	public VkMessage(String authorName, String content, String timeRaw, String attachmentUrl) {
		this.authorName = authorName;
		this.content = content;
		this.timeRaw = timeRaw;
		this.attachmentUrl = attachmentUrl;
	}

	public VkMessage() {
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeRaw() {
		return timeRaw;
	}

	public void setTimeRaw(String timeRaw) {
		this.timeRaw = timeRaw;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	@Override
	public String toString() {
		String main = authorName + " [" + timeRaw + "]: " + content;
		if (this.attachmentUrl != null)
			main += " [" + this.attachmentUrl + "]";
		return main;
	}
}
