package client.commands;

public class Command {
    public abstract class Command {
        private Object argument;

        public abstract void execute(String[] args);

        public Command() {}

        public Object getArgument() {
            return argument;
        }

        public void setArgument(Object argument) {
            this.argument = argument;
        }
        public abstract String getName();
        public abstract String getDescription();
    }
}
