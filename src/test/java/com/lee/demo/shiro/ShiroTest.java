package com.lee.demo.shiro;

import java.util.Arrays;
import java.util.Collection;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Shiro用户验证简单示例：用户名/密码
 * 
 * @author hzlifan
 *
 */
@RunWith(Parameterized.class)
public class ShiroTest {

	private String userName;
	private String password;

	public ShiroTest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { "kobe", "bryant" },
				{ "kobe", "bean" }, { "stephen", "curry" } };
		return Arrays.asList(data);
	}

	@org.junit.Test
	public void testLoginAuth() {
		// shiro.ini中配置了合法的用户名/密码
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,
				password);

		try {
			user.login(token);
		} catch (UnknownAccountException uae) {
			System.out.println("用户名不存在");
		} catch (IncorrectCredentialsException ice) {
			System.out.println("密码错误");
		} catch (LockedAccountException lae) {
			System.out.println("账户被锁定");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("失败次数过多");
		} catch (AuthenticationException ae) {
			System.out.println("验证失败");
		}

		Assert.assertEquals(true, user.isAuthenticated());
		user.logout();
	}

}
