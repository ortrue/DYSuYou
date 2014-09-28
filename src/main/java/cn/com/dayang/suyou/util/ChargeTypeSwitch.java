package cn.com.dayang.suyou.util;

public class ChargeTypeSwitch {

	public static Double SpaceSwitch(int ctId){
		double space=0;
		switch (ctId) {
		case 1:
			space=10;
			break;
		case 2:
			space=20;
			break;
		case 3:
			space=2048;
			break;
		case 4:
			space=5120;
			break;
		}
		return space;
	}
	
	public static Integer flowSwitch(int ctId){
		int flow=0;
		switch (ctId) {
		case 1:
			flow=5;
			break;
		case 2:
			flow=10;
			break;
		case 3:
			flow=300;
			break;
		case 4:
			flow=800;
			break;
		}
		return flow;
	}
	
	public static String TypeSwitch(int ctId){
		String type="";
		switch (ctId) {
		case 1:
			type="套餐1：个人标准用户";
			break;
		case 2:
			type="套餐2：个人用户流量包";
			break;
		case 3:
			type="套餐3：企业客户A";
			break;
		case 4:
			type="套餐4：企业用户B";
			break;
		}
		return type;
	}
}
