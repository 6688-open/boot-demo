package com.dj.boot.co.handler.common;

import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
public interface Handler {

	void handle(Request request, Response response)  throws Exception ;
}
