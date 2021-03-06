/*
 * Copyright 2013 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.map.mutable.primitive;

import com.gs.collections.impl.test.Verify;
import org.junit.Test;

public class UnmodifiableLongCharHashMapTest
{
    @Test
    public void serializedForm()
    {
        Verify.assertSerializedForm(
                1L,
                "rO0ABXNyAEljb20uZ3MuY29sbGVjdGlvbnMuaW1wbC5tYXAubXV0YWJsZS5wcmltaXRpdmUuVW5t\n"
                        + "b2RpZmlhYmxlTG9uZ0NoYXJIYXNoTWFwAAAAAAAAAAECAAFMAANtYXB0ADlMY29tL2dzL2NvbGxl\n"
                        + "Y3Rpb25zL2FwaS9tYXAvcHJpbWl0aXZlL011dGFibGVMb25nQ2hhck1hcDt4cHNyAD1jb20uZ3Mu\n"
                        + "Y29sbGVjdGlvbnMuaW1wbC5tYXAubXV0YWJsZS5wcmltaXRpdmUuTG9uZ0NoYXJIYXNoTWFwAAAA\n"
                        + "AAAAAAEMAAB4cHcIAAAAAD8AAAB4",
                new UnmodifiableLongCharHashMap(new LongCharHashMap()));
    }
}