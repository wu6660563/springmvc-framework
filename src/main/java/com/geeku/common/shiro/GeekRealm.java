package com.geeku.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 身份认证服务类
 * 此类是权限域的基本实现,继承自shiro安全框架的AuthorizingRealm. 它是整个权限控制的生命周期. 执行动作如下：
 * 1.获取当前用户的名称或状态()-subject. 
 * 2.通过用户名称与状态拿取角色相关的权限集.
 * 3.根据用户相关权限对用户的身份验证、授权、会话、访问控制进行管理.
 * 4.整个域的生命周期最后交付给SecurityManager来管理(SecurityManager 相当于 SpringMVC中的DispqtcherServlet.)
 * 
 * @Author ji.jiang
 */
public class GeekRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(GeekRealm.class);
	
//	@Autowired(required = true)
//	@Qualifier("userService")
//	private UserService userService;
	
	//授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
//		String account = (String) principalCollection.fromRealm(getName()).iterator().next(); //获取登录时输入的用户名 
//		User user = userService.findUserByAccount(account);
//		if(user != null){
//			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); //权限信息对象,用来存放用户的所有"角色"及"权限"
//			String category = user.getCategory();//用户类别，0-管理员;1-学生；2-老师
//			if(category != null){
//				if("0".equals(category.trim())){
//					info.addRole("admin");//角色信息    
//				}else if("1".equals(category.trim())){
//					info.addRole("student");
//				}else if("2".equals(category.trim())){
//					info.addRole("teacher");
//				}
//			}
//            //info.addStringPermission(role.getPermissions()); //基于Permission的权限信息  
//			return info;
//		}
		
		return null;
	}
   
	//认证回调函数,登录时调用.
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = null;
		
//		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//		String userName = upToken.getUsername();
//		String userPwd = new String(upToken.getPassword());
//		User user = userService.findLoginUser(userName, userPwd);
//		if(user != null){
//			authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(),user.getPassword(),getName());
//		}else{
//			logger.error(userName + "……登录认证失败……！");
//		}
		
		return authenticationInfo;
	}
    
}
