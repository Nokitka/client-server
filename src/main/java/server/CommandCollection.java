package server;

import common.DataManager;
import common.network.CommandResult;
import common.network.Request;

import java.util.HashMap;

public class CommandCollection {

    private HashMap<String, Executable> commands;
    private DataManager dataManager;

    public CommandCollection(DataManager dataManager) {
        this.commands = new HashMap<>();
        initCommands();
        this.dataManager = dataManager;
    }

    private void initCommands() {
        commands.put("add", dataManager::add);
        commands.put("add_if_max", dataManager::addIfMax);
        commands.put("add_if_min", dataManager::addIfMin);
        commands.put("show", dataManager::show);
        commands.put("clear", dataManager::clear);
        commands.put("info", dataManager::info);
        commands.put("help", dataManager::help);
        commands.put("filter_contains_name", dataManager::filterContainsName);
        commands.put("filter_starts_with_description", dataManager::filterStartsWithDescription);
        commands.put("count_less_than_age", dataManager::countLessThanAges);
        commands.put("remove_by_id", dataManager::removeById);
        commands.put("remove_greater", dataManager::removeGreater);
        commands.put("update_id", dataManager::updateId);
    }

    public CommandResult executeCommand(Request<?> request) {
        if (commands.containsKey(request.command))
            return commands.get(request.command).execute(request);
        return new CommandResult(false, "Данной команды не существует");
    }
}
