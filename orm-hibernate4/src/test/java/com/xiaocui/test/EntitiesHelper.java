package com.xiaocui.test;

import java.util.List;

import org.junit.Assert;

import com.xiaocui.test.vo.User;

public class EntitiesHelper {
	private static User baseUser = new User(1, "admin1");

	public static void assertUser(User expected, User actual) {
		// 预期值不能为空
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
	}

	public static void assertUsers(List<User> expected, List<User> actual) {
		for (int i = 0; i < expected.size(); i++) {
			// 预期值
			User ue = expected.get(i);
			// 实际值
			User ua = actual.get(i);
			assertUser(ue, ua);
		}
	}

	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}

}
