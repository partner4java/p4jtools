package com.partner4java.p4jtools.enums;

public enum BusinessEnum {

	GOODS {

		@Override
		public int id() {
			return 1;
		}

		@Override
		public String viewName() {
			return "商品单";
		}

	},
	STOCK {

		@Override
		public int id() {
			return 2;
		}

		@Override
		public String viewName() {
			return "库存单";
		}

	};

	/**
	 * 业务类型id
	 * 
	 * @return
	 */
	public abstract int id();

	/**
	 * 展示名称
	 * 
	 * @return
	 */
	public abstract String viewName();
}
