/*
 * Copyright 2016 Evove.tech
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

package tech.evove.sample;

public class IntegerMapper implements MapperExtension<Integer, Object> {
    @Override public Integer map(Object o) {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        String s = null;
        if (o instanceof String) {
            s = (String) o;
        } else if (o != null) {
            s = o.toString();
        }
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return 0;
    }

    @Override public <T2> Mapper<Integer, T2> cast() {
        return MapperWrapper.create(this);
    }
}
