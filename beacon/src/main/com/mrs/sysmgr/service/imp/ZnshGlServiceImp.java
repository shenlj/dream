package com.mrs.sysmgr.service.imp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.opensaml.xml.util.Base64;

import com.mrs.sysmgr.common.SendSms;
import com.mrs.sysmgr.common.SysmgrConstants;
import com.mrs.sysmgr.dao.CompetitionPartyDao;
import com.mrs.sysmgr.dao.DaySleepDao;
import com.mrs.sysmgr.dao.FootTypeDao;
import com.mrs.sysmgr.dao.FriendsDao;
import com.mrs.sysmgr.dao.MedalDao;
import com.mrs.sysmgr.dao.SensorDataDao;
import com.mrs.sysmgr.dao.SensorTypeDao;
import com.mrs.sysmgr.dao.SingleStepDataDao;
import com.mrs.sysmgr.dao.StepDataDao;
import com.mrs.sysmgr.dao.TaskDao;
import com.mrs.sysmgr.dao.UserDataDao;
import com.mrs.sysmgr.dao.UserMedalDao;
import com.mrs.sysmgr.dao.UserRequestDao;
import com.mrs.sysmgr.entity.CompetitionParty;
import com.mrs.sysmgr.entity.DaySleep;
import com.mrs.sysmgr.entity.Friends;
import com.mrs.sysmgr.entity.MyFile;
import com.mrs.sysmgr.entity.StepData;
import com.mrs.sysmgr.entity.Task;
import com.mrs.sysmgr.entity.UserData;
import com.mrs.sysmgr.entity.UserRequest;
import com.mrs.sysmgr.entity.ZnshResult;
import com.mrs.sysmgr.service.ZnshGlService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.file.FileOperationService;
import com.wholetech.commons.file.FileServer;
import com.wholetech.commons.file.FileUtil;
import com.wholetech.commons.service.BaseServiceImp;
import com.wholetech.commons.util.DateUtil;
import com.wholetech.commons.util.I18NMessageReader;
import com.wholetech.commons.util.MD5Util;
import com.wholetech.commons.util.PropertyXmlMgr;
import com.wholetech.commons.util.StringUtil;

/**
 * 文件名： ZnshGlServiceImp.java
 * 作者： 署名
 * 日期： 2014-2-21
 * 功能说明：智能手环接口处理类
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@WebService(endpointInterface = "com.mrs.sysmgr.service.ZnshGlService", serviceName = "ZnshGlService", targetNamespace = "http://www.znsh.com/ws/znshAction")
@Path(value = "/znshGlService")
public class ZnshGlServiceImp extends BaseServiceImp implements ZnshGlService {

	public static HashMap authCodeMap = new HashMap();
	private TaskDao taskDao;
	private UserDataDao userDataDao;
	private UserMedalDao userMedalDao;
	private StepDataDao stepDataDao;
	private SingleStepDataDao singleStepDataDao;
	private SensorTypeDao sensorTypeDao;
	private SensorDataDao sensorDataDao;
	private MedalDao medalDao;
	private FriendsDao friendsDao;
	private FootTypeDao footTypeDao;
	private DaySleepDao daySleepDao;
	private UserRequestDao userRequestDao;
	private CompetitionPartyDao competitionPartyDao;
	private FileOperationService fileOperationService;

	public FileOperationService getFileOperationService() {

		return fileOperationService;
	}

	public void setFileOperationService(FileOperationService fileOperationService) {

		this.fileOperationService = fileOperationService;
	}

	public void setTaskDao(TaskDao taskDao) {

		this.taskDao = taskDao;
	}

	public UserRequestDao getUserRequestDao() {

		return userRequestDao;
	}

	public void setUserRequestDao(UserRequestDao userRequestDao) {

		this.userRequestDao = userRequestDao;
	}

	@Override
	protected BaseDao getBaseDao() {

		return this.taskDao;
	}

	public UserDataDao getUserDataDao() {

		return userDataDao;
	}

	public void setUserDataDao(UserDataDao userDataDao) {

		this.userDataDao = userDataDao;
	}

	public UserMedalDao getUserMedalDao() {

		return userMedalDao;
	}

	public void setUserMedalDao(UserMedalDao userMedalDao) {

		this.userMedalDao = userMedalDao;
	}

	public StepDataDao getStepDataDao() {

		return stepDataDao;
	}

	public void setStepDataDao(StepDataDao stepDataDao) {

		this.stepDataDao = stepDataDao;
	}

	public SingleStepDataDao getSingleStepDataDao() {

		return singleStepDataDao;
	}

	public void setSingleStepDataDao(SingleStepDataDao singleStepDataDao) {

		this.singleStepDataDao = singleStepDataDao;
	}

	public SensorTypeDao getSensorTypeDao() {

		return sensorTypeDao;
	}

	public void setSensorTypeDao(SensorTypeDao sensorTypeDao) {

		this.sensorTypeDao = sensorTypeDao;
	}

	public SensorDataDao getSensorDataDao() {

		return sensorDataDao;
	}

	public void setSensorDataDao(SensorDataDao sensorDataDao) {

		this.sensorDataDao = sensorDataDao;
	}

	public MedalDao getMedalDao() {

		return medalDao;
	}

	public void setMedalDao(MedalDao medalDao) {

		this.medalDao = medalDao;
	}

	public FriendsDao getFriendsDao() {

		return friendsDao;
	}

	public void setFriendsDao(FriendsDao friendsDao) {

		this.friendsDao = friendsDao;
	}

	public FootTypeDao getFootTypeDao() {

		return footTypeDao;
	}

	public void setFootTypeDao(FootTypeDao footTypeDao) {

		this.footTypeDao = footTypeDao;
	}

	public DaySleepDao getDaySleepDao() {

		return daySleepDao;
	}

	public void setDaySleepDao(DaySleepDao daySleepDao) {

		this.daySleepDao = daySleepDao;
	}

	public CompetitionPartyDao getCompetitionPartyDao() {

		return competitionPartyDao;
	}

	public void setCompetitionPartyDao(CompetitionPartyDao competitionPartyDao) {

		this.competitionPartyDao = competitionPartyDao;
	}

	public TaskDao getTaskDao() {

		return taskDao;
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
	@PUT
	@Path("/register/{userid}/{password}/{username}")
	@Produces( { "application/json", "application/xml" })
	public Boolean register(@PathParam("userid") String userid, @PathParam("password") String password, @PathParam("username") String username) {
		boolean flg = true;// 注册是否成功标志,默认为注册成功
		UserData userData = this.userDataDao.get(userid);
		// 用户名、密码为空或该用户已经注册过就不在注册
		if (userData == null && StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(password)) {
			userData = new UserData();
			userData.setId(userid);
			userData.setPassword(MD5Util.getMD5(password.getBytes()));
			userData.setRegdate(this.getSystemDate());
			userData.setUsername(username);
			this.userDataDao.create(userData);
		} else {
			flg = false;
		}
		return flg;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 发送验证码接口
	 * @see
	 * @param userid
	 *            用户id
	 * @return 向注冊用戶發送驗證碼，發送成功后，返回true,否則返回false
	 * @exception JeawException
	 */
	@GET
	@Path("/sendSms/{userid}")
	@Produces( { "application/json", "application/xml" })
	public Boolean sendSms(@PathParam("userid") String userid) {
		Date nowDate = userDataDao.getSystemDate();
		boolean flg = false;// 注册是否成功标志,默认为注册成功
		if (StringUtils.isNotBlank(userid)) {
			String nowDateStr = DateUtil.format(nowDate,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS_SSS);
			String userAuthCode = (String) authCodeMap.get(userid);
			if(StringUtils.isNotBlank(userAuthCode)){
				String[] authCodes = userAuthCode.split(",");
				Date lastSendTime;
				try {
					lastSendTime = DateUtil.parse(authCodes[1], DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS_SSS);
					lastSendTime = DateUtil.addMinutes(lastSendTime, 1);
					if(nowDate.before(lastSendTime)){
						this.logger.info(userid+"距离上次发送校验码不到一分钟");
						return flg;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			String authCode = SendSms.createAuthCode();
			String content = I18NMessageReader.getMessage("authCode.msg", new String[] { authCode });
			flg = SendSms.sendSms(content, userid);
			this.logger.info(userid+"用户发送的短信验证码是"+authCode);
			if(flg){
				authCodeMap.put(userid, authCode+","+nowDateStr);
			}
		}
		return flg;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 校验验证码是否正确
	 * @see
	 * @param
	 * @return 返回值修改：返回一个int类型的数值,  其值可以为0、1、2 ： 0 代表验证通过， 1代表验证失败 --（输入的验证码不正确） ，2 代表验证失败 – 该手机号码已经被注册
	 * @exception JeawException
	 */
	@GET
	@Path("/checkSms/{userid}/{authCode}")
	@Produces( { "application/json", "application/xml" })
	public Integer checkSms(@PathParam("userid") String userid, @PathParam("authCode") String authCode) {
		int result = 1;//默认是校验码不正确。
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(authCode)) {
			this.logger.info(userid+"checkSms，校验码参数为："+authCode);
			UserData userData = this.userDataDao.get(userid);
			if(userData!=null){
				result= 2;
				authCodeMap.remove(userid);// 验证码校验通过后，将验证码从缓存中删除
				return result;
			}
			String userCode = (String)authCodeMap.get(userid);
			if(StringUtils.isNotBlank(userCode)){
				String[] userAuthCodes = userCode.split(",");
				if (userAuthCodes[0].equals(authCode)) {
					result = 0;
					authCodeMap.remove(userid);// 验证码校验通过后，将验证码从缓存中删除
				}
			}
		}
		return result;
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
	 * @return 校验通过则返回true，且返回是否有运动数据【1：有，0：无】，否则返回false
	 * @exception JeawException
	 */
	@GET
	@Path("/checkUser/{userid}/{password}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult checkUser(@PathParam("userid") String userid, @PathParam("password") String password) {
		ZnshResult rtnResult = new ZnshResult();
		boolean flg = false;// 默认校验不通过
		UserData userData = this.userDataDao.get(userid);
		if (userData != null && StringUtils.isNotBlank(password)) {
			String pwd = userData.getPassword();
			if (pwd.equals(MD5Util.getMD5(password.getBytes()))) {
				flg = true;
				if (userData.getWeight() > 0) {
					// 當存在運動數據時，認為不是第一次登錄
					rtnResult.setMsgObj(SysmgrConstants.SFDM_Y);
				} else {
					// 當不存在運動數據時，認為是第一次登錄
					rtnResult.setMsgObj(SysmgrConstants.SFDM_N);
				}
			}
		}
		rtnResult.setSuccessFlg(flg);
		return rtnResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 根据用户id返回用户数据
	 * @see
	 * @param userid
	 *            用户id
	 * @return 校验通过则返回true，否则返回false
	 * @exception JeawException
	 */
	@GET
	@Path("/getUserData/{userid}")
	@Produces( { "application/json", "application/xml" })
	public UserData getUserData(@PathParam("userid") String userid) {
		UserData userData = null;
		if (StringUtils.isNotBlank(userid)) {
			userData = this.userDataDao.get(userid);
		}
		return userData;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：于注册成功的用户，进行下一步的用户个人信息补充操作
	 * @see
	 * @param userid
	 *            用户id
	 * @param userData
	 * @return 更新完成后返回true,如果用户名不存在，则返回false
	 * @exception JeawException
	 */
	@POST
	@Path("/updateUser/{userid}")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public Boolean updateUser(@PathParam("userid") String userid, UserData userData) {
		boolean flg = false;// 默认更新不成功
		if (StringUtils.isNotBlank(userid) && userData != null) {
			UserData userData1 = this.userDataDao.get(userid);
			if (userData1 != null) {
				if (StringUtils.isNotBlank(userData.getBirthday())) {
					userData1.setBirthday(userData.getBirthday());
				}
				if (StringUtils.isNotBlank(userData.getEmail())) {
					userData1.setEmail(userData.getEmail());
				}
				if (StringUtils.isNotBlank(userData.getSensorid())) {
					userData1.setSensorid(userData.getSensorid());
				}
				if (StringUtils.isNotBlank(userData.getUsername())) {
					userData1.setUsername(userData.getUsername());
				}
				if (userData.getBindtime() != null) {
					userData1.setBindtime(userData.getBindtime());
				}
				if (userData.getHeight() != null) {
					userData1.setHeight(userData.getHeight());
				}
				if (userData.getStride() != null) {
					userData1.setStride(userData.getStride());
				}
				if (userData.getWaist() != null) {
					userData1.setWaist(userData.getWaist());
				}
				if (userData.getWeight() != null) {
					userData1.setWeight(userData.getWeight());
				}
				if (StringUtils.isNotBlank(userData.getSex())) {
					userData1.setSex(userData.getSex());
				}
				if (StringUtils.isNotBlank(userData.getSensorid())) {
					userData1.setSensorid(userData.getSensorid());
				}
				this.userDataDao.update(userData1);
				flg = true;
			}

		}
		return flg;
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
	 * @exception JeawException
	 */
	@GET
	@Path("/getStepData/{userid}/{stepDate}")
	@Produces( { "application/json", "application/xml" })
	public String getStepData(@PathParam("userid") String userid, @PathParam("stepDate") String stepDate) {
		byte[] rtn = null;
		if (StringUtils.isNotBlank(userid) && stepDate != null) {
			StepData stepData = (StepData) stepDataDao.findOneByHql("hql_find_userDaytime_userInfo", userid, stepDate);
			if (stepData != null) {
				rtn = stepData.getSteppackage();
			}
		}
		if (rtn == null) {
			return null;
		}
		return Base64.encodeBytes(rtn);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：用户上传单日数据
	 * @see
	 * @param userid
	 *            用户id
	 * @param stepDate
	 *            日期
	 * @return 增加成功后，返回true,否则返回false
	 * @throws ParseException 
	 * @exception JeawException
	 */
	@POST
	@Path("/addStepData/{userid}/{stepDate}")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public Boolean addStepData(@PathParam("userid") String userid, @PathParam("stepDate") String stepDate,
			StepData stepData) throws ParseException {
		boolean flg = true;
		UserData userData1 = this.userDataDao.get(userid);
		if (userData1 != null && StringUtil.isNotBlank(stepDate)) {
			StepData stepDataPo = (StepData) this.stepDataDao.findOneByHql("hql_find_userDaytime_userInfo", userid,
					stepDate);
			if (stepDataPo == null) {
				stepDataPo = new StepData();
				stepDataPo.setUserid(userid);
			}
			try {
				// 更新用户单日数据
				stepDataPo.setDaytime(DateUtil.parse(stepDate));
				stepDataPo.setMinutes(stepData.getMinutes());
				stepDataPo.setSteps(stepData.getSteps());
				stepDataPo.setCalorie(stepData.getCalorie());
				stepDataPo.setStepkm(stepData.getStepkm());
				stepDataPo.setMaxcal(stepData.getMaxcal());
				stepDataPo.setMaxminutes(stepData.getMaxminutes());
				stepDataPo.setMaxstepkm(stepData.getMaxstepkm());
				stepDataPo.setSteppackage(stepData.getSteppackage());
				this.stepDataDao.saveOrUpdate(stepDataPo);
			} catch (ParseException e) {
				e.printStackTrace();
				flg = false;
			}
			// 更新用户睡眠数据表
			DaySleep daySleepPo = (DaySleep) this.daySleepDao.findOneByHql("hql_find_userDaytime_daySleep", userid,
					stepDate);
			if (daySleepPo == null) {
				daySleepPo = new DaySleep();
				daySleepPo.setUserid(userid);
			}
			DaySleep daySleep = stepData.getDaySleep();
			if (daySleep != null) {
				try {
					daySleepPo.setSleeptime(DateUtil.parse(stepDate));
					daySleepPo.setDaysleeptime(daySleep.getDaysleeptime());
					daySleepPo.setDaylightsleep(daySleep.getDaylightsleep());
					daySleepPo.setDaydeepsleep(daySleep.getDaydeepsleep());
					daySleepPo.setLightsleepEnd(daySleep.getLightsleepEnd());
					daySleepPo.setLightsleepStart(daySleep.getLightsleepStart());
					daySleepPo.setDaydeepsleepStart(daySleep.getDaydeepsleepStart());
					daySleepPo.setDaydeepsleepEnd(daySleep.getDaydeepsleepEnd());
					daySleepPo.setGetup(daySleep.getGetup());
					this.daySleepDao.saveOrUpdate(daySleepPo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 更新用户约跑详细表
			CompetitionParty competitionParty = getCompetitionParty(userid, stepDate);
			if (competitionParty != null) {
				byte[] sleepMinutes = stepData.getSleepMinutes();//睡眠时间段，每4个字节表示一个时间段，格式为HHMM
				int sleepStart1=0;//第一次睡眠时间段
				int sleepEnd1=0;//第一次睡眠时间段
				int sleepStart2=0;//第二次睡眠时间段
				int sleepEnd2=0;//第二次睡眠时间段
				int sleepStart3=0;//第三次睡眠时间段
				int sleepEnd3=0;//第三次睡眠时间段
				if(!(sleepMinutes[0]==sleepMinutes[2] && sleepMinutes[1]==sleepMinutes[3])){
					sleepStart1 = Integer.valueOf(sleepMinutes[0])+Integer.valueOf(sleepMinutes[1])*60;
					sleepEnd1 = Integer.valueOf(sleepMinutes[2])+Integer.valueOf(sleepMinutes[3])*60;
				}
				if(!(sleepMinutes[4]==sleepMinutes[6] && sleepMinutes[5]==sleepMinutes[7])){
					sleepStart2 = Integer.valueOf(sleepMinutes[4])+Integer.valueOf(sleepMinutes[5])*60;
					sleepEnd2 = Integer.valueOf(sleepMinutes[6])+Integer.valueOf(sleepMinutes[7])*60;
				}

				if(!(sleepMinutes[8]==sleepMinutes[10] && sleepMinutes[9]==sleepMinutes[11])){
					sleepStart3 = Integer.valueOf(sleepMinutes[8])+Integer.valueOf(sleepMinutes[9])*60;
					sleepEnd3 = Integer.valueOf(sleepMinutes[10])+Integer.valueOf(sleepMinutes[11])*60;
				}
				Task task = taskDao.get(competitionParty.getTaskid());
				byte[] stepByte = stepData.getSteppackage();//运动数据包
				int steps=0;//运动步数
				double stepkm=0;//运动公里数
				double calorie=0;//消耗热量
				int minutes=0;//运动时间
				int syncMinutes=0;//同步多少分钟数据
				Calendar calendar = Calendar.getInstance(); 
				Date syncDate = competitionParty.getSyncDate();//上次同步数据的最后时间
				Date stepDates = DateUtil.parse(stepDate);//上传运动数据的运动时间
				Date startDate =task.getTaskstartTime();//任务开始时间
				Date endDate = task.getTaskendTime();//任务结束时间
				int startMinutes=0;//同步数据的数组开始坐标
				int endMinutes=0;//同步数据的数组结束坐标
				//运动时间在任务的开始与结束时间范围内
				if(stepDates.before(endDate)&&(stepDates.after(startDate)||DateUtil.isSameDay(startDate, stepDates))){
					if(DateUtil.isSameDay(startDate, stepDates)&&syncDate==null){
						//同步日期與開始日期是同一天，且是第一次上傳數據
						calendar.setTime(startDate);
						startMinutes = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY)*60-1;
					}else if((!DateUtil.isSameDay(startDate, stepDates)&&syncDate==null)||(syncDate!=null&&!DateUtil.isSameDay(syncDate, stepDates))){
						//同步日期与开始日期不是一天，且是第一次上传数据
						startMinutes=0;
					}else if(syncDate!=null&&DateUtil.isSameDay(syncDate, stepDates)){
						//不是第一次同步数据
						calendar.setTime(syncDate);
						startMinutes = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY)*60;
					}
					if(DateUtil.isSameDay(endDate, stepDates)){
						calendar.setTime(endDate);
						endMinutes = calendar.get(Calendar.MINUTE) + calendar.get(Calendar.HOUR_OF_DAY)*60-1;
					}else if(!DateUtil.isSameDay(endDate, stepDates)){
						endMinutes=1439;
					}
					int step=0;
					for(int i=startMinutes;i<=endMinutes;i++){
						step=Integer.valueOf(stepByte[i]);
						if(step>0&&!((sleepStart1<i && i<sleepEnd1)||(sleepStart2<i && i<sleepEnd2)||(sleepStart3<i && i<sleepEnd3))){//大于12才计算为运动步数
							steps+=step;
							minutes+=1;
							syncMinutes+=1;
						}
					}
					int lastMinutes = endMinutes;
					for(;lastMinutes>=startMinutes;lastMinutes--){
						if(Integer.valueOf(stepByte[lastMinutes])>0){
							break;
						}
					}
					if(syncMinutes>0){
						/*运动时：体重 x 距离 x 1.036 
						静态时：
						女性：65.5+9.6W+1.7H-4.7A
						男性：66+13.7W+5.0H-6.8A
						（W=体重，H=身高/cm，A=年龄/年）*/
						stepkm = steps*userData1.getStride()/100/1000;//运动公里数
						calorie = userData1.getWeight()/2*stepkm*1.036;
						calendar.setTime(competitionPartyDao.getSystemDate());
						int nl = calendar.get(Calendar.YEAR)-Integer.valueOf(userData1.getBirthday())+1;
						if(SysmgrConstants.SEX_M.equals(userData1.getSex())){
							calorie = calorie+66+13.7*userData1.getWeight()/2+5.0*userData1.getHeight()/100-6.8*nl;
						}else if(SysmgrConstants.SEX_F.equals(userData1.getSex())){
							calorie = calorie+66.5+9.6*userData1.getWeight()/2+1.7*userData1.getHeight()/100-4.7*nl;
						}
						
						competitionParty.setStep((competitionParty.getStep()==null?0:competitionParty.getStep())+ steps);
						competitionParty.setStepkm((competitionParty.getStepkm()==null?0:competitionParty.getStepkm())+ stepkm);
						competitionParty.setCalorie((competitionParty.getCalorie()==null?0:competitionParty.getCalorie())+ calorie);
						competitionParty.setMinutes((competitionParty.getMinutes()==null?0:competitionParty.getMinutes())+ minutes);
						competitionParty.setRate((double) Math.round((competitionParty.getStepkm()==null?0:competitionParty.getStepkm()) * 1000
								/( competitionParty.getMinutes()==null?0:competitionParty.getMinutes())
								/ 60 * 100) / 100);// 平均速率 m/s
						if (competitionParty.getStepkm() != null && task.getEndmark() != null
								&& competitionParty.getStepkm() > task.getEndmark()) {
							competitionParty.setOverDate(stepDate);
						}
						calendar.set(Calendar.HOUR_OF_DAY, 0);
						calendar.set(Calendar.MINUTE, 0);
						calendar.set(Calendar.SECOND, 0);
						calendar.add(Calendar.MINUTE, lastMinutes+1);
						competitionParty.setSyncDate(calendar.getTime());
						this.competitionPartyDao.update(competitionParty);
					}
				}
			}
		} else {
			flg = false;
		}
		return flg;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 1. 增加好友，在好友表中增好友关系，如果已经是好友则不再增加，直接返回true
	 * @see
	 * @param userid
	 *            用户id
	 * @param friendid
	 *            朋友id
	 * @return 传入用户id,与好友用户id,会把两者关联成为朋友关系，如果已经是朋友关系，也返回true
	 * @exception JeawException
	 */
	@PUT
	@Path("/addFriend/{userid}/{friendid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addFriend(@PathParam("userid") String userid, @PathParam("friendid") String friendid) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotEmpty(userid) && StringUtils.isNotEmpty(friendid) && !userid.equals(friendid)) {
			Object obj = this.friendsDao.findOneByHql("hql_find_friend_byUseridFriendid", userid, friendid, userid,
					friendid);
			if (obj != null) {
				znshResult.setSuccessFlg(true);
				znshResult.setMsgObj("增加成功");
			} else {
				Friends friends = new Friends();
				friends.setUserid(userid);
				friends.setFrienduserid(friendid);
				this.friendsDao.create(friends);
				znshResult.setSuccessFlg(true);
			}
		} else {
			znshResult.setSuccessFlg(true);
			znshResult.setMsgObj("传入参数为空");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 2. 删除好友，从好友表中删除好友关联关系
	 * @see
	 * @param userid
	 *            用户id
	 * @param friendid
	 *            朋友id
	 * @return 删除好友关系成功后，返回true,否则返回false
	 * @exception JeawException
	 */
	@PUT
	@Path("/deleteFriend/{userid}/{friendid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult deleteFriend(@PathParam("userid") String userid, @PathParam("friendid") String friendid) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotEmpty(userid) && StringUtils.isNotEmpty(friendid) && !userid.equals(friendid)) {
			Friends friends = (Friends) this.friendsDao.findOneByHql("hql_find_friend_byUseridFriendid", userid,
					friendid, userid, friendid);
			if (friends != null) {
				friends.setDelFlag("D");
				this.friendsDao.update(friends);
			}
			znshResult.setSuccessFlg(true);
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("传入参数为空");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 4. 根据用户id，朋友id获取朋友id的状态，返回用户信息,包括状态：
	 *           0 已添加：代表改好友已经被用户添加，1 添加：代表好友已在使用手环，且还未被用户添加，2 申请添加   3同意  4拒绝 
	 * @see
	 * @param userid
	 *            用户id
	 * @param seneorid
	 *            设备id
	 * @param friendIds
	 *            朋友id,多个时以逗号分隔
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getFriendUserData/{userid}/{friendIds}")
	@Produces( { "application/json", "application/xml" })
	public List<UserData> getFriendUserData(@PathParam("userid") String userid, @PathParam("friendIds") String friendIds) {
		List<UserData> rtnlist = new ArrayList<UserData>();
		List<Object[]> resultList = new ArrayList();
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(friendIds)) {
			String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "sql_find_FriendUserData_byUserId");
			hql = hql.replaceAll(":friendid", "'" + friendIds.replaceAll(",", "','") + "'");
			resultList = (List<Object[]>) this.friendsDao.findListBySql(hql, userid, userid, userid,userid);
			if (resultList != null && resultList.size() > 0) {
				for (Object[] obj : resultList) {
//					Blob userpic = (Blob) obj[16];
//					byte[] pic = blob2Byte(userpic);
					/*
					 * UserData userData = new UserData((String) obj[0], (String) obj[1],
					 * (Date) obj[3], (String) obj[4],
					 * obj[5] == null ? 0l : ((BigDecimal) obj[5]).longValue(),
					 * obj[6] == null ? 0d : ((BigDecimal) obj[6]).doubleValue(),
					 * obj[7] == null ? 0l : (((BigDecimal) obj[7]).longValue()),
					 * obj[8] == null ? 0d : ((BigDecimal) obj[8]).doubleValue(),
					 * (String) obj[9], (String) obj[10], (Date) obj[11], (String) obj[12],
					 * (String) obj[13], (String) obj[14], (String) obj[15], pic);
					 * userData.setTotalCal(obj[16] == null ? 0d : ((BigDecimal) obj[16]).doubleValue());
					 * userData.setTotalStepKm(obj[17] == null ? 0d : ((BigDecimal) obj[17]).doubleValue());
					 */
					// 2014-04-02只返回用户id,照片及状态 名称
					UserData userData = new UserData();
					userData.setId((String) obj[0]);
					userData.setFriendZt((String) obj[15]);
					userData.setRequestid((String) obj[17]);
					userData.setHeight(null);
					userData.setWaist(null);
					userData.setWeight(null);
					userData.setStride(null);
					userData.setSensorid(null);
					rtnlist.add(userData);
				}
			}
		}
		return rtnlist;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 4. 3. 朋友圈展现，根据用户id查询用户信息表，朋友圈表，运动单日数据表，将好友信息查询出，
	 * @see
	 * @param userid
	 *            用户id
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getFriendsInfo/{userid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult getFriendsInfo(@PathParam("userid") String userid) {
		ZnshResult znshResult = null;
		List<ZnshResult> rtnlist = new ArrayList<ZnshResult>();
		if (StringUtils.isNotBlank(userid)) {
			rtnlist = getUserStepInfo(new String[] { userid }, SysmgrConstants.STR_ZERO);
		}
		if (rtnlist != null && rtnlist.size() == 1) {
			znshResult = rtnlist.get(0);
		}
		Date now = stepDataDao.getSystemDate();
		Date endDate = DateUtil.addDays(now, -6);
		List<StepData> stepDataList = getStepDataList(userid, endDate, now);// 用户前七天的运动数据
		List<StepData> stepList = new ArrayList<StepData>();// 返回前台的运动数据
		Date everyDay = stepDataDao.getSystemDate();
		String everyDateStr = null;
		String stepDateStr = null;
		for (int i = 0; i < 7; i++) {
			everyDay = DateUtil.addDays(now, -i);
			StepData stepData = new StepData();
			stepData.setDaytime(everyDay);
			stepData.setSteps(0l);
			StepData temp;
			for (int j = 0; j < stepDataList.size(); j++) {
				temp = stepDataList.get(j);
				everyDateStr = DateUtil.format(everyDay, DateUtil.FMT_DATE_YYYYMMDD);
				stepDateStr = DateUtil.format(temp.getDaytime(), DateUtil.FMT_DATE_YYYYMMDD);
				if (everyDateStr.equals(stepDateStr)) {
					stepData.setSteps(temp.getSteps() == null ? 0 : temp.getSteps());
					stepDataList.remove(j);
					break;
				}
			}
			stepList.add(stepData);
		}
		znshResult.setStepDataList(stepList);
		znshResult.setRank(null);
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获取某一时间段内的用户运动数据
	 * @see
	 * @param userid
	 *            用户id
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @exception JeawException
	 */
	public List<StepData> getStepDataList(String userid, Date startDate, Date endDate) {

		return (List<StepData>) stepDataDao.findListByHql("hql_getStepDataList_byUseridStartEnd", userid, startDate,
				endDate);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 4. 3. 朋友圈展现，根据用户id查询用户信息表，朋友圈表，运动单日数据表，将好友信息查询出，
	 * @see
	 * @param userid
	 *            用户id
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getFriendsList/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getFriendsList(@PathParam("userid") String userid) {
		List<ZnshResult> list = getFriendsRank(userid);
		for (ZnshResult temp : list) {
			temp.setRank(null);
		}
		return list;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：
	 *          获取用户的运动汇总信息，【用户头像（userPic）、用户姓名(userName)、性别(sex)】用户信息表、
	 *          【运动总里程(totalKm)、燃烧大卡总量(totalCal)、运动总步数（totalSteps
	 *          ）、名次（rank）】用户运动单日数据表数据到当前时间的汇总,其中名次按燃烧大卡总量进行排序，
	 * @see
	 * @param userid
	 *            用户id数组
	 * @param flg
	 *            0:表示只查询当前一个用户信息，1表示查询多个用户信息
	 * @return
	 * @exception JeawException
	 */
	public List<ZnshResult> getUserStepInfo(String[] userids, String flg) {

		List<ZnshResult> rtnList = new ArrayList<ZnshResult>();

		String sql = PropertyXmlMgr.getString("SQL_SYSMGR", "sql_find_UserStepInfo_byUserId");
		StringBuffer userid = new StringBuffer();
		if (userids == null || userids.length == 0) {
			sql = sql.replaceAll(":whr", " ");
		} else {
			for (String str : userids) {
				userid.append(",'").append(str).append("'");
			}
			sql = sql.replaceAll(":whr", " AND U.ID in (" + userid.substring(1) + ") ");
		}

		List<Object[]> resultList = (List<Object[]>) this.friendsDao.findListBySql(sql);
		if (resultList != null && resultList.size() > 0) {
			for (Object[] obj : resultList) {
				ZnshResult znshResult = null;
				if (SysmgrConstants.STR_ZERO.equals(flg)) {// 查询个人信息数据
					znshResult = new ZnshResult((String) obj[0], (String) obj[3],
							(String) obj[1], (String) obj[2],
							obj[4] == null ? 0d : ((BigDecimal) obj[4]).doubleValue(),
							obj[5] == null ? 0d : ((BigDecimal) obj[5]).doubleValue(),
							obj[6] == null ? 0l : ((BigDecimal) obj[6]).longValue(),
							obj[7] == null ? 0d : ((BigDecimal) obj[7]).doubleValue(),
							obj[8] == null ? 0d : ((BigDecimal) obj[8]).doubleValue(),
							obj[9] == null ? 0d : ((BigDecimal) obj[9]).doubleValue(),
							obj[10] == null ? 0d : ((BigDecimal) obj[10]).doubleValue(),
							obj[11] == null ? 0d : ((BigDecimal) obj[11]).doubleValue(),
							obj[12] == null ? 0 : ((BigDecimal) obj[12]).intValue());
				} else if (SysmgrConstants.STR_ONE.equals(flg)) {
					znshResult = new ZnshResult();
					znshResult.setUserpic((String) obj[3]);
					znshResult.setUserName((String) obj[1]);
					znshResult.setSex((String) obj[2]);
					znshResult.setTotalKm(obj[4] == null ? 0d : ((BigDecimal) obj[4]).doubleValue());
					znshResult.setTotalCal(obj[5] == null ? 0d : ((BigDecimal) obj[5]).doubleValue());
					znshResult.setUserid((String) obj[0]);
					znshResult.setRank(obj[12] == null ? 0 : ((BigDecimal) obj[12]).intValue());
				}
				rtnList.add(znshResult);
			}
		}
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获取一个用户的朋友数据，不包括自己
	 * @see
	 * @param userid
	 *            用户id
	 * @return
	 * @exception JeawException
	 */
	public List<String> getFriendsByuserid(String userid) {

		List<String> list = (List<String>) this.friendsDao.findListBySql("sql_find_friend_byUserid", userid, userid);
		return list;
	}

	public byte[] blob2Byte(Blob blob) {

		byte[] pic = null;
		if (blob != null) {
			try {
				pic = new byte[((Long) blob.length()).intValue()];
				InputStream inStrem = blob.getBinaryStream();
				inStrem.read(pic);
				inStrem.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pic;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 增加约跑任务，任务名称不能重复，如果重复则不能创建，约跑模式：普通01、马拉松02
	 * @see
	 * @param
	 * @return 建立成功后返回true及约跑任务id,否则返回false且失败原因
	 * @exception JeawException
	 */
	@POST
	@Path("/addTask")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addTask(Task task) {
		ZnshResult znshResult = new ZnshResult();
		if (task != null && StringUtils.isNotBlank(task.getPartyuser())) {
//			Task taskEntity = (Task) this.taskDao.findOneByHql("hql_find_task_byTaskName", task.getTaskname());
			znshResult.setSuccessFlg(true);
			/*if (taskEntity != null) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务名称已经存在。");
			} else if (StringUtils.isBlank(task.getTaskname())) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务名称不能为空。");
			}
			if (!znshResult.isSuccessFlg()) {
				return znshResult;
			}*/
			// 如果该用户已经加入别的约跑任务且没有结束，就不能再加入别的约跑任务
			List<Task> taskList = (List<Task>) this.competitionPartyDao.findListByHql("hql_find_task_byUserid", task
					.getPartyuser());
			if (taskList != null && taskList.size() > 0) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("该用户当前已经参加了另外一个没有结束的任务，不能创建任务。");
				return znshResult;
			}
			task.setId(task.getPartyuser()
					+ DateUtil.dateToString(this.taskDao.getSystemDate(), DateUtil.FMT_DATE_YYYYMMDDHHmmss));
			task.setTaskstate(SysmgrConstants.RWTDM_ZBZ);// 初始任务状态为准备中
			task.setCreateDate(DateUtil.dateToString(this.taskDao.getSystemDate(), DateUtil.FMT_DATE_YYYY_MM_DD));
			try {
				task.setTaskendTime(DateUtil.parse(task.getEndTimeStr(), DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS));
				task.setTaskstartTime(DateUtil.parse(task.getStartTimeStr(), DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.logger.error(task.getEndTimeStr()+"结束日期------------------------ addTask(Task task),格式日期出现异常，开始日期为："+task.getStartTimeStr());
			}
			if(task.getPartyUserNumber()==null ||task.getPartyUserNumber()==0){
				task.setPartyUserNumber(1);
			}
			this.taskDao.create(task);
			addCompetitionPart(task.getPartyuser(), task.getId());// 将自己加入所建立的约跑任务
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("传入的任务信息为空或建立约跑的用户id为空。");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 删除约跑任务，如果已经进行或结束不能删除，需要删除约跑任务表，及约跑明细表相关数据
	 * @see
	 * @param taskid
	 *            任务id
	 * @return 删除成功后返回true，否则返回false且失败原因
	 * @exception JeawException
	 */
	@PUT
	@Path("/delTask/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult delTask(@PathParam("taskid") String taskid) {
		ZnshResult znshResult = new ZnshResult();
		Task task = this.taskDao.get(taskid);
		if (task != null) {
			String taskstate = task.getTaskstate();
			if (SysmgrConstants.RWTDM_ZBZ.equals(taskstate)) {
				znshResult.setSuccessFlg(true);
				task.setDelFlag("D");
				this.taskDao.update(task);// 删除任务信息
				delCompetitionParty(taskid);// 删除任务明细表
			} else {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务已经开始或结束不能删除。");
			}
			return znshResult;
		} else {
			znshResult.setSuccessFlg(true);
			znshResult.setMsgObj("任务已经不存在。");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 删除约跑明细表，能过任务id
	 * @see
	 * @param taskid
	 *            任务id
	 * @return
	 * @exception JeawException
	 */
	public ZnshResult delCompetitionParty(String taskid) {

		ZnshResult znshResult = new ZnshResult();
		znshResult.setSuccessFlg(true);
		if (StringUtils.isNotBlank(taskid)) {
			this.competitionPartyDao.executeHql("hql_delCompetitionParty_byTaskid", taskid);// 删除任务表
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获得约跑信息,获取自己创建及自己朋友创建的约跑任务、马拉松式任务
	 * @see
	 * @param userid用户id
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getTaskList/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<Task> getTaskList(@PathParam("userid") String userid) {
		List<Task> rtnList = null;
		List<String> friendList = getFriendsByuserid(userid);
		if (friendList != null && friendList.size() > 0) {
			friendList.add(userid);
			String[] userids = friendList.toArray(new String[0]);
			rtnList = getTaskListByUserid(userids);
			List<UserData> userDataList = getUserDataList(userids);
			for (Task t : rtnList) {
				for (UserData u : userDataList) {
					if (t.getPartyuser().equals(u.getId())) {
						t.setUsername(u.getUsername());
						t.setUserpic(u.getUserpic());
					}
				}
			}
		}
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获取用户实体
	 * @see
	 * @param
	 * @return
	 * @exception JeawException
	 */
	public List<UserData> getUserDataList(String[] userids) {

		String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "hql_findUserData_byUserids");
		StringBuffer userid = new StringBuffer();
		for (String str : userids) {
			userid.append(",'").append(str).append("'");
		}
		hql = hql.replaceAll(":userids", userid.substring(1));
		return (List<UserData>) this.userDataDao.findListByHql(hql);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 通过用户id获得任务信息
	 * @see
	 * @param userid
	 *            用户id
	 * @return
	 * @exception JeawException
	 */
	public List<Task> getTaskListByUserid(String[] userids) {

		String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "hql_findTask_byUserid");
		StringBuffer userid = new StringBuffer();
		for (String str : userids) {
			userid.append(",'").append(str).append("'");
		}
		hql = hql.replaceAll(":userid", userid.substring(1));
		return (List<Task>) this.taskDao.findListByHql(hql);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 9.
	 *          约跑明细表增加数据,通过约跑任务id，及用户id，将数据保存到约跑明细表，如果该用户已经加入别的约跑任务且没有结束，就不能再加入别的约跑任务。
	 *          2.看任务是否已经达到人数限制，3.看任务是否已经开始
	 * @see
	 * @param userid用户id
	 * @param taskid任务id
	 * @return 增加成功返回true,否则返回 false及原因
	 * @exception JeawException
	 */
	@PUT
	@Path("/addCompetitionPart/{userid}/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addCompetitionPart(@PathParam("userid") String userid, @PathParam("taskid") String taskid) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(taskid)) {
			Task task = this.taskDao.get(taskid);
			if (task == null) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("找不到对应的任务数据。");
				return znshResult;
			}
			// 如果该用户已经加入别的约跑任务且没有结束，就不能再加入别的约跑任务
			List<Task> taskList = (List<Task>) this.competitionPartyDao.findListByHql("hql_find_task_byUserid", userid);
			if (taskList != null && taskList.size() > 0) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("该用户当前已经参加了另外一个没有结束的任务。");
				return znshResult;
			}
			// 看任务是否已经开始
			String taskState = task.getTaskstate();
			if (!SysmgrConstants.RWTDM_ZBZ.equals(taskState)) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务已经开始或结束，不能加入。");
				return znshResult;
			}
			// 看任务是否已经达到人数限制
			long userNumber = 0;
			userNumber = (Long) this.competitionPartyDao.findOneByHql("hql_find_countUserBytaskid", taskid);
			int partyUserNumber = task.getPartyUserNumber();
			if (userNumber >= partyUserNumber) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务已经达到人数限制，不能加入。");
				return znshResult;
			}
			znshResult.setSuccessFlg(true);
			CompetitionParty competitionParty = new CompetitionParty();
			competitionParty.setTaskid(taskid);
			competitionParty.setUserid(userid);
			this.competitionPartyDao.create(competitionParty);
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("传入参数为空。");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 退出约跑任务，通过约跑任务id,及人员id，从约跑任务明细表中删除数据，删除前需要判断约跑任务是否已经开始
	 * @see
	 * @param userid用户id
	 * @param taskid任务id
	 * @return 退出成功返回true,否则返回 false及原因
	 * @exception JeawException
	 */
	@PUT
	@Path("/delCompetitionPart/{userid}/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult delCompetitionPart(@PathParam("userid") String userid, @PathParam("taskid") String taskid) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(taskid)) {
			Task task = this.taskDao.get(taskid);
			// 看任务是否已经开始
			String taskState = task.getTaskstate();
			if (!SysmgrConstants.RWTDM_ZBZ.equals(taskState)) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("任务已经开始或结束，不能退出。");
				return znshResult;
			}
			znshResult.setSuccessFlg(true);
			this.competitionPartyDao.executeHql("hql_delCompetitionParty_byTaskidUserid", taskid, userid);
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("传入参数为空。");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：获取用户参加的约跑任务信息及自己所有朋友新增的约跑任务及创建者信息，创建者头像、创建者、创建时间、参加人数、结束倒计时、比赛状态,约跑任务id
	 * @see
	 * @param userid用户id
	 * @return list<ZnshResult>
	 * @exception JeawException
	 */
	@GET
	@Path("/getTasksByPartyUserid/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getTasksByPartyUserid(@PathParam("userid") String userid) {
		List<ZnshResult> rtnList = new ArrayList<ZnshResult>();
		if (StringUtils.isBlank(userid)) {
			return rtnList;
		}
		List<ZnshResult> tempList = getJoinTasksByUserid(userid, null);// 参加的约跑任务
		List<String> list = getFriendsByuserid(userid);
		if (list != null && list.size() > 0) {
			tempList.addAll(getTasksByUserid(list.toArray(new String[0]), SysmgrConstants.RWTDM_ZBZ));// 自己朋友创建的任务
		}
		for (int i = 0; i < tempList.size(); i++) {
			if (!rtnList.contains(tempList.get(i))) {
				rtnList.add(tempList.get(i));
			}
		}
		Collections.sort(rtnList, new Comparator<ZnshResult>() {

			public int compare(ZnshResult arg0, ZnshResult arg1) {

				return arg0.getTaskstate().compareTo(arg1.getTaskstate());
			}
		});
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获取用户创建的任务
	 * @see
	 * @param userid
	 *            用户id
	 * @param rwzt
	 *            任务状态[01:进行中，02：准备中，03：结束]
	 * @return
	 * @exception JeawException
	 */
	public List<ZnshResult> getTasksByUserid(String[] userids, String taskstate) {

		List<ZnshResult> rtnList = null;
		if (userids != null && userids.length > 0) {
			String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "hql_find_task_byCreateUser");
			StringBuffer useridBuf = new StringBuffer();
			for (String temp : userids) {
				useridBuf.append(",'").append(temp).append("'");
			}
			hql = hql.replaceAll(":userid", useridBuf.substring(1));
			if (StringUtils.isNotBlank(taskstate)) {
				hql = hql.replaceAll(":whr", " and t.taskstate in ('" + taskstate.replaceAll(",", "','") + "') ");
			} else {
				hql = hql.replaceAll(":whr", " ");
			}
			rtnList = (List<ZnshResult>) this.taskDao.findListByHql(hql);
		}
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获取用户参加约跑的任务
	 * @see
	 * @param userid
	 *            用户id
	 * @param rwzt
	 *            任务状态[01:进行中，02：准备中，03：结束]，多个以逗号分隔,不传值时默认为全部
	 * @return
	 * @exception JeawException
	 */
	public List<ZnshResult> getJoinTasksByUserid(String userid, String taskstate) {

		List<ZnshResult> rtnList = null;
		if (StringUtils.isNotBlank(userid)) {
			String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "hql_find_task_byPartyUser");
			if (StringUtils.isNotBlank(taskstate)) {
				hql = hql.replaceAll(":whr", " and t.taskstate in ('" + taskstate.replaceAll(",", "','") + "')");
			} else {
				hql = hql.replaceAll(":whr", " ");
			}
			rtnList = (List<ZnshResult>) this.taskDao.findListByHql(hql, userid);
		}
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 约跑任务排名信息：名次、用户头像、用户姓名、完成时间、行走公里数、贡献率（行走公里数/团队总行走公里数）
	 * @see
	 * @param taskid
	 *            任务id
	 * @return List<ZnshResult>
	 * @exception JeawException
	 */
	@GET
	@Path("/getCompetitionPartyByTaskid/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getCompetitionPartyByTaskid(@PathParam("taskid") String taskid) {
		List<ZnshResult> rtnList = null;
		if (StringUtils.isNotBlank(taskid)) {
			List<Object[]> objList = (List<Object[]>) this.taskDao.findListBySql("sql_find_CompetitionParty_byTaskid",
					taskid);
			if (objList != null && objList.size() > 0) {
				rtnList = new ArrayList<ZnshResult>();
				for (Object[] obj : objList) {
					ZnshResult znshResult = new ZnshResult(((BigDecimal) obj[0]).intValue(), (String) obj[1],
							(String) obj[2], (String) obj[3],
							obj[4] == null ? 0d : ((BigDecimal) obj[4]).doubleValue(),
							obj[5] == null ? 0d : ((BigDecimal) obj[5]).doubleValue(),
							obj[6] == null ? 0d : ((BigDecimal) obj[6]).doubleValue());
					znshResult.setTotalSteps(obj[7] == null ? 0l : ((BigDecimal) obj[7]).longValue());
					znshResult.setTotalCal(obj[8] == null ? 0d : ((BigDecimal) obj[8]).doubleValue());
					znshResult.setTotalTime(obj[9] == null ? 0d : ((BigDecimal) obj[9]).doubleValue());
					znshResult.setAvgSpeep(obj[10] == null ? 0d : ((BigDecimal) obj[10]).doubleValue());
					znshResult.setUserid((String) obj[11]);
					znshResult.setModel((String) obj[12]);
					znshResult.setTaskstartTime((Date) obj[13]);
					znshResult.setTaskendTime((Date) obj[14]);
					znshResult.setPartyUserNumber(obj[16] == null ? 0 : ((BigDecimal) obj[16]).intValue());
					rtnList.add(znshResult);
				}
			}
		}
		return rtnList;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 查询当前用户参加的且正在执行的约跑明细表
	 * @see
	 * @param userid用户id
	 * @return
	 * @exception JeawException
	 */
	public CompetitionParty getCompetitionParty(String userid, String date) {

		return (CompetitionParty) this.competitionPartyDao.findOneByHql("hql_findCompetitionParty_byTaskidUserid",
				userid, date, date);
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 申请添加好友
	 * @see
	 * @return
	 * @exception JeawException
	 */
	@POST
	@Path("/addUserRequest")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addUserRequest(UserRequest userRequest) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotBlank(userRequest.getFriendid()) && StringUtils.isNotBlank(userRequest.getUserid())) {
			znshResult.setSuccessFlg(true);
			List<UserRequest> userRequestList = (List<UserRequest>) userRequestDao.findListByHql(
					"hql_findUserRequest_byUseridFriendid",
					userRequest.getUserid(), userRequest.getFriendid());
			if (userRequestList != null && userRequestList.size() > 0) {
				for (UserRequest temp : userRequestList) {
					if (SysmgrConstants.TJHY_SQTJ.equals(temp.getRequestZt())) {
						znshResult.setSuccessFlg(false);
						znshResult.setMsgObj("已经提交添加好友申请。");
						return znshResult;
					} else if (SysmgrConstants.TJHY_TY.equals(temp.getRequestZt())) {
						Object obj = this.friendsDao.findOneByHql("hql_find_friend_byUseridFriendid", userRequest.getUserid(), userRequest.getFriendid(), userRequest.getUserid(),
								userRequest.getFriendid());
						if(obj!=null){
							znshResult.setSuccessFlg(false);
							znshResult.setMsgObj("已经成为好友，不需要再添加申请。");
							return znshResult;
						}
					}
				}
			}
			userRequest.setRequestZt(SysmgrConstants.TJHY_SQTJ);
			userRequestDao.create(userRequest);
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("请求用户id与被请求用户id不能为空。");
		}

		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 同意或拒絕添加好友,
	 * @see
	 * @param reqId
	 * @param result
	 *            （3：同意，4：拒绝）
	 * @return
	 * @exception JeawException
	 */
	@POST
	@Path("/updateUserRequest/{reqId}/{requestZt}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult updateUserRequest(@PathParam("reqId") String reqId, @PathParam("requestZt") String requestZt) {
		ZnshResult znshResult = new ZnshResult();
		if (StringUtils.isNotBlank(reqId) && StringUtils.isNotBlank(requestZt)) {
			UserRequest userRequest = userRequestDao.get(reqId);
			znshResult.setSuccessFlg(true);
			if (userRequest == null) {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("请求id数据不正确。");
				return znshResult;
			}
			if (SysmgrConstants.TJHY_JJ.equals(requestZt)) {
				userRequest.setRequestZt(requestZt);
				userRequestDao.update(userRequest);
			} else if (SysmgrConstants.TJHY_TY.equals(requestZt)) {
				addFriend(userRequest.getUserid(), userRequest.getFriendid());// 添加为好友
				userRequest.setRequestZt(requestZt);
				userRequestDao.update(userRequest);
			} else {
				znshResult.setSuccessFlg(false);
				znshResult.setMsgObj("状态值不正确，无法操作数据库。");
			}
		} else {
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("请求id及处理结果不能为空。");
		}
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 根据用户id查询用户好友圈中排行榜
	 * @see
	 * @param userid
	 *            用户id
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getFriendsRank/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getFriendsRank(@PathParam("userid") String userid) {
		List<ZnshResult> rtnlist = new ArrayList<ZnshResult>();
		if (StringUtils.isNotBlank(userid)) {
			List<String> list = getFriendsByuserid(userid);
			list.add(userid);
			if (list != null && list.size() > 0) {
				String[] userids = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					userids[i] = list.get(i);
				}
				rtnlist = getUserStepInfo(userids, SysmgrConstants.STR_ONE);
			}
		}
		return rtnlist;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 给邀请用户发短信
	 * @see
	 * @param userid
	 *            用户id
	 * @param friendid
	 *            朋友id
	 * @return 短信发送成功后，返回 true
	 * @exception JeawException
	 */
	@GET
	@Path("/inviteUser/{userid}/{friendid}")
	@Produces( { "application/json", "application/xml" })
	public Boolean inviteUser(@PathParam("userid") String userid, @PathParam("friendid") String friendid) {
		UserData userData = this.userDataDao.get(userid);
		String username=null;
		if(userData!=null){
			username = userData.getUsername();
		}else{
			username = userid;
		}
		String msg = I18NMessageReader.getMessage("invite.msg", new String[] { username });
		boolean flg = SendSms.sendSms(msg, friendid);
		return flg;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 上传用户头像
	 * @see
	 * @param
	 * @return
	 * @exception JeawException
	 */
	@POST
	@Path("/uploadUerpic")
	@Produces( { "application/json", "application/xml" })
	@Consumes( { "application/json", "application/xml" })
	public ZnshResult uploadUerpic(MyFile myFile) {
		ZnshResult znshResult = new ZnshResult();
		znshResult.setSuccessFlg(true);
		System.out.println("11="+myFile.getFileName());
		System.out.println("22="+myFile.getUserid());
		System.out.println("33="+myFile.getUserpic());
		if (myFile == null || StringUtils.isBlank(myFile.getFileName())  || StringUtils.isBlank(myFile.getUserid()) || myFile.getUserpic() == null) {
			System.out.println("uploadUerpic 传入参数为空");
			znshResult.setSuccessFlg(false);
			znshResult.setMsgObj("uploadUerpic 传入参数为空");
			return znshResult;
		}
		String fileName = DateUtil.dateToString(this.taskDao.getSystemDate(), DateUtil.FMT_DATE_YYYYMMDDHHmmss)
				+ myFile.getFileName();
		UserData userData = userDataDao.get(myFile.getUserid());
		FileServer fileServer;
		try {
			fileServer = FileUtil.connectServer();
			File file = fileServer.getFileByPath(userData.getUserpic());
			if(file!=null){
				file.delete();	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userData.setUserpic(fileName);
		ByteArrayInputStream is = new ByteArrayInputStream(myFile.getUserpic());
		fileOperationService.uploadFile(fileName, is);
		znshResult.setSuccessFlg(true);
		return znshResult;
	}

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 下载用户头像
	 * @see
	 * @param
	 * @return
	 * @exception JeawException
	 */
	@POST
	@Path("/downloadUerpic")
	@Produces( { "application/json", "application/xml" })
	@Consumes( { "application/json", "application/xml" })
	public MyFile downloadUerpic(MyFile myFile) {
		if (myFile == null || StringUtils.isBlank(myFile.getUserid())) {
			return null;
		}
		MyFile myFileRtn = new MyFile();
		UserData userData = userDataDao.get(myFile.getUserid());
		myFileRtn.setFileName(userData.getUserpic());
		myFileRtn.setUserid(myFile.getUserid());
		FileServer fileServer;
		try {
			fileServer = FileUtil.connectServer();
			this.logger.info("-------downloadUerpic-----"+myFile.getUserid()+"用户，头像名称为："+userData.getUserpic());
			File file = fileServer.getFileByPath(userData.getUserpic());
			FileInputStream fis = new FileInputStream(file);
			if (fis != null) {
				int len = fis.available();
				byte[] userpic = new byte[len];
				fis.read(userpic);
				myFileRtn.setUserpic(userpic);
				fis.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myFileRtn;
	}
	/**
	 * @author  署名
	 * @version 1.0	
	 * 功能描述： 根据开始与结束时间实时去更新任务状态 
	 * @see 
	 * @param 
	 * @return  
	 * @exception JeawException
	 */
	public void executeTaskZt(){
		String hql = PropertyXmlMgr.getString("SQL_SYSMGR", "hql_getTask_byZt");
		hql = hql.replaceAll(":where", " and t.taskstate in ('"+SysmgrConstants.RWTDM_ZBZ+"','"+SysmgrConstants.RWTDM_JXZ+"')");
		List<Task> taskList = (List<Task>) this.taskDao.findListByHql(hql);
		Date startTime =null;
		Date endTime =null;
		Date now = taskDao.getSystemDate();
		if(taskList !=null && taskList.size()>0){
			for (Task t:taskList){
				startTime = t.getTaskstartTime();
				endTime = t.getTaskendTime();
				if(startTime!=null &&endTime!=null){
					if(startTime.before(now)&&now.before(endTime)){
						t.setTaskstate(SysmgrConstants.RWTDM_JXZ);
					}else if(now.after(endTime)){
						t.setTaskstate(SysmgrConstants.RWTDM_YJS);
					}
				}else{
					//如果任务开始与结束时间为空，则将任务修改为结束状态 
					t.setDelFlag("D");
				}
			}
		}
		taskDao.batchSaveOrUpdate(taskList);
	}

	public static byte[] file2byte(File file) {

		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName) {

		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		String msg = I18NMessageReader.getMessage("org.root.name");
		String msg1 = I18NMessageReader.getMessage("org.root.fullname");
System.out.println(String.valueOf(null));
System.out.println(msg1);
	}
	public void exeMethodTime(String userid,String method){
		Date nowDate = userDataDao.getSystemDate();
		this.logger.info("--------------"+userid+"调用接口"+method+"，时间为："+DateUtil.format(nowDate,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS_SSS));
	}
}
