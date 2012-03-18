package com.greenapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

public class HtmlParser {
	private static final Pattern PRODUCT_NAME_PRICE_PATTERN =
			Pattern.compile("owb63p\">([^<]+).+zdi3pb\">([^<]+)");
	
	public static String getDescription(String siteContent) {
	    Matcher matcher = PRODUCT_NAME_PRICE_PATTERN.matcher(siteContent);
	    String content = "";
	    if (matcher.find()) {
	      content = unescapeHTML(matcher.group(1));
	    }
	    return content;
	}
	
    private static String unescapeHTML(String raw) {
        return Html.fromHtml(raw).toString();
    }
}
