package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.WaiteService;

public abstract class WaiteServiceImp implements WaiteService {

	public abstract int getWaiteNum(User user);

}
