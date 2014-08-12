package com.wholetech.commons;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClaimedProperties2JsonConverter extends Obj2JsonConverter {

  private static Logger logger = LoggerFactory.getLogger(ClaimedProperties2JsonConverter.class);
  private Set<String> propertySet = new HashSet();

  public ClaimedProperties2JsonConverter(String[] paramArrayOfString) {

    addPropertySet(paramArrayOfString);
  }

  public ClaimedProperties2JsonConverter(String paramString) {

    String[] arrayOfString = paramString.split(",");
    addPropertySet(arrayOfString);
  }

  public ClaimedProperties2JsonConverter(Collection<String> paramCollection) {

    this.propertySet.addAll(paramCollection);
  }

  private void addPropertySet(String[] paramArrayOfString) {

    String[] arrayOfString = paramArrayOfString;
    int i = arrayOfString.length;
    for (int j = 0; j < i; ++j) {
      String str = arrayOfString[j];
      this.propertySet.add(str);
    }
  }

  @Override
  public JSONObject toJSONObject(Object paramObject) {

    return toJSONObject(paramObject, this.propertySet);
  }

  private JSONObject toJSONObject(Object paramObject, Set<String> paramSet) {

    Object localObject1;
    if (paramSet.isEmpty() || paramObject == null) {
      return new JSONObject();
    }
    JSONObject localJSONObject = null;
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(paramSet);
    final HashMap map = new HashMap();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext()) {
      String str1 = (String) localIterator.next();
      map.put(str1, str1);
    }
    if (!localHashSet.isEmpty()) {
      localObject1 = new JsonConfig();
      super.defaultSetupJsonConfig((JsonConfig) localObject1);
      ((JsonConfig) localObject1).setJsonPropertyFilter(new PropertyFilter()
      {

        public boolean apply(Object paramObject1, String paramString, Object paramObject2)
        {

          return map.get(paramString) == null;
        }
      });
      localJSONObject = JSONObject.fromObject(paramObject, (JsonConfig) localObject1);
    } else {
      localJSONObject = new JSONObject();
    }

    return localJSONObject;
  }

}
