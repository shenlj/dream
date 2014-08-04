package com.wholetech.commons;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SysCode extends BaseStandardEntity {

	private static final long serialVersionUID = -8755636949381488914L;

	private String codeClass;
	private String codeClassDesc;
	private String code;
	private String codeDesc;
	private String spellCode;
	// private String parentid;
	private Integer displayOrder;
	private String status;
	private String treePath;
	private String selectAble;
	private Integer codeLevel;
	private String editable;
	private String appSysCode;
	// 关联属性。
	private SysCode parentCode;
	private Set<SysCode> children = new HashSet<SysCode>(0);

	public String getCodeClass() {

		return codeClass;
	}

	public void setCodeClass(final String codeClass) {

		this.codeClass = codeClass;
	}

	public String getCodeClassDesc() {

		return codeClassDesc;
	}

	public void setCodeClassDesc(final String codeClassDesc) {

		this.codeClassDesc = codeClassDesc;
	}

	public String getCode() {

		return code;
	}

	public void setCode(final String code) {

		this.code = code;
	}

	public String getCodeDesc() {

		return codeDesc;
	}

	public void setCodeDesc(final String codeDesc) {

		this.codeDesc = codeDesc;
	}

	public String getSpellCode() {

		return spellCode;
	}

	public void setSpellCode(final String spellCode) {

		this.spellCode = spellCode;
	}

	public Integer getDisplayOrder() {

		return displayOrder;
	}

	public void setDisplayOrder(final Integer displayOrder) {

		this.displayOrder = displayOrder;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(final String status) {

		this.status = status;
	}

	public String getTreePath() {

		return treePath;
	}

	public void setTreePath(final String treePath) {

		this.treePath = treePath;
	}

	public String getSelectAble() {

		return selectAble;
	}

	public void setSelectAble(final String selectAble) {

		this.selectAble = selectAble;
	}

	public Integer getCodeLevel() {

		return codeLevel;
	}

	public void setCodeLevel(final Integer codeLevel) {

		this.codeLevel = codeLevel;
	}

	public SysCode getParentCode() {

		return parentCode;
	}

	public void setParentCode(final SysCode parentCode) {

		this.parentCode = parentCode;
	}

	public Set<SysCode> getChildren() {

		return children;
	}

	public void setChildren(final Set<SysCode> children) {

		this.children = children;
	}

	public SysCode getParent() {

		return parentCode;
	}

	@Override
	public String toString() {

		return new ToStringBuilder(this).append("id", getId()).append("类别", codeClass).toString();
	}

	public String getEditable() {

		return editable;
	}

	public void setEditable(final String editable) {

		this.editable = editable;
	}

	public String getAppSysCode() {

		return appSysCode;
	}

	public void setAppSysCode(final String appSysCode) {

		this.appSysCode = appSysCode;
	}

}
