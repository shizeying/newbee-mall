package ltd.newbee.mall.controller.hive;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ltd.newbee.mall.controller.vo.LineVO;
import ltd.newbee.mall.service.MallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * (mall_log)表控制层
 *
 * @author xxxxx
 */
@Controller
@RequestMapping("/mall_log")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MallLogDOController {
		
		/**
		 * 服务对象
		 */
		private final MallLogService mallLogService;
		
		@GetMapping("/carousels")
		
		public String carouselPage(HttpServletRequest request) {
				request.setAttribute("path", "show_figure_index");
				return "admin/show_figure_index";
		}
		
		/**
		 * 折线图
		 */
		@GetMapping("/line")
		@ResponseBody
		public LineVO line() {
				return this.mallLogService.line();
		}
		/**
		 * 柱状图
		 */
		@GetMapping("/bar")
		@ResponseBody
		public LineVO bar() {
				return this.mallLogService.bar();
		}
		/**
		 * 饼图
		 */
		@GetMapping("/pie")
		@ResponseBody
		public LineVO pie() {
				return this.mallLogService.pie();
		}
		
		
}