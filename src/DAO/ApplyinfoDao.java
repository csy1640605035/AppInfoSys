package DAO;

import java.util.List;

import entity.Applyinfo;

public interface ApplyinfoDao {
int applyinfo(Applyinfo info);//��������
List<Applyinfo> classinfo(String game);//����Ŀ��ѯ����
List<Applyinfo> seleclass(String infoclass);//���༶��ѯ����
int deletename(String name);	//ȡ����������
}
