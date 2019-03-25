package net.treebear.kwifimanager.mvp.server.model;

import net.treebear.kwifimanager.base.BaseResponse;
import net.treebear.kwifimanager.base.BaseServerModel;
import net.treebear.kwifimanager.mvp.server.contract.FirmwareUpgradeContract;

import okhttp3.RequestBody;

public class FirmwareUpgradeModel extends BaseServerModel implements FirmwareUpgradeContract.Model {

    @Override
    public void upgradeNode(RequestBody params, AsyncCallBack<BaseResponse<Object>> callBack) {
        bindObservable(mService.firmwareUpgrade(params),callBack);
    }
}
