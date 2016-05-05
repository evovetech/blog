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
        // Defined mapper, returns a number from a string
        Mapper<Number, String> wrapper = new MapperWrapper<Number, String>(
                // Implementation mapper, returns an Integer from an Object
                new Mapper<Integer /*extends Number*/, Object /*super String*/>() {
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
                });

        Number one = wrapper.map("1");
        Assert.assertTrue(one instanceof Integer);
        Assert.assertEquals(one, 1);
    }

    @Test public void map_two() throws Exception {
        MapperExtension<Integer, Object> integerMapper = new IntegerMapper();
        Mapper<Number, String> wrap = MapperWrapper.<Number, String>create(integerMapper);
        Mapper<Object, Number> wrap2 = MapperWrapper.<Object, Number>create(integerMapper);
        Mapper<Number, String> castStatic = MapperWrapper.cast(integerMapper);
        Mapper<Object, Number> castStatic2 = MapperWrapper.cast(integerMapper);
        // fails to compile
//        Mapper<Object, Object> castStatic3 = MapperWrapper.cast(castStatic2);
        Mapper<Object, Object> castStatic3 = MapperWrapper.cast(integerMapper);

        // fails to compile
//        Mapper<Number, String> castInstance = integerMapper.cast();
        // best we can do with instance
        Mapper<Integer, String> castInstance = integerMapper.cast();
        Mapper<Integer, Number> castInstance2 = integerMapper.cast();

        // whatever
        Mapper<? super Integer, ? super String> whatever1 = integerMapper;//.cast();

        // compile error
//        Mapper<? super Integer, ? extends String> whatever2 = integerMapper;//.cast();
        Mapper<? super Integer, ? extends String> whatever2 = integerMapper.cast();
//        Integer two = whatever2.map("haha");
//        Integer two2 = MapperWrapper.map(whatever2, "haha");

        Mapper<? extends Integer, ? super String> whatever3 = integerMapper;//.cast();

        // compile error
//        Mapper<? extends Integer, ? extends String> whatever4 = integerMapper;//.cast();
        Mapper<? extends Integer, ? extends String> whatever4 = integerMapper.cast();

        // Doesn't compile
//        Mapper<Object, Object> mapper6 = MapperWrapper.cast(mapper5);
    }
}
