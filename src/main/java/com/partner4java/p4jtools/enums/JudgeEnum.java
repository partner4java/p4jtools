package com.partner4java.p4jtools.enums;

/**
 * 是否枚举
 *
 */
public enum JudgeEnum {

	YES {

		@Override
		public short value() {
			return 1;
		}

		@Override
		public String viewName() {
			return "是";
		}

	},
	NO {

		@Override
		public short value() {
			return 0;
		}

		@Override
		public String viewName() {
			return "否";
		}

	};

	/**
	 * 值
	 * 
	 * @return
	 */
	public abstract short value();

	/**
	 * 展示名称
	 * 
	 * @return
	 */
	public abstract String viewName();
}
