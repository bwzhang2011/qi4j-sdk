/*
 * Copyright (c) 2012, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *     You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.api.value;

import java.io.InputStream;
import org.qi4j.api.type.ValueType;
import org.qi4j.functional.Function2;

/**
 * Use a ValueDeserializer to create new values instances from serialized state.
 *
 * <p>
 *     Serialized state must be one of:
 * </p>
 * <ul>
 *     <li>a ValueComposite,</li>
 *     <li>a Collection,</li>
 *     <li>a Map,</li>
 *     <li>a plain value.</li>
 * </ul>
 * <p>
 *     Nested plain values, EntityReferences, Collections, Maps, ValueComposites are supported.
 *     EntityReferences are deserialized as their identity string.
 * </p>
 * <p>
 *     Plain values can be one of:
 * </p>
 * <ul>
 *     <li>String,</li>
 *     <li>Boolean,</li>
 *     <li>Integer,</li>
 *     <li>Long,</li>
 *     <li>Short,</li>
 *     <li>Byte,</li>
 *     <li>Float,</li>
 *     <li>Double,</li>
 *     <li>BigInteger,</li>
 *     <li>BigDecimal,</li>
 *     <li>Date,</li>
 *     <li>DateTime (JodaTime),</li>
 *     <li>LocalDateTime (JodaTime),</li>
 *     <li>LocalDate (JodaTime).</li>
 * </ul>
 * <p>
 *     Having type information in the serialized payload allows to keep actual ValueComposite types and by so
 *     circumvent {@link AmbiguousTypeException} when deserializing.
 * </p>
 */
public interface ValueDeserializer
{

    /**
     * Factory method for a deserialize function.
     *
     * @param <T> the parametrized function return type
     * @return a deserialization function
     */
    <T> Function2<ValueType, String, T> deserialize();

    /**
     * Deserialize a value from a state.
     *
     * @param <T> the parametrized returned type
     * @param type the value type
     * @param input the state
     * @return the value
     * @throws ValueSerializationException if the deserialization failed
     */
    <T> T deserialize( ValueType type, String input )
        throws ValueSerializationException;

    /**
     * Deserialize a value from a JSON state.
     *
     * @param <T> the parametrized returned type
     * @param type the value type
     * @param input the state stream
     * @return the value
     * @throws ValueSerializationException if the deserialization failed
     */
    <T> T deserialize( ValueType type, InputStream input )
        throws ValueSerializationException;
}