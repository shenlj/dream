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

    return this.codeClass;
  }

  public void setCodeClass(String codeClass) {

    this.codeClass = codeClass;
  }

  public String getCodeClassDesc() {

    return this.codeClassDesc;
  }

  public void setCodeClassDesc(String codeClassDesc) {

    this.codeClassDesc = codeClassDesc;
  }

  public String getCode() {

    return this.code;
  }

  public void setCode(String code) {

    this.code = code;
  }

  public String getCodeDesc() {

    return this.codeDesc;
  }

  public void setCodeDesc(String codeDesc) {

    this.codeDesc = codeDesc;
  }

  public String getSpellCode() {

    return this.spellCode;
  }

  public void setSpellCode(String spellCode) {

    this.spellCode = spellCode;
  }

  public Integer getDisplayOrder() {

    return this.displayOrder;
  }

  public void setDisplayOrder(Integer displayOrder) {

    this.displayOrder = displayOrder;
  }

  public String getStatus() {

    return this.status;
  }

  public void setStatus(String status) {

    this.status = status;
  }

  public String getTreePath() {

    return this.treePath;
  }

  public void setTreePath(String treePath) {

    this.treePath = treePath;
  }

  public String getSelectAble() {

    return this.selectAble;
  }

  public void setSelectAble(String selectAble) {

    this.selectAble = selectAble;
  }

  public Integer getCodeLevel() {

    return this.codeLevel;
  }

  public void setCodeLevel(Integer codeLevel) {

    this.codeLevel = codeLevel;
  }

  public SysCode getParentCode() {

    return this.parentCode;
  }

  public void setParentCode(SysCode parentCode) {

    this.parentCode = parentCode;
  }

  public Set<SysCode> getChildren() {

    return this.children;
  }

  public void setChildren(Set<SysCode> children) {

    this.children = children;
  }

  public SysCode getParent() {

    return this.parentCode;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this).append("id", getId()).append("类别", this.codeClass).toString();
  }

  public String getEditable() {

    return this.editable;
  }

  public void setEditable(String editable) {

    this.editable = editable;
  }

  public String getAppSysCode() {

    return this.appSysCode;
  }

  public void setAppSysCode(String appSysCode) {

    this.appSysCode = appSysCode;
  }

}
