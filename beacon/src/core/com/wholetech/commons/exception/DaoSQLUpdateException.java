package com.wholetech.commons.exception;

public class DaoSQLUpdateException extends RuntimeException {

	private String sql;
	private Object[] params;

	public DaoSQLUpdateException(String sql, Object[] params, Exception e) {
		super(e);
		this.sql = sql;
		this.params = params;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("执行更新语句出错，使用的更新语句是:[");
		sb.append(this.sql).append("];");
		if (params == null && params.length == 0) {
			sb.append("无查询参数");
		} else {
			sb.append("\r\t查询参数是：[");
			for (int i = 0; i < params.length; i++) {
				if (i == 0) {
					sb.append(params[i].toString());
				} else {
					sb.append(",").append(params[i].toString());
				}
			}
		}

		return sb.toString();
	}

}
