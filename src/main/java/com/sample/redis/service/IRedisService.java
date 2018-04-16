package com.sample.redis.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 * @see
 * @since JDK1.8
 */
public interface IRedisService {

  /**
   * get object form redis by key
   *
   * @param key
   * @return
   */
  Object getObject(String key);

  /**
   * get class by key
   *
   * @param key
   * @param requiredType
   * @return
   */
  <T> T get(String key, Class<? extends Serializable> requiredType);

  /**
   * remove key
   *
   * @param key
   * @return
   */
  void remove(String key);

  /**
   * @param key
   * @param object
   * @return
   */
  void saveObject(String key, Serializable object);

  /**
   * @param key
   * @param object
   * @param timeout
   * @param unit
   * @return
   */
  void saveWithExpireTime(String key, Serializable object, long timeout, TimeUnit unit);

  /**
   * @param key
   * @return
   */
  boolean hasKey(String key);

  // /**
  // * @param key
  // * @return
  // */
  //  Map<String, String> getRedisMap(String key);
  //
  // /**
  // * @param key
  // * @param map
  // */
  //  void saveRedisMap(String key, Map<String, String> map);
  //
  //  void saveRedisMap(String key, String fieid, String value);
  //
  //  List<? extends Serializable> getObjectList(String key);
  //
  //  void saveObjectList(String key, List<? extends Serializable>
  // list);
  //
  //  void removeRedisList(String key);
  //
  //  void saveRedisList(String key, List<? extends Serializable> list);
  //
  //  List<String> getRedisList(String key);
  //
  // /**
  // * 删除所有的key
  // */
  //  void delete(Serializable key);
  // /**
  // * @param keys
  // */
  //  void delete(Collection<Serializable> keys);
  //
  // /**
  // * @param serializable
  // * @param values
  // * @return
  // */
  //  Long addToSet(Serializable serializable, Serializable... values);
  //
  // /**
  // * @param key
  // * @param values
  // * @return
  // */
  //  Long removeFromSet(Serializable key, Serializable... values);
  //
  // /**
  // * @param key
  // * @return
  // */
  //  Set<Serializable> getSet(Serializable key);
  //
  // /**
  // * @param key
  // * @return
  // */
  //  Set<Object> hashKeys(Serializable key);
  //
  // /**
  // * @param key
  // * @return
  // */
  //  List<Object> hashVals(Serializable key);
  //
  // /**
  // * @param key
  // * @param hashkey
  // * @param val
  // */
  //  void hashPut(Serializable key, Serializable hashkey, Serializable
  // val);
  //
  // /**
  // * @param key
  // * @param map
  // */
  //  void hashPutAll(Serializable key, Map<? extends Serializable, ?
  // extends Serializable> map);
  //
  // /**
  // * @param key
  // * @param hashkey
  // * @param val
  // */
  //  void hashPutIfAbsent(Serializable key, Serializable hashkey,
  // Serializable val);
  //
  // /**
  // * @param key
  // * @param hashkey
  // * @return
  // */
  //  Boolean hashExistKey(Serializable key, Serializable hashkey);
  //
  // /**
  // * @param key
  // * @param hashkey
  // * @return
  // */
  //  Object hashGet(Serializable key, Serializable hashkey);
  //
  // /**
  // * @param key
  // * @return
  // */
  //  Map<Object, Object> hashEntries(Serializable key);
  //
  // /**
  // * @param key
  // * @param hashkey
  // */
  //  void hashDelete(Serializable key, Serializable hashkey);
  //
  // /**
  // * @param key
  // * @param hashKeys
  // */
  //  void hashDelete(Serializable key, Serializable... hashKeys);
  //
  // /**
  // * @param key
  // * @param set
  // */
  //  void saveObjToSet(String key, Set<String> set);
  //
  // /**
  // * @param pattern
  // * @return
  // */
  //  Set<Serializable> getKeys(String pattern);
}
