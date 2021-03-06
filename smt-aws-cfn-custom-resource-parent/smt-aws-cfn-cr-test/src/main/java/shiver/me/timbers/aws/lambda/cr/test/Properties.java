/*
 * Copyright 2017 Karl Bennett
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package shiver.me.timbers.aws.lambda.cr.test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Properties {

    public static Property property(String name, Object value) {
        return new Property(name, value);
    }

    public static List<Property> properties(Property... properties) {
        final int length = properties.length;
        return IntStream.range(0, length)
            .mapToObj(index -> index == length - 1 ? properties[index].withLast(true) : properties[index])
            .collect(toList());
    }

}
