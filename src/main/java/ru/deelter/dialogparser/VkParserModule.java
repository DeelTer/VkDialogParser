package ru.deelter.dialogparser;

import ru.deelter.dialogparser.commands.VkParseCommand;
import ru.milkshake.core.managers.ConsoleCommandManager;
import ru.milkshake.core.modules.JavaModule;

public class VkParserModule extends JavaModule {

	private static VkParserModule instance;

	public static VkParserModule getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		ConsoleCommandManager.register(new VkParseCommand());
		System.out.println("Module enabled.");
	}
}