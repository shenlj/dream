<#assign declarationName = pojo.getQualifiedDeclarationName()>
<#assign className = pojo.getDeclarationName()>
<#assign varClassName = className?uncap_first>
<#global package="">
<#global namespace="">
<#assign packages = declarationName?split(".")>
<#if packages??>
	<#list packages as name>
		<#if name_index lt packages?size - 2>
			<#if name_index == 0>
				<#assign package = package + name>
			<#else>
				<#if name_index gte 2>
						<#assign namespace = namespace + "/" + name>
				</#if>
				<#assign package = package + "." + name>
			</#if>	
		</#if>	
	</#list>
</#if>
package ${package}.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import ${package}.dao.${className}Dao;
import ${pojo.getQualifiedDeclarationName()};
import ${package}.service.${className}Service;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class ${className}ServiceImp extends BaseServiceImp<${className}> implements ${className}Service {

	private ${className}Dao ${varClassName}Dao;
	
	public void set${className}Dao(${className}Dao ${varClassName}Dao) {
		this.${varClassName}Dao = ${varClassName}Dao;
	}
	
	@Override
	protected BaseDao<${className}> getBaseDao() {
		return this.${varClassName}Dao;
	}
	
}
