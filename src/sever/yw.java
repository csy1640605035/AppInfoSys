package sever;
import java.util.*;

import DAO.ApplyinfoDao;
import DAO.impl.ApplyinfoDaoImpl;
import entity.Applyinfo;
//业务类
public class yw {
	ApplyinfoDao impl=new ApplyinfoDaoImpl();
	Scanner in =new Scanner(System.in);
	//添加业务
	public void  addgame ()
	{
		Applyinfo info=new Applyinfo();
		System.out.println("请输入姓名:");
		String name=in.next();
		info .setName(name);
		System.out.println("请输入年龄:");
		int age=in.nextInt();
		info.setAge(age);
		System.out.println("请选择班级(1.一班 2.二班   3.三班)");
		int bj=in.nextInt();
		if(bj==1)
		{
			info.setApplyclass("一班");
		}else if (bj==2)
		{
			info.setApplyclass("二班");
		} else {
			info .setApplyclass("三班");
		}
		System.out.println("请选择报名项目:(1.跳远 2.接力跑  3.跳绳):");
		int game =in.nextInt();
		if(game==1)
		{
			info.setGame("跳远");
		}else if (game==2)
		{
			info.setGame("接力跑");
		}else {
			info .setGame("跳绳");
		}
		int result =impl.applyinfo(info);
		if(result!=0)
		{
			System.out.println("报名成功!");
		}
	}
	//按项目查询业务
	public void selegame()
	{
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		Applyinfo info =new Applyinfo();
		String game=null;
		System.out.println("请选择要查询的比赛项目:(1.跳远  2.接力跑  3.跳绳)");
		int xz=in.nextInt();
		if(xz==1)
		{
			game ="跳远";
		}else if (xz==2)
		{
			game="接力跑";
		}else {
			game="跳绳";
		}
		list=impl.classinfo(game);
		System.out.println("项目\t姓名\t班级\t年龄");
		for(int i=0;i<list.size();i++)
		{
			info=list.get(i);
			System.out.print(info.getGame()+"\t"+info.getName()+"\t"+info.getApplyclass()+"\t"+info.getAge()+"\n");
		}
	}
	//按班级查询业务
	public void seleclass(){
		List<Applyinfo> list =new ArrayList<Applyinfo>();
		Applyinfo info =new Applyinfo();
		String game=null;
		System.out.println("请选择要查询的比赛项目:(1.一班  2.二班  3.三班)");
		int xz=in.nextInt();
		if(xz==1)
		{
			game ="一班";
		}else if (xz==2)
		{
			game="二班";
		}else {
			game="三班";
		}
		list=impl.seleclass(game);
		System.out.println("项目\t姓名\t班级\t年龄");
		for(int i=0;i<list.size();i++)
		{
			info=list.get(i);
			System.out.print(info.getGame()+"\t"+info.getName()+"\t"+info.getApplyclass()+"\t"+info.getAge()+"\n");
		}
	}
	//取消报名业务
	public void delete(){
		System.out.println("请输入取消报名的学生姓名:");
		String name=in.next();
		int result=impl.deletename(name);
		if(result!=0)
		{
			System.out.println("取消报名成功");
		}
	}
	public void jm(){

		System.out.println("*****欢迎使用运动会报名系统*****");
		do{
			System.out.println("1.学生报名  2.按比赛项目查询  3.按班级查询   4.取消报名    5.退出系统");
			System.out.println("请选择(1-5):");
			int xz=in.nextInt();
			switch (xz) {
			case 1:addgame();break;
			case 2:selegame();break;
			case 3: seleclass();break;
			case 4:delete(); break;
			case 5:System.out.println("谢谢使用"); System.exit(1);
			default:
				break;
			}
		}while (true);
	}
}
