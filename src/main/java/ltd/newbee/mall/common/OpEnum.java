package ltd.newbee.mall.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zeyingshi
 * @date 2023/3/15 07:27
 */
@Getter
@AllArgsConstructor
public enum OpEnum {
		/**
		 * 登陆
		 */
		LOGIN("login", "登陆"),
		/**
		 * 注册
		 */
		REGISTER("register", "注册"),
		/**
		 * 购买商品
		 */
		BUY("buy", "购买商品"),
		/**
		 * 添加商品
		 */
		ADD("add", "添加商品"),
		/**
		 * 删除商品
		 */
		DELETE("delete", "删除商品"),
		/**
		 * 修改商品
		 */
		UPDATE("update", "修改商品");
		
		private final String op;
		private final String desc;
		
		public static String getDesc(String op) {
				for (OpEnum opEnum : OpEnum.values()) {
						if (opEnum.getOp().equals(op)) {
								return opEnum.getDesc();
						}
				}
				return null;
		}
		
}