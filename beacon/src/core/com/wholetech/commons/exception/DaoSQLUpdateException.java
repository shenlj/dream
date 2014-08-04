package com.wholetech.commons.exception;

public class DaoSQLUpdateException extends RuntimeException {

	private final String sql;
	private final Object[] params;

	public DaoSQLUpdateException(final String sql, final Object[] params, final Exception e) {

		super(e);
		this.sql = sql;
		this.params = params;
	}

	@Override
	public String getMessage() {

		final StringBuffer sb = new StringBuffer("执行更新语句出错，使用的更新语句是:[");
		sb.append(sql).append("];");
		if (this.params == null && this.params.length == 0) {
			sb.append("无查询参数");
		} else {
			sb.append("\r\t查询参数是：[");
			for (int i = 0; i < this.params.length; i++) {
				if (i == 0) {
					sb.append(this.params[i].toString());
				} else {
					sb.append(",").append(this.params[i].toString());
				}
			}
		}

		return sb.toString();
	}

}
