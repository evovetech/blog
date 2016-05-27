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

public final class MapperWrapper<R, T> implements MapperExtension<R, T> {
    private final Mapper<? extends R, ? super T> actual;

    public MapperWrapper(Mapper<? extends R, ? super T> actual) {
        // We need to put a T in and get an R out
        // Wildcards allow the actual implementation to handle:
        //      1) any ancestor of T (i.e. T is String, actual can handle Object or any superclass of String)
        // and create:
        //      2) any descendant of R (i.e. R is number, actual can return Integer or any subclass of Number)
        this.actual = actual;
    }

    public static <R, T> Mapper<R, T> create(Mapper<? extends R, ? super T> mapper) {
        return new MapperWrapper<>(mapper);
    }

    public static <R2, R1 extends R2, T1, T2 extends T1> Mapper<R2, T2> cast(Mapper<R1, T1> mapper) {
        return new MapperWrapper<R2, T2>(mapper);
    }

    @Override public R map(T t) {
        // Safely put in a T and return an R with the proper bounded wildcards
        return actual.map(t);
    }

    @Override public <T2 extends T> Mapper<R, T2> cast() {
        return new MapperWrapper<>(actual);
    }
}
