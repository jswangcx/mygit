package com.ufgov.wcx.entity;

import java.io.Serializable;

public class HOUSE_REGISTER implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long ID;   //null
	private String ROOM_NUMBER;   //房间号
	private String THE_NATURE_OF_HOUSE;   //用房性质
	private Long TOTAL_AREA_OF_HOUSE;   //房屋总面积
	private String CURRENT_STATE;   //当前状态

	public void setID(Long ID){
		this.ID=ID;
	}

	public Long getID(){
		return ID;
	}

	public void setROOM_NUMBER(String ROOM_NUMBER){
		this.ROOM_NUMBER=ROOM_NUMBER;
	}

	public String getROOM_NUMBER(){
		return ROOM_NUMBER;
	}

	public void setTHE_NATURE_OF_HOUSE(String THE_NATURE_OF_HOUSE){
		this.THE_NATURE_OF_HOUSE=THE_NATURE_OF_HOUSE;
	}

	public String getTHE_NATURE_OF_HOUSE(){
		return THE_NATURE_OF_HOUSE;
	}

	public void setTOTAL_AREA_OF_HOUSE(Long TOTAL_AREA_OF_HOUSE){
		this.TOTAL_AREA_OF_HOUSE=TOTAL_AREA_OF_HOUSE;
	}

	public Long getTOTAL_AREA_OF_HOUSE(){
		return TOTAL_AREA_OF_HOUSE;
	}

	public void setCURRENT_STATE(String CURRENT_STATE){
		this.CURRENT_STATE=CURRENT_STATE;
	}

	public String getCURRENT_STATE(){
		return CURRENT_STATE;
	}
}

