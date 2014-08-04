package com.wholetech.commons.query;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.wholetech.commons.Constants;

public class Page<T> implements java.io.Serializable {

	private static final long serialVersionUID = -8488719493119941270L;

	/** 当前页第一条数据的位置,从0开始 */
	private int idisplayStart;
	/** 每页的记录数 */
	private int idisplayLength = Constants.DEFAULT_PAGE_SIZE;
	private int secho;
	/** 当前页中存放的记录 */
	private List<T> data;
	/** 总记录数 */
	private int totalCount;
	private boolean autoCount = true;

	/** 构造方法，只构造空页 */
	public Page() {

		this(0, Constants.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 默认构造方法
	 * 
	 * @param idisplayStart
	 *            本页数据在数据库中的起始位置
	 * @param idisplayLength
	 *            本页容量
	 */
	public Page(final int idisplayStart, final int idisplayLength) {

		this.idisplayLength = idisplayLength;
		this.idisplayStart = idisplayStart;
	}

	/**
	 * 默认构造方法
	 * 
	 * @param idisplayStart
	 *            本页数据在数据库中的起始位置
	 * @param totalCount
	 *            数据库中总记录条数
	 * @param idisplayLength
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public Page(final List<T> list) {

		if (list != null) {
			this.idisplayLength = 0;
			this.idisplayStart = 0;
			this.totalCount = list.size();
			this.data = list;
		}
	}

	public void setTotalCount(final int totalCount) {

		if (this.idisplayStart >= totalCount && getIdisplayLength() > 0) {
			if (totalCount == 0) {
				this.idisplayStart = 0;
			} else {
				this.idisplayStart = totalCount / getIdisplayLength() == 0 ? 1 : totalCount / getIdisplayLength();
			}
		}

		this.totalCount = totalCount;
	}

	public int getIdisplayStart() {

		return this.idisplayStart;
	}

	public void setIdisplayStart(final int idisplayStart) {

		this.idisplayStart = idisplayStart;
	}

	/**
	 * 取数据库中包含的总记录数
	 */
	public int getTotalCount() {

		return this.totalCount;
	}

	@Override
	public String toString() {

		return new ToStringBuilder(this)
				.append("起始行", this.idisplayStart)
				.append("每页行数", this.idisplayLength)
				.append("总条数", this.totalCount)
				.append("当前列表size", this.data.size())
				.toString();
	}

	public int getIdisplayLength() {

		return this.idisplayLength;
	}

	public void setIdisplayLength(final int idisplayLength) {

		this.idisplayLength = idisplayLength;
	}

	public int getSecho() {

		return this.secho;
	}

	public void setSecho(final int secho) {

		this.secho = secho;
	}

	public List<T> getData() {

		return this.data;
	}

	public void setData(final List<T> data) {

		this.data = data;
	}

	public boolean isAutoCount() {

		return this.autoCount;
	}

	public void setAutoCount(final boolean autoCount) {

		this.autoCount = autoCount;
	}
}
