package com.ignite;

import com.ignite.model.Student;

public interface AppService {

	public void get(Long id);
	public void getAll();
	public void save(Student student);
}
