package common;

import common.network.CommandResult;
import common.network.Request;

public abstract class DataManager {
    public abstract CommandResult add(Request<?> request);

    public abstract CommandResult addIfMax(Request<?> request);

    public abstract CommandResult addIfMin(Request<?> request);

    public abstract CommandResult show(Request<?> request);

    public abstract CommandResult clear(Request<?> request);

    public abstract CommandResult info(Request<?> request);

    public abstract CommandResult help(Request<?> request);

    public abstract CommandResult filterContainsName(Request<?> request);

    public abstract CommandResult filterStartsWithDescription(Request<?> request);

    public abstract CommandResult countLessThanAges(Request<?> request);

    public abstract CommandResult removeById(Request<?> request);

    public abstract CommandResult removeGreater(Request<?> request);

    public abstract CommandResult updateId(Request<?> request);

    public void save() {
    }
}