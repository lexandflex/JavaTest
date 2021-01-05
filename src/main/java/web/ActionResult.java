package web;

public class ActionResult {
	private String url;
	private ActionResultType type;

	public ActionResult(String url, ActionResultType type) {
		this.url = url;
		this.type = type;
	}

	public ActionResult(String url) {
		this(url, ActionResultType.REDIRECT);
	}

	public String getUrl() {
		return url;
	}

	public ActionResultType getType() {
		return type;
	}
}
