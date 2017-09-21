package com.laker.xlibrary.view.dto;

import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

public class ConfigListDTO extends BaseDTO {
	private List<KeyValueBean> info;

	public List<KeyValueBean> getInfo() {
		return info;
	}

	public void setInfo(List<KeyValueBean> info) {
		this.info = info;
	}
}
