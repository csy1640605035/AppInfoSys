package DAO.impl;
import DAO.BaseDao;
import entity.Applyinfo;

import java.util.ArrayList;
import java.util.List;

import DAO.ApplyinfoDao;

public class ApplyinfoDaoImpl extends BaseDao implements ApplyinfoDao  {

	@Override
	public int applyinfo(Applyinfo info ) {
		// TODO Auto-generated method stub
		int result=0;
		String sql="insert into apply_info(name,age,class,game)values(?,?,?,?) ";
		Object [] obj={info.getName(),info.getAge(),info.getApplyclass(),info.getGame()};
		result=super.addgame(sql, obj);
		return result;
	}

	@Override//实现接口的按项目查询
	public List<Applyinfo> classinfo(String game) {
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		String sql=String.format("select * from apply_info where game='%s'",game);
		list=super.classinfo(sql);
		// TODO Auto-generated method stub
		return list;
	}

	@Override //实现接口的按班级查询
	public List<Applyinfo> seleclass(String infoclass) {
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		String sql=String.format("select * from apply_info where class='%s'",infoclass);
		list=super.classinfo(sql);
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	//实现接口的取消报名
	public int deletename(String name) {
		// TODO Auto-generated method stub
		int result=0;
		String sql="delete from apply_info where name=?";
		Object [] obj={name};
		result=super.addgame(sql, obj);
		return result;
	}

}
