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

import junit.framework.Assert;

import org.junit.Test;

public class MapperTest {
    @Test public void map_withWildcards() throws Exception {
        // Main (used) mapper, returns a number from a string
        Mapper<Number, String> wrapper;

        // Implementation mapper, returns an Integer from an Object
        Mapper<Integer, Object> integerMapper = new Mapper<Integer, Object>() {
            @Override public Integer map(Object o) {
                if (o instanceof Number) {
                    return ((Number) o).intValue();
                } else if (o instanceof String) {
                    return Integer.parseInt((String) o);
                }
                return 0;
            }
        };

        //  i.e. new MapperWrapper<Number, String>(Mapper<Integer extends Number, Object super String> integerMapper);
        wrapper = new MapperWrapper<Number, String>(integerMapper);
        Number one = wrapper.map("1");
        Assert.assertTrue(one instanceof Integer);
        Assert.assertEquals(one, 1);
    }
}
