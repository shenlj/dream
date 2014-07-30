package com.mrs.sysmgr.service;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mrs.sysmgr.entity.MyFile;
import com.mrs.sysmgr.entity.StepData;
import com.mrs.sysmgr.entity.Task;
import com.mrs.sysmgr.entity.UserData;
import com.mrs.sysmgr.entity.UserRequest;
import com.mrs.sysmgr.entity.ZnshResult;
import com.wholetech.commons.service.BaseService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */

@WebService
@Path(value = "/znshGlService")
public interface ZnshGlService extends BaseService {

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
	public Boolean register(@PathParam("userid") String userid, @PathParam("password") String password, @PathParam("username") String username);

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
	public Boolean sendSms(@PathParam("userid") String userid);

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
	public Integer checkSms(@PathParam("userid") String userid, @PathParam("authCode") String authCode);

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
	@GET
	@Path("/checkUser/{userid}/{password}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult checkUser(@PathParam("userid") String userid, @PathParam("password") String password);

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
	public UserData getUserData(@PathParam("userid") String userid);


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
	public Boolean updateUser(@PathParam("userid") String userid, UserData userData);

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
	public String getStepData(@PathParam("userid") String userid, @PathParam("stepDate") String stepDate);

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
	 * @exception JeawException
	 */
	@POST
	@Path("/addStepData/{userid}/{stepDate}")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public Boolean addStepData(@PathParam("userid") String userid, @PathParam("stepDate") String stepDate,
			StepData stepData)throws ParseException;

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
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addFriend(@PathParam("userid") String userid, @PathParam("friendid") String friendid);

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
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult deleteFriend(@PathParam("userid") String userid, @PathParam("friendid") String friendid);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 4. 获取好友信息列表，根据用户id,设备id,找到所有与该用户使用同一类型设备的用户，返回用户信息,包括状态：
	 *           0 已添加：代表改好友已经被用户添加，1 添加：代表好友已在使用手环，且还未被用户添加，
	 * @see
	 * @param userid
	 *            用户id
	 * @param friendIds
	 *            朋友id，多个以逗号分割
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getFriendUserData/{userid}/{friendIds}")
	@Produces( { "application/json", "application/xml" })
	public List<UserData> getFriendUserData(@PathParam("userid") String userid, @PathParam("friendIds") String friendIds);

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
	public ZnshResult getFriendsInfo(@PathParam("userid") String userid);

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
	public List<ZnshResult> getFriendsList(@PathParam("userid") String userid);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 增加约跑任务，任务名称不能重复，如果重复则不能创建，，约跑模式：普通01、马拉松02
	 * @see
	 * @param
	 * @return 建立成功后返回true及约跑任务id,否则返回false且失败原因
	 * @exception JeawException
	 */
	@POST
	@Path("/addTask")
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult addTask(Task task);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 删除约跑任务，如果已经进行或结束不能删除，需要删除约跑任务表，及约跑明细表相关数据
	 * @see
	 * @param taskid任务id
	 * @return 删除成功后返回true，否则返回false且失败原因
	 * @exception JeawException
	 */
	@PUT
	@Path("/delTask/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public ZnshResult delTask(@PathParam("taskid") String taskid);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 获得约跑信息,获取自己创建及自己朋友创建的约跑任务、马拉松式任务
	 * @see
	 * @param userid用户id
	 * @return 返回任务list
	 * @exception JeawException
	 */
	@GET
	@Path("/getTaskList/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<Task> getTaskList(@PathParam("userid") String userid);

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
	public ZnshResult addCompetitionPart(@PathParam("userid") String userid, @PathParam("taskid") String taskid);

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
	public ZnshResult delCompetitionPart(@PathParam("userid") String userid, @PathParam("taskid") String taskid);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述：获取用户参加的约跑任务信息及创建者信息，创建者头像、创建者、创建时间、参加人数、结束倒计时、比赛状态,约跑任务id
	 * @see
	 * @param userid用户id
	 * @return list<ZnshResult>
	 * @exception JeawException
	 */
	@GET
	@Path("/getTasksByPartyUserid/{userid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getTasksByPartyUserid(@PathParam("userid") String userid);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 约跑任务排名信息：名次、用户头像、用户姓名、完成时间、行走公里数、贡献率（行走公里数/团队总行走公里数）
	 * @see
	 * @param
	 * @return
	 * @exception JeawException
	 */
	@GET
	@Path("/getCompetitionPartyByTaskid/{taskid}")
	@Produces( { "application/json", "application/xml" })
	public List<ZnshResult> getCompetitionPartyByTaskid(@PathParam("taskid") String taskid);

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
	public ZnshResult addUserRequest(UserRequest userRequest);

	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 同意或拒絕添加好友
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
	public ZnshResult updateUserRequest(@PathParam("reqId") String reqId, @PathParam("requestZt") String requestZt);

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
	public List<ZnshResult> getFriendsRank(@PathParam("userid") String userid);

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
	public Boolean inviteUser(@PathParam("userid") String userid, @PathParam("friendid") String friendid);

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
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	public ZnshResult uploadUerpic(MyFile myFile);
	/**
	 * @author 署名
	 * @version 1.0
	 *          功能描述： 下载用户头像
	 * @see
	 * @param
	 * @return````````
	 * @exception JeawException
	 */
	@POST
	@Path("/downloadUerpic")
	@Produces( { "application/json", "application/xml" })
	@Consumes( { "application/json", "application/xml" })
	public MyFile downloadUerpic(MyFile myFile);
	/**
	 * @author  署名
	 * @version 1.0	
	 * 功能描述： 根据开始与结束时间实时去更新任务状态 
	 * @see 
	 * @param 
	 * @return  
	 * @exception JeawException
	 */
	public void executeTaskZt();
}
