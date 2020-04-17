package org.geekbang.thinking.in.spring.bean.factory;

/* User 工厂类
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;

public interface UserFactory {

    default User createUser(){
        return User.createUser();
    }
}
