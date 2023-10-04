package handlers.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbsCommandHandler {

    abstract Object handle(Message message);
}
