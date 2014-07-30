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
package ${package}.web.action;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Results;

import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.web.BaseAction;
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

@ParentPackage("default")
@Namespace("${namespace}")
@Results({

})
<bean id="${varClassName}Dao" class="${package}.dao.hibernate.${className}DaoImp" />
<bean id="${varClassName}Service" class="${package}.service.imp.${className}ServiceImp" />
public class ${className}Action extends BaseAction<${className}> {

	private ${className}Service ${varClassName}Service;

	public ${className}Action() {
		super();
	}
	
	public void set${className}Service(${className}Service ${varClassName}Service) {
		this.${varClassName}Service = ${varClassName}Service;
	}

	protected BaseService<${className}> getBaseService() {
		return this.${varClassName}Service;
	}
}
