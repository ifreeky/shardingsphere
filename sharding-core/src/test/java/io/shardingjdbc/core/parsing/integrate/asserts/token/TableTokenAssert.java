/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package io.shardingjdbc.core.parsing.integrate.asserts.token;

import io.shardingjdbc.core.parsing.integrate.asserts.SQLStatementAssertMessage;
import io.shardingjdbc.core.parsing.integrate.jaxb.token.ExpectedTableToken;
import io.shardingjdbc.core.parsing.integrate.jaxb.token.ExpectedTokens;
import io.shardingjdbc.core.parsing.parser.token.SQLToken;
import io.shardingjdbc.core.parsing.parser.token.TableToken;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Table token assert.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
final class TableTokenAssert {
    
    private final SQLStatementAssertMessage assertMessage;
    
    void assertTableTokens(final List<SQLToken> actual, final ExpectedTokens expected) {
        List<TableToken> tableTokens = getTableTokens(actual);
        assertThat(assertMessage.getFullAssertMessage("Table tokens size error: "), tableTokens.size(), is(expected.getTableTokens().size()));
        int count = 0;
        for (ExpectedTableToken each : expected.getTableTokens()) {
            assertTableToken(tableTokens.get(count), each);
            count++;
        }
    }
    
    private void assertTableToken(final TableToken actual, final ExpectedTableToken expected) {
        assertThat(assertMessage.getFullAssertMessage("Table tokens begin position assertion error: "), actual.getBeginPosition(), is(expected.getBeginPosition()));
        assertThat(assertMessage.getFullAssertMessage("Table tokens original literals assertion error: "), actual.getOriginalLiterals(), is(expected.getOriginalLiterals()));
    }
    
    private List<TableToken> getTableTokens(final List<SQLToken> actual) {
        List<TableToken> result = new ArrayList<>(actual.size());
        for (SQLToken each : actual) {
            if (each instanceof TableToken) {
                result.add((TableToken) each);
            }
        }
        return result;
    }
}
