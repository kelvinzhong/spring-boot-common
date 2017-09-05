/**
 * 
 */
package com.hippo.common.framework.exceptionResolver;

import com.alibaba.fastjson.JSON;
import com.hippo.common.bean.ErrorCode;
import com.hippo.common.bean.SystemException;
import com.hippo.common.config.properties.Configuration;
import com.hippo.common.util.general.Constants;
import com.hippo.common.util.general.StringUtils;
import com.hippo.common.util.general.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class JsonHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(JsonHandlerExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		log.error("exception resolver catch : ", ex);

		ModelAndView mav = new ModelAndView("jsonView");
		mav.setView(new JsonView());
		if (ex instanceof BindException) {
			BindException bindException = (BindException) ex;
			if (bindException.getBindingResult() != null) {
				StringBuffer sb = new StringBuffer();
				for (ObjectError objectError : bindException.getBindingResult().getAllErrors()) {
					String defaultMsg = objectError.getDefaultMessage();
					if (!StringUtils.isEmpty(defaultMsg)) {
						sb.append(Configuration.getProperty(defaultMsg) + "  ");
					}
				}
				log.error("bind exception {}", sb);
				mav.getModel().put(Constants.RESULT_CODE, SystemException.codeFormat(ErrorCode.BAD_REQUEST.code));
				mav.getModel().put(Constants.RESULT_MSG, ErrorCode.BAD_REQUEST.msg);

			}
		} else if (ex instanceof SystemException) {
			SystemException systemException = (SystemException) ex;

			mav.getModel().put(Constants.RESULT_CODE, systemException.getCode());
			mav.getModel().put(Constants.RESULT_MSG, systemException.getMsg());

		} else if (Utils.isSystemException(ex)) {
			mav.getModel().put(Constants.RESULT_CODE, Utils.getSystemExceptionCode(ex));
			mav.getModel().put(Constants.RESULT_MSG, Utils.getSystemExceptionMessage(ex));
		} else {

			mav.getModel().put(Constants.RESULT_CODE, SystemException.codeFormat(ErrorCode.INTERNAL_SERVER_ERROR.code));
			mav.getModel().put(Constants.RESULT_MSG, "Unkown error, system would recovered soon! Please try later!");
		}

		return mav;
	}

	public class JsonView extends AbstractView {

		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug("here call writer");
			response.getWriter().write(JSON.toJSONString(model));
		}

	}

}
