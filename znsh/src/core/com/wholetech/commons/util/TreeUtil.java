package com.wholetech.commons.util;

import java.util.ArrayList;
import java.util.List;

public final class TreeUtil {
	
	  /**
	   * @author
	   * @version 1.0
	   * @功能描述： 根据数据结构判断是否有下级单位
	   * @see
	   * @param rsList
	   *          需要判断的数据集合
	   * @return List<Object[]> 带有下级关系的树形结构数据
	   * @exception JeawException
	   */
	  public static List<Object[]> changeTreeData(List<Object[]> rsList) {

	    List<Object[]> rtList = null;
	    if (rsList != null && rsList.size() != 0) {
	      rtList = new ArrayList<Object[]>();
	      for (Object rsObj1 : rsList) {
	        Object[] rsObj = (Object[]) rsObj1;
	        Object[] rtObj = new Object[rsObj.length];
	        for (int i = 0; i < rsObj.length; i++) {
	          if (i == rsObj.length - 1) {
	            rtObj[i] = ((Number) rsObj[i]).intValue() > 0 ? true : false;
	          } else {
	            rtObj[i] = rsObj[i];
	          }
	        }
	        rtList.add(rtObj);
	      }
	    }
	    return rtList;
	  }
	  
	  /**
	   * @author
	   * @version 1.0
	   * @功能描述： 根据数据结构判断是否有下级单位
	   * @see
	   * @param rsList
	   *          需要判断的数据集合
	   * @return List<Object[]> 带有下级关系的树形结构数据
	   * @exception JeawException
	   */
	  public static List<Object[]> changeTreeDataEx(List<Object[]> rsList,String[] OpenStr) {

		    List<Object[]> rtList = null;
		    if (rsList != null && rsList.size() != 0) {
		      rtList = new ArrayList<Object[]>();
		      for (Object rsObj1 : rsList) {
		        Object[] rsObj = (Object[]) rsObj1;
		        Object[] rtObj = new Object[rsObj.length+1];
		        for (int i = 0; i < rsObj.length; i++) {
		          if (i == rsObj.length - 1) {
		            rtObj[i] = ((Number) rsObj[i]).intValue() > 0 ? true : false;
		          } else {
		            rtObj[i] = rsObj[i];
		          }
		        }
		        for(String openNodeid : OpenStr){
		        	rtObj[rsObj.length] = rsObj[0].toString().equals(openNodeid) ? true : false;		        		
		        }
		        rtList.add(rtObj);
		      }
		    }
		    return rtList;
		  }
}