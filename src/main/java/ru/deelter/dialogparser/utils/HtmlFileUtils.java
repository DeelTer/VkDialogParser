package ru.deelter.dialogparser.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlFileUtils {

	@Contract("_ -> new")
	public static @NotNull List<VkMessage> parseVkMessages(@NotNull File file) {
		if (!file.getName().endsWith(".html")) throw new RuntimeException("Not html file");

		List<VkMessage> messages = new ArrayList<>();
		Document document;
		try {
			document = Jsoup.parse(file, "Windows-1251");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (document == null) return messages;
		Elements divs = document.getElementsByClass("item");
		for (Element div : divs) {
			VkMessage message = new VkMessage();

			Element messageBlock = div.getElementsByClass("message").first();
			Element attachmentsBlock = messageBlock.getElementsByClass("attachment").first();
			Elements headerBlock = div.getElementsByClass("message__header");
			if (headerBlock != null) {
				String[] nameAndTime = headerBlock.first().text().trim().split(", ");
				String name = nameAndTime[0];
				String timeRaw = nameAndTime[1];

				message.setAuthorName(name);
				message.setTimeRaw(timeRaw);
			}
			for (Element element : messageBlock.getElementsByIndexEquals(1)) {
				String content = element.text().trim();
				if (content.isBlank()) continue;
				message.setContent(content);
			}
			if (attachmentsBlock != null) {
				Elements attachmentLink = attachmentsBlock.getElementsByClass("attachment__link");
				message.setAttachmentUrl(attachmentLink.text());
			}
			messages.add(message);
		}
		Collections.reverse(messages);
		return messages;
	}
}