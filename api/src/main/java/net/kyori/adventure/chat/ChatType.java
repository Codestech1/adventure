/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2022 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.chat;

import java.util.Objects;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

/**
 * A type of chat.
 *
 * @since 4.12.0
 * @sinceMinecraft 1.19
 */
public interface ChatType extends Examinable, Keyed {
  // todo should these be in another class/interface so as not to conflict with any implementations?
  /**
   * A chat message from a player.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType CHAT = new ChatTypeImpl(Key.key("chat"));

  /**
   * A chat message from the "system" or server.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType SYSTEM = new ChatTypeImpl(Key.key("system"));

  /**
   * A game information message, displaying in the action bar.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType GAME_INFO = new ChatTypeImpl(Key.key("game_info"));

  /**
   * A message sent as a result of using the {@code /msg} command.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType MSG_COMMAND = new ChatTypeImpl(Key.key("say_command"));

  /**
   * A message sent as a result of using the {@code /teammsg} command.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType TEAM_MSG_COMMAND = new ChatTypeImpl(Key.key("team_msg_command"));

  /**
   * A message sent as a result of using the {@code /me} command.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  ChatType EMOTE_COMMAND = new ChatTypeImpl(Key.key("emote_command"));

  /**
   * A message sent as a result of using the {@code /tellraw} command.
   *
   * @since 4.12.0
   * @sinceMinecraft 1.19
   */
  // todo TELLRAW or TELL_RAW? mc is inconsistent here (see teammsg)
  ChatType TELL_RAW_COMMAND = new ChatTypeImpl(Key.key("tellraw_command"));

  /**
   * Creates a new chat type with a given key.
   *
   * @param key the key
   * @return the chat type
   * @since 4.12.0
   */
  static @NotNull ChatType chatType(final @NotNull Key key) {
    return new ChatTypeImpl(Objects.requireNonNull(key, "key"));
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(ExaminableProperty.of("key", this.key()));
  }
}
