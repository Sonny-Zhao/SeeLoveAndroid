package com.tianyu.seelove.network.request;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tianyu.seelove.common.MessageSignConstant;
import com.tianyu.seelove.common.RequestCode;
import com.tianyu.seelove.common.ResponseConstant;
import com.tianyu.seelove.common.WebConstant;
import com.tianyu.seelove.model.entity.network.request.UserFindAllActionInfo;
import com.tianyu.seelove.model.entity.network.request.base.RequestInfo;
import com.tianyu.seelove.model.entity.network.response.UserFindAllRspInfo;
import com.tianyu.seelove.network.request.base.PostJsonRequest;
import com.tianyu.seelove.utils.AppUtils;
import com.tianyu.seelove.utils.GsonUtil;
import com.tianyu.seelove.utils.LogUtil;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * author : L.jinzhu
 * date : 2015/9/11
 * introduce : 获取所有用户请求request
 */
public class UserFindAllRequest extends PostJsonRequest {
    private int pageNumber = 0;
    private int dataGetType = 0;
    private int ageStart = 0;
    private int ageEnd = 0;
    private String sex;
    private String cityCode;

    public UserFindAllRequest(Handler handler, Context context, int pageNumber, int dataGetType, int ageStart, int ageEnd, String sex, String cityCode) {
        this.handler = handler;
        this.context = context;
        this.pageNumber = pageNumber;
        this.dataGetType = dataGetType;
        this.ageStart = ageStart;
        this.ageEnd = ageEnd;
        this.sex = sex;
        this.cityCode = cityCode;
    }

    @Override
    protected String getParamsJson() {
        UserFindAllActionInfo actionInfo = new UserFindAllActionInfo(RequestCode.USER_FIND_ALL, pageNumber, dataGetType, AppUtils.getInstance().getUserId(), ageStart, ageEnd, sex, cityCode);
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
            UserFindAllRspInfo info = GsonUtil.fromJson(response.toString(), UserFindAllRspInfo.class);
            //响应正常
            if (ResponseConstant.SUCCESS == info.getStatusCode()) {
                b.putInt("currentPage", info.getCurrentPage());
                b.putInt("isEndPage", info.getIsEndPage());
                b.putSerializable("userList", (Serializable) info.getUserDetailList());
                msg.what = MessageSignConstant.USER_FIND_ALL_SUCCESS;
                msg.setData(b);
                handler.sendMessage(msg);
                LogUtil.i(requestTag() + " success");
            }
            //响应失败
            else {
                b.putInt("code", info.getStatusCode());
                b.putString("message", info.getStatusMsg());
                msg.what = MessageSignConstant.USER_FIND_ALL_FAILURE;
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
