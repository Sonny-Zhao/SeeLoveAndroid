package com.tianyu.seelove.network.request;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tianyu.seelove.common.MessageSignConstant;
import com.tianyu.seelove.common.RequestCode;
import com.tianyu.seelove.common.ResponseConstant;
import com.tianyu.seelove.common.WebConstant;
import com.tianyu.seelove.model.entity.network.request.FollowFindAllActionInfo;
import com.tianyu.seelove.model.entity.network.request.SecurityCodeSendActionInfo;
import com.tianyu.seelove.model.entity.network.request.base.RequestInfo;
import com.tianyu.seelove.model.entity.network.response.FollowFindAllRspInfo;
import com.tianyu.seelove.network.request.base.PostJsonRequest;
import com.tianyu.seelove.utils.GsonUtil;
import com.tianyu.seelove.utils.LogUtil;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * author : L.jinzhu
 * date : 2015/9/11
 * introduce : 发送验证码request
 */
public class SecurityCodeSendRequest extends PostJsonRequest {
    private String phoneNumber;
    private String type;

    public SecurityCodeSendRequest(Handler handler, Context context, String phoneNumber, String type) {
        this.handler = handler;
        this.context = context;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    @Override
    protected String getParamsJson() {
        SecurityCodeSendActionInfo actionInfo = new SecurityCodeSendActionInfo(RequestCode.SEND_SECURITY_CODE, phoneNumber, type);
        RequestInfo requestInfo = new RequestInfo(context, actionInfo);
        return GsonUtil.toJson(requestInfo);
    }

    @Override
    protected String getUrl() {
        return WebConstant.BASE_URL;
    }

    @Override
    protected String requestTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected void responseSuccess(JSONObject response) {
        Bundle b = new Bundle();
        Message msg = new Message();
        try {
            LogUtil.i("response success json: [" + requestTag() + "]: " + response.toString());
            FollowFindAllRspInfo info = GsonUtil.fromJson(response.toString(), FollowFindAllRspInfo.class);
            //响应正常
            if (ResponseConstant.SUCCESS == info.getStatusCode()) {
                msg.what = MessageSignConstant.SEND_SECURITY_CODE_SUCCESS;
                msg.setData(b);
                handler.sendMessage(msg);
                LogUtil.i(requestTag() + " success");
            }
            //响应失败
            else {
                b.putInt("code", info.getStatusCode());
                b.putString("message", info.getStatusMsg());
                msg.what = MessageSignConstant.SEND_SECURITY_CODE_FAILURE;
                msg.setData(b);
                handler.sendMessage(msg);
                LogUtil.i(requestTag() + " error, code: " + info.getStatusCode() + " message: " + info.getStatusMsg());
            }
        } catch (Throwable e) {
            handler.sendEmptyMessage(MessageSignConstant.UNKNOWN_ERROR);
            LogUtil.e(requestTag() + " error", e);
        }
    }
}
