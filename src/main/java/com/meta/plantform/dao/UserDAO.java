package com.meta.plantform.dao;

import java.util.List;

import com.meta.plantform.model.User;

public interface UserDAO {

	List<User> selectAllUser();
}
