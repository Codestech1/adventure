package net.kyori.adventure.text.serializer.nbt;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ClickEventSerializer {

  private static final String ACTION = "action";
  private static final String VALUE = "value";

  private static final String CLICK_EVENT_OPEN_URL = "open_url";
  private static final String CLICK_EVENT_OPEN_FILE = "open_file";
  private static final String CLICK_EVENT_RUN_COMMAND = "run_command";
  private static final String CLICK_EVENT_SUGGEST_COMMAND = "suggest_command";
  private static final String CLICK_EVENT_CHANGE_PAGE = "change_page";
  private static final String CLICK_EVENT_COPY_TO_CLIPBOARD = "copy_to_clipboard";

  private ClickEventSerializer() {
  }

  static @Nullable ClickEvent deserialize(@NotNull CompoundBinaryTag tag) {
    ClickEvent.Action action;
    String actionString = tag.getString(ACTION);

    if (actionString.isEmpty()) {
      return null;
    }

    switch (actionString) {
      case CLICK_EVENT_OPEN_URL:
        action = ClickEvent.Action.OPEN_URL;
        break;
      case CLICK_EVENT_OPEN_FILE:
        action = ClickEvent.Action.OPEN_FILE;
        break;
      case CLICK_EVENT_RUN_COMMAND:
        action = ClickEvent.Action.RUN_COMMAND;
        break;
      case CLICK_EVENT_SUGGEST_COMMAND:
        action = ClickEvent.Action.SUGGEST_COMMAND;
        break;
      case CLICK_EVENT_CHANGE_PAGE:
        action = ClickEvent.Action.CHANGE_PAGE;
        break;
      case CLICK_EVENT_COPY_TO_CLIPBOARD:
        action = ClickEvent.Action.COPY_TO_CLIPBOARD;
        break;
      default:
        return null;
    }

    return ClickEvent.clickEvent(action, tag.getString(VALUE));
  }

  static @NotNull CompoundBinaryTag serialize(@NotNull ClickEvent event) {
    ClickEvent.Action action = event.action();
    String actionString;

    switch (action) {
      case OPEN_URL:
        actionString = CLICK_EVENT_OPEN_URL;
        break;
      case OPEN_FILE:
        actionString = CLICK_EVENT_OPEN_FILE;
        break;
      case RUN_COMMAND:
        actionString = CLICK_EVENT_RUN_COMMAND;
        break;
      case SUGGEST_COMMAND:
        actionString = CLICK_EVENT_SUGGEST_COMMAND;
        break;
      case CHANGE_PAGE:
        actionString = CLICK_EVENT_CHANGE_PAGE;
        break;
      case COPY_TO_CLIPBOARD:
        actionString = CLICK_EVENT_COPY_TO_CLIPBOARD;
        break;
      default:
        // Never called, but needed for proper compilation
        throw new IllegalStateException("Unknown click event action: " + action);
    }

    return CompoundBinaryTag.builder()
      .putString(ACTION, actionString)
      .putString(VALUE, event.value())
      .build();
  }
}
