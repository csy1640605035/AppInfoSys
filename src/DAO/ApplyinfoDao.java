package DAO;

import java.util.List;

import entity.Applyinfo;

public interface ApplyinfoDao {
int applyinfo(Applyinfo info);//报名方面
List<Applyinfo> classinfo(String game);//按项目查询方法
List<Applyinfo> seleclass(String infoclass);//按班级查询方法
int deletename(String name);	//取消报名方法
}
