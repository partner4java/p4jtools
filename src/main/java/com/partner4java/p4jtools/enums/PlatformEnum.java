package com.partner4java.p4jtools.enums;

/**
 * 平台枚举
 *
 */
public enum PlatformEnum {
	CLIENT_IOS(1, "iOS客户端", "iOSClient") {
		@Override
		public int value() {
			return 1;
		}

		@Override
		public String viewName() {
			return "iOSClient";
		}

		@Override
		public String cnName() {
			return "iOS客户端";
		}
	},
	CLIENT_ANDROID(2, "安卓客户端", "androidClient") {
		@Override
		public int value() {
			return 2;
		}

		@Override
		public String viewName() {
			return "androidClient";
		}

		@Override
		public String cnName() {
			return "安卓客户端";
		}
	},
	WECHART(3, "微信平台", "wechat") {
		@Override
		public int value() {
			return 3;
		}

		@Override
		public String viewName() {
			return "wechat";
		}

		@Override
		public String cnName() {
			return "微信平台";
		}
	},
	PC(4, "电脑端", "pc") {
		@Override
		public int value() {
			return 4;
		}

		@Override
		public String viewName() {
			return "pc";
		}

		@Override
		public String cnName() {
			return "电脑端";
		}
	},
	KEFU(5, "客服平台", "kefu") {
		@Override
		public int value() {
			return 5;
		}

		@Override
		public String viewName() {
			return "kefu";
		}

		@Override
		public String cnName() {
			return "客服平台";
		}
	},
	M(6, "M版本", "m") {
		@Override
		public int value() {
			return 6;
		}

		@Override
		public String viewName() {
			return "m";
		}

		@Override
		public String cnName() {
			return "M版本";
		}

	};

	public static String getViewName(int value) {
		if (value == CLIENT_IOS.value()) {
			return CLIENT_IOS.viewName();
		} else if (value == CLIENT_ANDROID.value()) {
			return CLIENT_ANDROID.viewName();
		} else if (value == WECHART.value()) {
			return WECHART.viewName();
		} else if (value == PC.value()) {
			return PC.viewName();
		} else if (value == KEFU.value()) {
			return KEFU.viewName();
		} else if (value == M.value()) {
			return M.viewName();
		} else {
			return null;
		}
	}

	private int value;
	private String cnName;
	private String viewName;

	private PlatformEnum(int value, String cnName, String viewName) {
		this.value = value;
		this.cnName = cnName;
		this.viewName = viewName;
	}

	public int getValue() {
		return value;
	}

	public String getCnName() {
		return cnName;
	}

	public String getViewName() {
		return viewName;
	}

	public abstract int value();

	public abstract String cnName();

	public abstract String viewName();

}
