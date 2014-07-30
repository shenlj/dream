package com.mrs.sysmgr.web.action;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.UserData;
import com.mrs.sysmgr.service.ZnshGlService;
import com.wholetech.commons.file.FileAction;
import com.wholetech.commons.service.BaseService;

/**
 * @author yourname here
 *         ===============================================================================
 *         修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 *         修改作者 日期 修改内容
 *         ===============================================================================
 */

@ParentPackage("default")
@Namespace("/sysmgr")
@Results( {

})
public class ZnshSysmgrAction extends FileAction {

	private ZnshGlService znshGlService;
	private File file;

	public void setZnshGlService(ZnshGlService znshGlService) {

		this.znshGlService = znshGlService;
	}

	public File getFile() {

		return file;
	}

	public void setFile(File file) {

		this.file = file;
	}

	protected BaseService getBaseService() {

		return znshGlService;
	}

	public void listPkrwWithXnxq() {
		byte[] d = new byte[6];
		d[0] = new Integer(1).byteValue();
		d[1] = new Integer(2).byteValue();
		d[2] = new Integer(12).byteValue();
		d[3] = new Integer(13).byteValue();
		d[4] = new Integer(14).byteValue();
		d[5] = new Integer(15).byteValue();
		UserData d1 = new UserData();
		d1.setId("2");
		d1.setBirthday("ddd");
		znshGlService.updateUser("1", d1);
	}
	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：用户注册
	 * @see
	 * @param userid
	 *            用户id
	 * @param password
	 *            用户密码
	 * @return 注册成功返回true，否则返回false
	 * @exception JeawException
	 */
	public void register() {

//		String userid = getParameter("userid");
//		String password = getParameter("password");
//		boolean flg = this.znshGlService.register(userid, password);
//		renderJson(flg);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 校验用户与用户密码是否正确
	 * @see
	 * @param userid
	 *            用户id
	 * @param password
	 *            用户密码
	 * @return 校验通过则返回true，否则返回false
	 * @exception JeawException
	 */
	public void checkUser() {

		// String userid = getParameter("userid");
		// String password = getParameter("password");
		// boolean flg = this.znshGlService.checkUser(userid, password);
		// renderJson(flg);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：于注册成功的用户，进行下一步的用户个人信息补充操作
	 * @see
	 * @param userid
	 *            用户id
	 * @param paramMap
	 *            姓名(name)、身高(height)、体重(weight)、腰围(waist)、步幅(stride)、出生年月(birthday)
	 * @return 更新完成后返回true,如果用户名不存在，则返回false
	 * @exception JeawException
	 */
	public void updateUser() {

		String userid = getParameter("userid");
		HashMap paramMap = new HashMap();
		String name = getParameter("name") == null ? "" : (String) getParameter("name");
		long height = getParameter("height") == null ? 0 : Long.valueOf(getParameter("height"));
		double weight = getParameter("weight") == null ? 0 : Double.valueOf(getParameter("weight"));
		long waist = getParameter("waist") == null ? 0 : Long.valueOf(getParameter("waist"));
		double stride = getParameter("stride") == null ? 0 : Double.valueOf(getParameter("stride"));
		String birthday = getParameter("birthday") == null ? "" : (String) getParameter("birthday");
		paramMap.put(name, name);
		paramMap.put(height, height);
		paramMap.put(weight, weight);
		paramMap.put(waist, waist);
		paramMap.put(stride, stride);
		paramMap.put(birthday, birthday);
		// boolean flg = this.znshGlService.updateUserMap(userid, paramMap);
		// renderJson(flg);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 通过接口将指定日期的用户和运动数据
	 * @see
	 * @param userid
	 *            用户id
	 * @param stepDate
	 *            日期
	 * @return 返回当日数据包，如果没有查询到，则返回 null
	 * @throws ParseException
	 * @exception JeawException
	 */
	public void getUserInfo() throws ParseException {

		// String userid = getParameter("userid");
		// String stepDate = getParameter("stepDate");
		// byte[] rtn = this.znshGlService.getStepData(userid, DateUtil.parse(stepDate));
		// getResponse().reset();
		// getResponse().setCharacterEncoding("utf-8");
		// getResponse().setHeader("Content-disposition", "attachment;");
		// try {
		// OutputStream os = getResponse().getOutputStream();
		// os.write(rtn);
		// getResponse().flushBuffer();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：
	 * @see
	 * @param userid
	 *            用户id
	 * @param stepDate
	 *            运动日期
	 * @param userinfo
	 *            用户信息
	 * @return 更新成功后返回true,否则返回false
	 * @throws ParseException
	 * @exception JeawException
	 */
	public void updateUserInfo() throws ParseException {

		// String userid = getParameter("userid");
		// String stepDate = getParameter("stepDate");
		// InputStream fileIO = null;
		// try {
		// fileIO = new BufferedInputStream(new FileInputStream(file));
		// byte[] userinfo = new byte[fileIO.available()];
		// HashMap paraMap = new HashMap();
		// // 读取输入流
		// fileIO.read(userinfo);
		// boolean flg = this.znshGlService.updateUserInfo(userid, DateUtil.parse(stepDate), userinfo, file.getName());
		// renderJson(flg);
		// } catch (FileNotFoundException e) {
		// renderJson(false);
		// e.printStackTrace();
		// } catch (IOException e) {
		// renderJson(false);
		// e.printStackTrace();
		// }

	}
}
