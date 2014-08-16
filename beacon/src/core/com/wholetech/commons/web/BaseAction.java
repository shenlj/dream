package com.wholetech.commons.web;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.wholetech.commons.Constants;
import com.wholetech.commons.exception.EntityNotExistException;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.BeanUtil;
import com.wholetech.commons.util.GenericsUtil;

/**
 * 负责管理单个Entity CRUD操作的Struts Action基类. 子类以以下方式声明,并实现将拥有默认的CRUD函数
 * 此类仅演示一种封装的方式，大家可按自己的项目习惯进行重新封装。 目前封装了： <li>1. list、load、save、delete 四种action的流程封装；</li> <li>2. 子类需要实现的两个接口</li> <li>
 * 3. 对关联参数的处理，只要子类声明实现了ParameterNameAware，则具备了该能力。默认关闭这种能力</li>
 * <p>
 * <strong>重点一</strong>： 在action的具体实现类中，采用以下方式来来声明：
 * 
 * <pre>
 *   &at;ParentPackage("default")
 *   &at;Namespace("/monitor")
 *   &at;Results({ 
 *      &at;Result(name = "list", value = "/app/monitor/main.jsp", type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
 *      &at;Result(name = "runstate", value = "/app/home/runState.jsp")
 *   })
 *   public class RunStateAction extends BaseAction<RunState> {
 *   	public String doSomething() {
 *          return "list";
 *      }
 *   }
 * </pre>
 * 
 * 其中ParantPackage中对应struts.xml中定义的package元素的name； namespace中的将被用到url中，见重点二。 <br />
 * <br />
 * <br />
 * <br />
 * </p>
 * <p>
 * <strong>重点二</strong>：如上例，通过前台url对应action的处理方法的对应方式为：
 * <i>http://ip:port/context</i>/namespace注解/action名称!action方法.action <br />
 * <br />
 * <br />
 * <br />
 * </p>
 * <p>
 * <strong>重点三</strong>：对传入参数的封装。struts2对参数的封装是通过valuestack来进行的，可以通过
 * <code>ServletActionContext.getContext().getValueStack();</code> 来获取valuestack，struts框架会根据http
 * post的参数名在valuestack中从栈顶到栈底 扫描对象，如果某一个对象的属性对应参数名，
 * 那么参数值就会被设置到当前对象的对应属性。而struts2默认放两个对象到valuestack，首先是当前action对象，其次是通过getModel
 * 从action获取到的对象，那么你就应该知道参数是怎么被设置到action的实体变量和entity中了。 <br>
 * 目前有两个重要对象，entity和page，entity本框架作为getModel返回的对象，而page是封装分页信息的对象。 <br>
 * 如果你不想使用struts2对参数的封装，也可以使用自己直接从request中获取。可以使用接口BaseAction.getParameter(param )来获取参数值。 <br />
 * <br />
 * <br />
 * <br />
 * </p>
 * <p>
 * <strong>重点四</strong>:对结果的处理，如重点一中的Results注解可以使用下面的注解方式。
 * <li>Result(name = "list", value = "target.jsp", type=org.apache.struts2.dispatcher.ServletRedirectResult.class) --
 * 结果重定向到value指定的jsp上。</li>
 * <li>Result(name = "runstate", value = "/app/home/runState.jsp") -- 默认情况下，使用forward方式导向到value指定的jsp上。</li>
 * <li>Result(name = "success", value = "customer", params={"method","getAll"},
 * type=com.opensymphony.xwork2.ActionChainResult.class) --
 * 这种使用forward方式导向到另一个action，其中value指定导向到的action，而params指定要action中的处理方法。</li> <br />
 * 如果在action方法处理中，只是想Response中输出一些字符串、html元素或者json串，那么就不需要上面的Result配置了， 可以使用接口render*()接口，然后直接返回Action.NONE; eg.
 * <code>this.renderText("success"); return Action.NONE; </code> <br />
 * <br />
 * <br />
 * <br />
 * </p>
 * <p>
 * <strong>重点五</strong>:prepare方法的执行。 由于BaseAction实现了，Preparable接口，那么如果在struts.xml文件中配置了prepare拦截器
 * ，那么在执行action方法之前，都会执行prepare<i>Method</i>()方法，其中的<i>Method</i>对应action方法名。 <br />
 * <br />
 * <br />
 * <br />
 * </p>
 * <p>
 * <strong>重点六</strong>:对级联属性的处理。 如果前台传入的参数中有级联参数，比如User中有地址对象，而地址有address.city,address
 * .street等属性，struts默认的处理方式可能会造成设置级联属性后，在保存时出错，尤其像**.id时，最容易出错， 所以就需要使用BaseAction中处理级联属性的方式
 * ，默认情况下是关闭的，如果出现级联属性处理有误的情况，要激活之，激活的办法是让子类实现接口ParameterNameAware。 <br />
 * <br />
 * <br />
 * <br />
 * </p>
 */
@SuppressWarnings("unchecked")
abstract public class BaseAction<T> extends ComnAction implements
    ModelDriven<T>, Preparable {

  protected static final String LIST = "list";

  protected Page<T> page = new Page<T>();
  protected String scolumns;
  protected int idisplayLength;
  protected int secho;
  protected int idisplayStart;
  /** Action所管理的Entity类型. */
  protected Class<T> entityClass;

  protected T entityForm;

  /** preparable接口要实现的方法，现在写成空实现 */
  public void prepare() throws Exception {

    // donothing
  }

  /** 在save()前执行二次绑定. */
  public void prepareSave() throws Exception {

    prepareModel();
  }

  public void prepareList() throws Exception {

  }

  public void prepareLoad() throws Exception {

    prepareModel();
  }

  /**
   * 获得EntityManager类，必须在子类实现
   */
  abstract protected BaseService<T> getBaseService();

  protected Serializable getKey() {

    String id = getParameter(Constants.DEFAULT_ID_NAME);
    if (StringUtils.isBlank(id)) {
      return null;
    }
    return id;
  }

  public T getModel() {

    return this.entityForm;
  }

  protected void prepareModel() throws Exception {

    // 获得有效的参数(主键)
    Serializable id = this.getKey();

    if (id != null) {
      this.logger.debug("准备实体{}@{}", this.getEntityClass().getName(), id);
      this.entityForm = getBaseService().get(id);
      if (this.entityForm == null) {
        throw new EntityNotExistException(this.getEntityClass(), id);
      }
    } else {
      if (this.entityForm == null) {
        this.entityForm = getNewEntity();
      }
    }

    if (this instanceof ParameterNameAware) {
      handleCascadeId();
    }
  }

  /**
   * 取得entityClass的函数. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
   */
  protected Class<T> getEntityClass() {

    return this.entityClass;
  }

  /**
   * 构造函数. 通过对T的反射获得entity class
   */
  public BaseAction() {

    try {
      this.entityClass = GenericsUtil.getGenericClass(getClass());
    } catch (Exception e) {
      this.logger.error("初始化action时出错", e);
    }
  }

  /**
   * url参数未定义method时的默认Action函数. 默认为分页查询List Action.
   */
  @Override
  public String execute() {

    return "";
  }

  /**
   * 新建业务对象的函数.
   */
  protected T getNewEntity() throws InstantiationException, IllegalAccessException {

    T object = null;
    Class<T> clazz = getEntityClass();

    object = clazz.newInstance();
    return object;
  }

  public boolean acceptableParameterName(String parameterName) {

    if ("id".equals(parameterName)
        && StringUtils.isBlank(getParameter(parameterName))) {
      return false;
    }

    // 如果以.id结尾，则要致其对应的属性字段为空
    if (parameterName.endsWith(".id")) {
      String property = StringUtils.substringBeforeLast(parameterName,
          ".id");

      if (StringUtils.isBlank(getParameter(parameterName))
          && property.indexOf('.') == -1 && this.getModel() != null) {
        try {
          BeanUtil.setProperty(this.getModel(), property, null);
        } catch (Exception e) {
          // 不做任何事情，只是为了对付hibernate映射的问题
        }
      }
      return false;
    }

    return true;
  }

  private void handleCascadeId() {

    ActionContext ac = ActionContext.getContext();
    T entityForm = this.getModel();

    Map parameters = ac.getParameters();

    for (Iterator iterator = parameters.entrySet().iterator(); iterator
        .hasNext();) {
      Map.Entry entry = (Map.Entry) iterator.next();
      String key = (String) entry.getKey();

      // 如果是以id结尾，则例行检查
      if (key.endsWith(".id")) {
        String property = key.substring(0, key.length() - 3);
        String[] arrId = (String[]) entry.getValue();
        if (property.indexOf('.') != -1 || arrId.length > 1) {
          // 如果是多重关联，则不予处理
          continue;
        }

        try {
          if (StringUtils.isBlank(arrId[0])) {
            // 如果id为空，则将此属性置为空
            PropertyUtils.setProperty(entityForm, property, null);
          } else {
            PropertyDescriptor pd = PropertyUtils
                .getPropertyDescriptor(entityForm, property);
            Object cascadeProperty = this.getBaseService().get(
                pd.getPropertyType(), arrId[0]);
            PropertyUtils.setProperty(entityForm, property,
                cascadeProperty);
          }
        } catch (Exception e) {
          // 不做任何事情，只是为了对付hibernate映射的问题
        }
      }
    }
  }

  public String getScolumns() {

    return this.scolumns;
  }

  public void setScolumns(String scolumns) {

    this.scolumns = scolumns;
  }

  public Page<T> getPage() {

    return this.page;
  }

  public void setPage() {

    this.page.setIdisplayLength(this.idisplayLength);
    this.page.setSecho(this.secho);
    this.page.setIdisplayStart(this.idisplayStart);
  }

  public int getIdisplayLength() {

    return this.idisplayLength;
  }

  public void setIdisplayLength(int idisplayLength) {

    this.idisplayLength = idisplayLength;
  }

  public int getSecho() {

    return this.secho;
  }

  public void setSecho(int secho) {

    this.secho = secho;
  }

  public int getIdisplayStart() {

    return this.idisplayStart;
  }

  public void setIdisplayStart(int idisplayStart) {

    this.idisplayStart = idisplayStart;
  }
  
  /**
   *获取登录对象(获取后用user强制转换下)
   **/
  public Object getLoginObject(){
	  return this.getSession().getAttribute(Constants.USER_IN_SESSION);
  }
  
  public Map getUserAuth(){
	  return (Map)this.getSession().getAttribute(Constants.User_Auth);
  }

}
