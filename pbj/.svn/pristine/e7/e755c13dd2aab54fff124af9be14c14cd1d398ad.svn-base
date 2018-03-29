package com.lw.iot.pbj.common.enums;


public class SysEnum {

	/**
	 * 鸡只状态
	 * @author sunships
	 * @date 2018年1月10日下午2:49:08
	 */
	public enum ChickenStatus{
		FEEDING(1,"饲养中"),SELLED(2,"已出栏");
		private int key;
		private String desc;
		
		private ChickenStatus(int key, String desc) {
			this.key = key;
			this.desc = desc;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static ChickenStatus getDesc(int key){
			for(ChickenStatus fn:ChickenStatus.values()){
				if(fn.key==key)
					return fn;
			}
			return null;
		}
	}
}
