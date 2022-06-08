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
package net.kyori.adventure.identity;

import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.internal.Internals;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class PlayerIdentityImpl implements PlayerIdentity {
  private final UUID uuid;
  private final Component name;
  private final @Nullable Component teamName;

  PlayerIdentityImpl(final @NotNull UUID uuid, final @NotNull Component name, final @Nullable Component teamName) {
    this.uuid = uuid;
    this.name = name;
    this.teamName = teamName;
  }

  @Override
  public @NotNull UUID uuid() {
    return this.uuid;
  }

  @Override
  public @NotNull Component name() {
    return this.name;
  }

  @Override
  public @Nullable Component teamName() {
    return this.teamName;
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if (this == other) return true;
    if (!(other instanceof PlayerIdentity)) return false;
    final PlayerIdentity that = (PlayerIdentity) other;
    return this.uuid == that.uuid() && this.name == that.name() && this.teamName == that.teamName();
  }

  @Override
  public int hashCode() {
    int result = this.uuid.hashCode();
    result = 31 * result + this.name.hashCode();
    result = 31 * result + Objects.hashCode(this.teamName);
    return result;
  }

  @Override
  public String toString() {
    return Internals.toString(this);
  }
}
