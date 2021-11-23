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
package org.apache.ibatis.binding;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperProxy.MapperMethodInvoker;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * MapperProxy 的工厂类，负责生成实际的Mapper代理类，
 * 并且缓存实际的Mapper方法和MapperMethodInvoker的对应关系
 *
 * MapperMethodInvoker就是对Mapper中方法的一个封装
 *
 * @author Lasse Voss
 */
public class MapperProxyFactory<T> {

  /**
   * 这个是在addMapper时，添加进来的Mapper接口的Class类型
   */
  private final Class<T> mapperInterface;

  /**
   * 此Mapper接口中的Mapper方法及其对应的MapperMethodInvoker，这个是做了一个反射的调用封装
   *
   * 主要实现类有 PlainMethodInvoker 和 DefaultMethodInvoker，也就是普通的接口方法，和默认方法
   */
  private final Map<Method, MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

  public MapperProxyFactory(Class<T> mapperInterface) {
    this.mapperInterface = mapperInterface;
  }

  public Class<T> getMapperInterface() {
    return mapperInterface;
  }

  public Map<Method, MapperMethodInvoker> getMethodCache() {
    return methodCache;
  }

  @SuppressWarnings("unchecked")
  protected T newInstance(MapperProxy<T> mapperProxy) {
    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
  }

  public T newInstance(SqlSession sqlSession) {
    // MapperProxy是一个InvocationHandler的实现类，也就是我们的代理逻辑
    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
    return newInstance(mapperProxy);
  }

}
