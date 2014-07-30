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
package ${package}.service;

import com.wholetech.commons.service.BaseService;
import ${pojo.getQualifiedDeclarationName()};

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public interface ${className}Service extends BaseService<${className}> {

}
