package sever;
import java.util.*;

import DAO.ApplyinfoDao;
import DAO.impl.ApplyinfoDaoImpl;
import entity.Applyinfo;
//ҵ����
public class yw {
	ApplyinfoDao impl=new ApplyinfoDaoImpl();
	Scanner in =new Scanner(System.in);
	//���ҵ��
	public void  addgame ()
	{
		Applyinfo info=new Applyinfo();
		System.out.println("����������:");
		String name=in.next();
		info .setName(name);
		System.out.println("����������:");
		int age=in.nextInt();
		info.setAge(age);
		System.out.println("��ѡ��༶(1.һ�� 2.����   3.����)");
		int bj=in.nextInt();
		if(bj==1)
		{
			info.setApplyclass("һ��");
		}else if (bj==2)
		{
			info.setApplyclass("����");
		} else {
			info .setApplyclass("����");
		}
		System.out.println("��ѡ������Ŀ:(1.��Զ 2.������  3.����):");
		int game =in.nextInt();
		if(game==1)
		{
			info.setGame("��Զ");
		}else if (game==2)
		{
			info.setGame("������");
		}else {
			info .setGame("����");
		}
		int result =impl.applyinfo(info);
		if(result!=0)
		{
			System.out.println("�����ɹ�!");
		}
	}
	//����Ŀ��ѯҵ��
	public void selegame()
	{
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		Applyinfo info =new Applyinfo();
		String game=null;
		System.out.println("��ѡ��Ҫ��ѯ�ı�����Ŀ:(1.��Զ  2.������  3.����)");
		int xz=in.nextInt();
		if(xz==1)
		{
			game ="��Զ";
		}else if (xz==2)
		{
			game="������";
		}else {
			game="����";
		}
		list=impl.classinfo(game);
		System.out.println("��Ŀ\t����\t�༶\t����");
		for(int i=0;i<list.size();i++)
		{
			info=list.get(i);
			System.out.print(info.getGame()+"\t"+info.getName()+"\t"+info.getApplyclass()+"\t"+info.getAge()+"\n");
		}
	}
	//���༶��ѯҵ��
	public void seleclass(){
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		Applyinfo info =new Applyinfo();
		String game=null;
		System.out.println("��ѡ��Ҫ��ѯ�ı�����Ŀ:(1.һ��  2.����  3.����)");
		int xz=in.nextInt();
		if(xz==1)
		{
			game ="һ��";
		}else if (xz==2)
		{
			game="����";
		}else {
			game="����";
		}
		list=impl.seleclass(game);
		System.out.println("��Ŀ\t����\t�༶\t����");
		for(int i=0;i<list.size();i++)
		{
			info=list.get(i);
			System.out.print(info.getGame()+"\t"+info.getName()+"\t"+info.getApplyclass()+"\t"+info.getAge()+"\n");
		}
	}
	//ȡ������ҵ��
	public void delete(){
		System.out.println("������ȡ��������ѧ������:");
		String name=in.next();
		int result=impl.deletename(name);
		if(result!=0)
		{
			System.out.println("ȡ�������ɹ�");
		}
	}
	public void jm(){

		System.out.println("*****��ӭʹ���˶��ᱨ��ϵͳ*****");
		do{
			System.out.println("1.ѧ������  2.��������Ŀ��ѯ  3.���༶��ѯ   4.ȡ������    5.�˳�ϵͳ");
			System.out.println("��ѡ��(1-5):");
			int xz=in.nextInt();
			switch (xz) {
			case 1:addgame();break;
			case 2:selegame();break;
			case 3: seleclass();break;
			case 4:delete(); break;
			case 5:System.out.println("ллʹ��"); System.exit(1);
			default:
				break;
			}
		}while (true);
	}
}
