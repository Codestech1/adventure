/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
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
package net.kyori.adventure.text.minimessage;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import net.kyori.adventure.text.minimessage.transformation.TransformationRegistry;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.util.Buildable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * MiniMessage is a textual representation of components.
 *
 * <p>This class allows you to serialize and deserialize them, strip
 * or escape them.</p>
 *
 * @since 4.10.0
 */
public interface MiniMessage extends ComponentSerializer<Component, Component, String>, Buildable<MiniMessage, MiniMessage.Builder> {

  /**
   * Gets a simple instance without markdown support.
   *
   * @return a simple instance
   * @since 4.10.0
   */
  static @NotNull MiniMessage miniMessage() {
    return MiniMessageImpl.INSTANCE;
  }

  /**
   * Escapes all known tokens in the input message, so that they are ignored in deserialization.
   *
   * <p>Useful for untrusted input.</p>
   *
   * <p>Only globally known tokens will be escaped. Use the overload that takes a {@link PlaceholderResolver} if placeholders should be handled.</p>
   *
   * @param input the input message, with tokens
   * @return the output, with escaped tokens
   * @since 4.10.0
   */
  @NotNull String escapeTokens(final @NotNull String input);

  /**
   * Escapes all known tokens in the input message, so that they are ignored in deserialization.
   *
   * <p>Useful for untrusted input.</p>
   *
   * @param input the input message, with tokens
   * @param placeholders the placeholder resolver adding known placeholders
   * @return the output, with escaped tokens
   * @since 4.10.0
   */
  @NotNull String escapeTokens(final @NotNull String input, final @NotNull PlaceholderResolver placeholders);

  /**
   * Removes all supported tokens in the input message.
   *
   * <p>Useful for untrusted input.</p>
   *
   * <p>Only globally known tokens will be stripped. Use the overload that takes a {@link PlaceholderResolver} if placeholders should be handled.</p>
   *
   * @param input the input message, with tokens
   * @return the output, without tokens
   * @since 4.10.0
   */
  @NotNull String stripTokens(final @NotNull String input);

  /**
   * Removes all known tokens in the input message, so that they are ignored in deserialization.
   *
   * <p>Useful for untrusted input.</p>
   *
   * @param input the input message, with tokens
   * @param placeholders the placeholder resolver adding known placeholders
   * @return the output, without tokens
   * @since 4.10.0
   */
  @NotNull String stripTokens(final @NotNull String input, final @NotNull PlaceholderResolver placeholders);

  /**
   * Parses a string into an component.
   *
   * @param input the input string
   * @return the output component
   * @since 4.10.0
   */
  default Component parse(final @NotNull String input) {
    return this.deserialize(input);
  }

  /**
   * Deserializes a string into a component, with a placeholder resolver to parse placeholders of the form {@code <key>}.
   *
   * <p>Placeholders will be resolved from this resolver before the resolver provided in the builder is used.</p>
   *
   * @param input the input string
   * @param placeholderResolver the placeholder resolver
   * @return the output component
   * @since 4.10.0
   */
  @NotNull Component deserialize(final @NotNull String input, final @NotNull PlaceholderResolver placeholderResolver);

  /**
   * Creates a new {@link MiniMessage.Builder}.
   *
   * @return a builder
   * @since 4.10.0
   */
  static Builder builder() {
    return new MiniMessageImpl.BuilderImpl();
  }

  /**
   * A builder for {@link MiniMessage}.
   *
   * @since 4.10.0
   */
  interface Builder extends Buildable.Builder<MiniMessage> {

    /**
     * Uses the supplied transformation registry.
     *
     * @param transformationRegistry the transformation registry to use
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder transformations(final @NotNull TransformationRegistry transformationRegistry);

    /**
     * Modify the set transformation registry.
     *
     * <p>By default, this will start out with a registry of all default transformations.</p>
     *
     * @param modifier an action to perform on the registry builder
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder transformations(final @NotNull Consumer<TransformationRegistry.Builder> modifier);

    /**
     * Sets the placeholder resolver.
     *
     * <p>This placeholder resolver will be used after any placeholder resolved provided in {@link MiniMessage#deserialize(String, PlaceholderResolver)}.</p>
     *
     * @param placeholderResolver the placeholder resolver to use, if any
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder placeholderResolver(final @Nullable PlaceholderResolver placeholderResolver);

    /**
     * Allows to enable strict mode (disabled by default).
     *
     * <p>By default, MiniMessage will allow non-{@link net.kyori.adventure.text.minimessage.transformation.Inserting Inserting} tags to be implicitly closed. When strict mode
     * is enabled, all non-inserting tags which are {@code <opened>} must be explicitly {@code </closed>} as well.</p>
     *
     * @param strict if strict mode should be enabled
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder strict(final boolean strict);

    /**
     * Print debug information to the given output (disabled by default).
     *
     * <p>Debug output includes detailed information about the parsing process to help debug parser behavior.</p>
     *
     * @param debugOutput if debug mode should be enabled
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder debug(final @Nullable Appendable debugOutput);

    /**
     * If in lenient mode, MiniMessage will output helpful messages.
     *
     * <p>This method allows you to change how they should be printed. By default, they will be printed to standard out.</p>
     *
     * @param consumer the error message consumer
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder parsingErrorMessageConsumer(final @NotNull Consumer<List<String>> consumer);

    /**
     * Specify a function that takes the component at the end of the parser process.
     * <p>By default, this compacts the resulting component with {@link Component#compact()}.</p>
     *
     * @param postProcessor method run at the end of parsing
     * @return this builder
     * @since 4.10.0
     */
    @NotNull Builder postProcessor(final @NotNull UnaryOperator<Component> postProcessor);

    /**
     * Builds the serializer.
     *
     * @return the built serializer
     * @since 4.10.0
     */
    @Override
    @NotNull MiniMessage build();
  }
}