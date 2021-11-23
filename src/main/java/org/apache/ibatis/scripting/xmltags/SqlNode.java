/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.scripting.xmltags;

/**
 *
 * 用来表示SQL节点，一个完整的SQL语句可能会由多个部分组成，原因就在于mybatis支持的动态sql
 * <p>
 *   &lt;select&gt; <br/>
 *
 *     select * from test <br/>
 *     where 1=1 <br/>
 *     &lt;if test='a==b'&gt; <br/>
 *     and <br/>
 *     &lt;/if&gt; <br/>
 *
 *   &lt;/select&gt;
 * </p>
 *
 *   上面这个片段就会有两段SqlNode
 *
 * @author Clinton Begin
 */
public interface SqlNode {
  /**
   *  对SqlNode进行处理
   */
  boolean apply(DynamicContext context);
}
