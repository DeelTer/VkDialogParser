package ru.deelter.dialogparser.managers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.deelter.dialogparser.VkParserModule;
import ru.deelter.dialogparser.utils.HtmlFileUtils;
import ru.deelter.dialogparser.utils.VkMessage;
import ru.milkshake.core.utils.discord.DiscordChannels;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VkParseManager {

	private static final TextChannel OUTPUT_CHANNEL = DiscordChannels.getTextChannel("vk");
	private static final Logger LOGGER = LogManager.getLogger("VkParser");
	private static final Color COLOR_YOU = Color.decode("#7ce1fa");
	private static final Color COLOR_OTHER = Color.decode("#fa7cae");

	public static @NotNull List<File> getFilesInFolder() {
		LOGGER.info("Start parsing files..");
		List<File> files = new ArrayList<>();
		VkParserModule module = VkParserModule.getInstance();
		File dataFolder = module.getDataFolder();
		if (!dataFolder.exists()) {
			LOGGER.warn("Directory not found. Creating new..");
			dataFolder.mkdirs();
		}
		File[] folderFiles = dataFolder.listFiles();
		if (folderFiles == null) {
			LOGGER.warn("Folder files is null");
			return files;
		}
		LOGGER.info("Found " + (folderFiles.length + 1) + " files");
		for (File file : folderFiles) {
			if (file.isDirectory()) continue;
			if (!file.getName().endsWith(".html")) continue;
			files.add(file);
		}
		LOGGER.info("Start sorting " + files.size() + " files");
		files.sort((value, value2) -> {
			String name = value.getName().replaceAll("[a-zA-Z.]", "");
			String name2 = value2.getName().replaceAll("[a-zA-Z.]", "");
			return Integer.compare(Integer.parseInt(name2), Integer.parseInt(name));
		});
		LOGGER.info("Sorting ended");
		return files;
	}

	public static @NotNull List<VkMessage> getMessagesFromFiles(@NotNull List<File> htmlFiles) {
		int filesSize = htmlFiles.size();
//		int total = filesSize * 50;
		int counter = 1;
		for (File file : htmlFiles) {
			String name = file.getName();
			String queueName = String.format("%s [%s/%s]", name, counter++, filesSize);
			LOGGER.info("Process " + queueName);
			return getMessagesFromFile(file);
		}
		return Collections.emptyList();
	}

	public static @NotNull @Unmodifiable List<VkMessage> getMessagesFromFile(@NotNull File htmlFile) {
		String name = htmlFile.getName();
		List<VkMessage> messages = HtmlFileUtils.parseVkMessages(htmlFile);
		LOGGER.info(String.format("Found %s messages in file %s%n", messages.size(), name));
		return Collections.emptyList();
	}

	public static void sendMessagesToChannel(@NotNull List<VkMessage> messages) {
		int filesSize = messages.size();
		int counter = 1;
		for (VkMessage message : messages) {
			LOGGER.info(String.format("Process messages [%s/%s]", counter++, filesSize));
			String attachmentUrl = message.getAttachmentUrl();
			String authorName = message.getAuthorName();
			if (attachmentUrl != null && attachmentUrl.isBlank())
				attachmentUrl = null;
			MessageEmbed embed = new EmbedBuilder()
					.setAuthor(authorName)
					.setColor(getAuthorColor(authorName))
					.setDescription(message.getContent())
					.setFooter(message.getTimeRaw())
					.setImage(attachmentUrl)
					.build();
			OUTPUT_CHANNEL.sendMessageEmbeds(embed).complete();
		}
	}

	private static @NotNull Color getAuthorColor(@NotNull String name) {
		if (name.toLowerCase().contains("вы")) return COLOR_YOU;
		return COLOR_OTHER;
	}

}
