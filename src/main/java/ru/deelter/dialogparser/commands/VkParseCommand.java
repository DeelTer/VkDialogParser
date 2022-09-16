package ru.deelter.dialogparser.commands;

import org.jetbrains.annotations.NotNull;
import ru.deelter.dialogparser.managers.VkParseManager;
import ru.deelter.dialogparser.utils.VkMessage;
import ru.milkshake.core.commands.console.api.AbstractConsoleCommand;

import java.io.File;
import java.util.List;

public class VkParseCommand extends AbstractConsoleCommand {

	public VkParseCommand() {
		super("parse", "Parse vk html files");
	}

	@Override
	public void execute(@NotNull String[] args) {
		List<File> folderFiles = VkParseManager.getFilesInFolder();
		if (folderFiles.isEmpty()) return;

		List<VkMessage> messages = VkParseManager.getMessagesFromFiles(folderFiles);
		VkParseManager.sendMessagesToChannel(messages);
	}
}
