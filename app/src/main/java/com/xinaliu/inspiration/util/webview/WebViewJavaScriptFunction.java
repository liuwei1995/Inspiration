package com.xinaliu.inspiration.util.webview;

public interface WebViewJavaScriptFunction {

	void onJsFunctionCalled(String tag);

	/**
	 *  view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
	 + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
	 */
	/**
	 * 分享
	 * @param data d
	 */
	void showShare(Object data);

}
